package com.kotlin.commonlibrary.permissions.helpers

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat
import com.kotlin.commonlibrary.permissions.dialogs.RationaleDialog
import com.kotlin.commonlibrary.permissions.helpers.base.PermissionsHelper
import com.kotlin.commonlibrary.permissions.models.PermissionRequest

/**
 * Permissions helper for [Activity].
 */
internal class ActivityPermissionsHelper(
    host: Activity
) : PermissionsHelper<Activity>(host) {

    override var context: Context? = host

    override fun directRequestPermissions(requestCode: Int, perms: Array<out String>) {
        ActivityCompat.requestPermissions(host, perms, requestCode)
    }

    override fun shouldShowRequestPermissionRationale(perm: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(host, perm)
    }

    override fun showRequestPermissionRationale(permissionRequest: PermissionRequest) {
        RationaleDialog(host, permissionRequest).showDialog()
    }
}
