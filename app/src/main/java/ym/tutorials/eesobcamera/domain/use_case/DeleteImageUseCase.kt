package ym.tutorials.eesobcamera.domain.use_case

import ym.tutorials.eesobcamera.domain.repository.ImageRepository
import javax.inject.Inject

class DeleteImageUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(
        filename: String,
        onSuccess: () -> Unit
    ): Boolean{
        return imageRepository.deleteImage(
            filename = filename,
            onSuccess = onSuccess
        )
    }
}