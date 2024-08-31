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
    override suspend fun saveImage(bitmap: Bitmap, filename: String) {
        withContext(Dispatchers.IO){
            try{
                context.openFileOutput(
                    filename,
                    Context.MODE_PRIVATE
                ).use {outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
                }

            }catch(e: Exception){
                Log.e("ImageRepository", "error saving image", e)
            }
        }
    }

    override suspend fun loadImage(): List<ImageData> {
        return withContext(Dispatchers.IO){
            val imageList = mutableListOf<ImageData>()
            val fileList = context.fileList()
            for (filename in fileList){
                if(filename.endsWith(".jpg") ||
                    filename.endsWith(".png") ||
                    filename.endsWith(".jpeg")){
                    try {
                        context.openFileInput(filename).use{ inputStream ->
                            val bitmap = BitmapFactory.decodeStream(inputStream)
                            bitmap?.let {
                                imageList.add(ImageData(filename, it))
                            }

                        }
                    } catch (e: Exception){
                        Log.e("ImageRepository", "Error loading image", e)
                    }
                }
            }
            imageList
        }
    }

    override suspend fun deleteImage(filename: String): Boolean {
        return withContext(Dispatchers.IO){
                context.deleteFile(filename)
        }
    }


}