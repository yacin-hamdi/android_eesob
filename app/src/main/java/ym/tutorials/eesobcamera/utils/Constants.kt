package ym.tutorials.eesobcamera.utils

import android.Manifest
import org.tensorflow.lite.DataType

object Constants {

    const val MODEL_MAX_RESULTS_COUNT = 10
    const val MODEL_PATH = "eesob.tflite"
    const val LABELS_PATH = "labels.txt"
    const val CONFIDENCE_SCORE = 0.5f

    const val INPUT_MEAN = 0f
    const val INPUT_STANDARD_DEVIATION = 255f

    val INPUT_IMAGE_TYPE = DataType.FLOAT32
    val OUTPUT_IMAGE_TYPE = DataType.FLOAT32

    const val CONFIDENCE_THRESHOLD = 0.3F
    const val IOU_THRESHOLD = 0.5F

    const val CAMERA_PERMISSION = Manifest.permission.CAMERA

}