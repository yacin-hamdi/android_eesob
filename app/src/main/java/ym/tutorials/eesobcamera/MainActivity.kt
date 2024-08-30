package ym.tutorials.eesobcamera

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import ym.tutorials.eesobcamera.presentation.camera.CameraScreen
import ym.tutorials.eesobcamera.presentation.permission.PermissionRequest
import ym.tutorials.eesobcamera.ui.theme.EesobCameraTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EesobCameraTheme {
                PermissionRequest(
                    onGoToAppSettingClick = ::openAppSettings,
                    isPermissionDeclined = {
                        !shouldShowRequestPermissionRationale(
                            it
                        )
                    }
                )

                CameraScreen(
                    applicationContext = applicationContext
                )
            }
        }
    }
}

fun Activity.openAppSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}