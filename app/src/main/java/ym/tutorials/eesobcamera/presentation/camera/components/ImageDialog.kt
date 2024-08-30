package ym.tutorials.eesobcamera.presentation.camera.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.window.Dialog

@Composable
fun ImageDialog(
    bitmap: Bitmap?,
    onShowDialog: (Boolean) -> Unit
){
    Dialog(
        onDismissRequest = {
            onShowDialog(false)
        }
    ){
        bitmap?.let{
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
                    .clickable {
                        onShowDialog(false)
                    }
            )

        }
    }
}