package ym.tutorials.eesobcamera.presentation.permission

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}