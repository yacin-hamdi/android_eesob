package ym.tutorials.eesobcamera.presentation.permission


import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.hilt.navigation.compose.hiltViewModel
import ym.tutorials.eesobcamera.utils.Constants.CAMERA_PERMISSION

@Composable
fun PermissionRequest(
    viewModel: PermissionViewModel = hiltViewModel(),
    onGoToAppSettingClick: () -> Unit,
    isPermissionDeclined: (String) -> Boolean
){
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {isGranted ->
            viewModel.onPermissionResult(
                permission = CAMERA_PERMISSION,
                isGranted = isGranted
            )
        }
    )

    SideEffect {
        cameraPermissionResultLauncher.launch(
            CAMERA_PERMISSION
        )
    }



    dialogQueue.reversed().forEach { permission ->
        PermissionDialog(
            permissionTextProvider = when (permission) {
                Manifest.permission.CAMERA -> CameraPermissionTextProviderImpl()
                else -> return@forEach
            },
            isPermanentlyDeclined = isPermissionDeclined(permission),
            onDismiss = viewModel::dismissDialog,
            onOkClick = {
                viewModel.dismissDialog()
                cameraPermissionResultLauncher.launch(
                    permission
                )
            },
            onGoToAppSettingClick = onGoToAppSettingClick
        )
    }

}
