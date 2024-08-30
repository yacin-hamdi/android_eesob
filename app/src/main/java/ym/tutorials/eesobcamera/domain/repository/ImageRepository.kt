package ym.tutorials.eesobcamera.domain.repository

import android.graphics.Bitmap

interface ImageRepository {
    suspend fun saveImage(
        bitmap: Bitmap,
        filename: String
    )

    suspend fun loadImage(
        filename: String
    ): Bitmap?
}