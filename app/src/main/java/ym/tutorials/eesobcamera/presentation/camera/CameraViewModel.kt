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
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    val objectDetectionManager: ObjectDetectionManager,
    @ApplicationContext val context: Context
): ViewModel() {
    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    private val _photoInference = mutableStateOf(false)
    val photoInference: State<Boolean> = _photoInference




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
            boxResults.forEach{box ->
                DrawDetectionBox.onDrawBox(bitmap, box)

            }

            _bitmaps.value += bitmap
        }


    }

    override fun onCleared() {
        super.onCleared()
        objectDetectionManager.clear()
        viewModelScope.cancel()

    }
}