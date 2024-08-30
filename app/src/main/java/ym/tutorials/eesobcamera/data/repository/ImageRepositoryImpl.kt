package ym.tutorials.eesobcamera.data.repository

import android.graphics.Bitmap
import ym.tutorials.eesobcamera.domain.repository.ImageRepository

class ImageRepositoryImpl: ImageRepository {
    override suspend fun saveImage(bitmap: Bitmap, filename: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadImage(filename: String): Bitmap? {
        TODO("Not yet implemented")
    }
}