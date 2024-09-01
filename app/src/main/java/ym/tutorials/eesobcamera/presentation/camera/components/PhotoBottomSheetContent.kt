package ym.tutorials.eesobcamera.presentation.camera.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ym.tutorials.eesobcamera.domain.model.ImageData
import java.io.File


@Composable
fun PhotoBottomSheetContent(
    imageData: List<ImageData>,
    modifier: Modifier = Modifier,
    context: Context,
    onDeleteItem: (item: String) -> Unit
){
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }

    var imagePath by rememberSaveable {
        mutableStateOf<File?>(null)
    }
    if(imageData.isEmpty()){
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center)
        {
            Text(
                text = "there are no photos yet"
            )
        }
    }else{
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {
            items(imageData){ imageData->
                val absolutePath = context.filesDir.absolutePath + File.separator + imageData.filePath
                val file = File(absolutePath)

                Box(
                    modifier = Modifier
                        .clickable {
                            imagePath = file
                            showDialog = true
                        }
                ){
                    AsyncImage(
                        model = file,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                    )

                    IconButton(
                        onClick = { onDeleteItem(imageData.filePath) },
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray.copy(alpha = 0.5f))
                    ){
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription =null
                        )

                    }
                }

            }

        }
    }

    if(showDialog){
        ImageDialog(
            imagePath = imagePath
        ) { show ->
            showDialog = show

        }
    }
}

