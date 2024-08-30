package ym.tutorials.eesobcamera.presentation.permission


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ym.tutorials.eesobcamera.ui.theme.EesobCameraTheme


@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingClick: () -> Unit,
    modifier: Modifier = Modifier
){
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider()
                Text(
                    text = if(isPermanentlyDeclined){
                        "Grant permission"
                    }else{
                        "OK"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingClick()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(16.dp)
                )
            }
        },
        title = {
            Text(
                text = "Permission required"
            )
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(isPermanentlyDeclined)
            )
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewPermissionDialog(){
    EesobCameraTheme {
        PermissionDialog(
            permissionTextProvider = CameraPermissionTextProviderImpl(),
            isPermanentlyDeclined = true,
            onDismiss = { /*TODO*/ },
            onOkClick = { /*TODO*/ },
            onGoToAppSettingClick = { /*TODO*/ })
    }
}