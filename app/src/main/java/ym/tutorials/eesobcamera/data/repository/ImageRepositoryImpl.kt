package ym.tutorials.eesobcamera.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.text.selection.DisableSelection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ym.tutorials.eesobcamera.domain.model.ImageData
import ym.tutorials.eesobcamera.domain.repository.ImageRepository

class ImageRepositoryImpl(private val context: Context): ImageRepository {
    override suspend fun saveImage(
        bitmap: Bitmap,
        filename: String,
        onSuccess: () -> Unit
    ) {
        withContext(Dispatchers.IO){
            try{
                context.openFileOutput(
                    filename,
                    Context.MODE_PRIVATE
                ).use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
                }
                onSuccess()

            }catch(e: Exception){
                Log.e("ImageRepository", "error saving image", e)
            }
        }
    }

    override suspend fun loadImages(): List<ImageData> {
        return withContext(Dispatchers.IO){
            val imageList = mutableListOf<ImageData>()
            val fileList = context.fileList()
            for (filename in fileList){
                if(filename.endsWith(".jpg") ||
                    filename.endsWith(".png") ||
                    filename.endsWith(".jpeg")){
                    imageList.add(ImageData(
                        filePath = filename
                    ))
                }
            }
            imageList
        }
    }

    override suspend fun deleteImage(
        filename: String,
        onSuccess: () -> Unit
    ): Boolean {
        return withContext(Dispatchers.IO){
            val success = context.deleteFile(filename)
            if(success)
                onSuccess()

            success
        }
    }

    fun calculateSampleSize(height: Int, width: Int, reqHeight: Int, reqWidth: Int): Int {
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }


}