package ym.tutorials.eesobcamera.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ym.tutorials.eesobcamera.R
import ym.tutorials.eesobcamera.presentation.camera.components.CameraPreview
import ym.tutorials.eesobcamera.presentation.camera.components.PhotoBottomSheetContent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen(
    applicationContext: Context,
    viewModel: CameraViewModel = hiltViewModel()
){
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetState = scaffoldState.bottomSheetState
    val controller = remember {
        LifecycleCameraController(applicationContext).apply{
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }


    val bitmaps by viewModel.imagesData.collectAsState()

    LaunchedEffect(
        key1 = viewModel.photoInference.value,
        key2 = bottomSheetState.currentValue)
    {
        if(viewModel.photoInference.value || bottomSheetState.currentValue == SheetValue.Expanded){
            viewModel.stopCamera(
                controller = controller
            )
        }else{

            viewModel.startCamera(
                controller = controller,
                lifecycleOwner = lifecycleOwner
            )
        }

    }


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            PhotoBottomSheetContent(
                bitmaps = bitmaps,
                modifier = Modifier
                    .fillMaxSize()
            ){ filename ->
                viewModel.deleteImage(filename)
            }

        },
        sheetPeekHeight = 0.dp

    ) { padding ->



        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ){



            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                if(viewModel.photoInference.value){
                    CircularProgressIndicator(
                        modifier = Modifier.then(Modifier.size(32.dp)),
                        color = Color.Red
                    )
                }

            }
            


            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter,
            ){
                IconButton(
                    onClick = {
                        viewModel.takePhoto(
                            controller = controller
                        )
                    })
                {

                    Icon(
                        painter = painterResource(R.drawable.take_photo),
                        contentDescription = "take photo",
                        tint = Color.White
                    )

                }
            }




            IconButton(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                })
            {
                Icon(
                    painter = painterResource(R.drawable.photo),
                    contentDescription = "open gallery",
                    tint = Color.White
                )


            }








        }

    }

}







