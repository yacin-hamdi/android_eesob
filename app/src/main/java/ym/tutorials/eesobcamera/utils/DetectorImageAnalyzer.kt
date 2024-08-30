package ym.tutorials.eesobcamera.utils


import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import ym.tutorials.eesobcamera.domain.manager.objectDetection.ObjectDetectionManager
import ym.tutorials.eesobcamera.domain.model.Detection

import javax.inject.Inject

class DetectorImageAnalyzer @Inject constructor(
    private val objectDetectionManager: ObjectDetectionManager,
    private val onResult: (List<Detection>) -> Unit,
//    private val confidenceScoreState: Float
): ImageAnalysis.Analyzer {


    private var frameSkipCounter = 0
    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {



            val rotatedImageMatrix: Matrix =
                Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                }

            val rotatedBitmap: Bitmap = Bitmap.createBitmap(
                image.toBitmap(),
                0,
                0,
                image.width,
                image.height,
                rotatedImageMatrix,
                true
            )

//            val results = objectDetectionManager.detectObjectsInBitmap(rotatedBitmap)
//            onResult(results)


        }
        frameSkipCounter++
        image.close()
    }

}