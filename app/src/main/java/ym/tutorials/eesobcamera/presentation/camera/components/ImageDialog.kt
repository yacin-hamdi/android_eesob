package ym.tutorials.eesobcamera.presentation.camera.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import java.io.File

@Composable
fun ImageDialog(
    imagePath: File?,
    onShowDialog: (Boolean) -> Unit
){
    Dialog(
        onDismissRequest = {
            onShowDialog(false)
        }
    ){
        imagePath?.let{
            AsyncImage(
                model = it,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onShowDialog(false)
                    }
            )

        }
    }
}