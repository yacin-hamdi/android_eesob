package ym.tutorials.eesobcamera.domain.use_case

import ym.tutorials.eesobcamera.domain.model.ImageData
import ym.tutorials.eesobcamera.domain.repository.ImageRepository
import javax.inject.Inject

class LoadImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {

    suspend operator fun invoke(): List<ImageData>{
        return imageRepository.loadImages()
    }
}