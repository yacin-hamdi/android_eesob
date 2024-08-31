package ym.tutorials.eesobcamera.domain.repository

import android.graphics.Bitmap
import ym.tutorials.eesobcamera.domain.model.ImageData

interface ImageRepository {
    suspend fun saveImage(
        bitmap: Bitmap,
        filename: String
    )

    suspend fun loadImages(): List<ImageData>
    suspend fun deleteImage(filename: String): Boolean
}