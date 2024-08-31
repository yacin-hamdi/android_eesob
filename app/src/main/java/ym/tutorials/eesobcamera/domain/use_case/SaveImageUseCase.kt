package ym.tutorials.eesobcamera.domain.use_case

import android.graphics.Bitmap
import ym.tutorials.eesobcamera.domain.repository.ImageRepository
import javax.inject.Inject

class SaveImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(
        bitmap: Bitmap,
        filename: String
    ){
        imageRepository.saveImage(
            bitmap = bitmap,
            filename = filename
        )
    }
}