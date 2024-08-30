package ym.tutorials.eesobcamera.domain.manager.objectDetection

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow
import ym.tutorials.eesobcamera.domain.model.Detection

interface ObjectDetectionManager {
    fun detectObjectsInBitmap(
        bitmap: Bitmap
    ): List<Detection>

    fun clear()
}