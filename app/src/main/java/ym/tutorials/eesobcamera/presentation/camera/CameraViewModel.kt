package ym.tutorials.eesobcamera.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ym.tutorials.eesobcamera.common.DrawDetectionBox
import ym.tutorials.eesobcamera.domain.manager.objectDetection.ObjectDetectionManager
import ym.tutorials.eesobcamera.domain.model.ImageData
import ym.tutorials.eesobcamera.domain.repository.ImageRepository
import ym.tutorials.eesobcamera.domain.use_case.DeleteImageUseCase
import ym.tutorials.eesobcamera.domain.use_case.LoadImagesUseCase
import ym.tutorials.eesobcamera.domain.use_case.SaveImageUseCase
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val objectDetectionManager: ObjectDetectionManager,
    private val saveImageUseCase: SaveImageUseCase,
    private val deleteImageUseCase: DeleteImageUseCase,
    private val loadImagesUseCase: LoadImagesUseCase,
    @ApplicationContext val context: Context
): ViewModel() {
    private val _imagesData = MutableStateFlow<List<ImageData>>(emptyList())
    val imagesData = _imagesData.asStateFlow()

    private val _photoInference = mutableStateOf(false)
    val photoInference: State<Boolean> = _photoInference

    init {
        loadImages()
    }


    fun takePhoto(
        controller: LifecycleCameraController,
    ){
        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object: OnImageCapturedCallback(){
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val matrix = Matrix().apply{
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val rotateBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    image.close()


                    viewModelScope.launch {
                        _photoInference.value = true
                        try {
                            processImage(rotateBitmap)
                        } finally {
                            _photoInference.value = false
                        }
                    }




                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                }
            }
        )
    }

    fun startCamera(
        controller: LifecycleCameraController,
        lifecycleOwner: LifecycleOwner
    ){
        controller.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
        controller.unbind()
        controller.bindToLifecycle(lifecycleOwner)
    }

    fun stopCamera(
        controller: LifecycleCameraController
    ){
        controller.unbind()
    }

    private suspend fun processImage(bitmap: Bitmap) {
        withContext(Dispatchers.IO){
            val boxResults = objectDetectionManager.detectObjectsInBitmap(bitmap)
            val newBitmap = boxResults.fold(bitmap.copy(bitmap.config, true)){
                bitmap, box ->
                DrawDetectionBox.onDrawBox(bitmap, box)
            }

            val filename = "image_${System.currentTimeMillis()}.png"
            saveImageUseCase(
                bitmap = newBitmap,
                filename = filename,
            ){
                loadImages()
            }
            newBitmap.recycle()

        }


    }

    fun loadImages(){
        viewModelScope.launch {
            _imagesData.value = loadImagesUseCase()
        }
    }

    fun deleteImage(
        filename: String
    ){
        viewModelScope.launch{
            deleteImageUseCase(
                filename = filename
            ){
                loadImages()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
//        _imagesData.value.forEach { it.bitmap.recycle() }
        objectDetectionManager.clear()
        viewModelScope.cancel()

    }
}