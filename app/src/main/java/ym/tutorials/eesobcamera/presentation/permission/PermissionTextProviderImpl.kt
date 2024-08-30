package ym.tutorials.eesobcamera.presentation.permission

class CameraPermissionTextProviderImpl: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined){
            "it seems you permanemtly declined camera permission, " +
                    "you can go to the app settings to grant it."
        }else{
            "This app needs access to your camera so that your can use it " +
                    "to take photo of electronics."
        }
    }
}