package com.kotlin.commonlibrary.permissions.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kotlin.commonlibrary.permissions.EasyPermissions
import com.kotlin.commonlibrary.permissions.helpers.base.PermissionsHelper
import com.kotlin.commonlibrary.permissions.models.PermissionRequest

/**
 * Dialog to prompt the user to go to the app's settings screen and enable permissions. If the user
 * clicks 'OK' on the dialog, they are sent to the settings screen. The result is returned to the
 * Activity via [Activity.onActivityResult].
 */
class RationaleDialog(
    private val context: Context,
    private val model: PermissionRequest
) : DialogInterface.OnClickListener {

    private val permissionCallbacks: EasyPermissions.PermissionCallbacks?
        get() = if (context is EasyPermissions.PermissionCallbacks) context else null
    private val rationaleCallbacks: EasyPermissions.RationaleCallbacks?
        get() = if (context is EasyPermissions.RationaleCallbacks) context else null

    /**
     * Display the dialog.
     */
    fun showCompatDialog() {
        AlertDialog.Builder(context, model.theme)
            .setCancelable(false)
            .setMessage(model.rationale)
            .setPositiveButton(model.positiveButtonText, this)
            .setNegativeButton(model.negativeButtonText, this)
            .show()
    }

    fun showDialog() {
        android.app.AlertDialog.Builder(context, model.theme)
            .setCancelable(false)
            .setMessage(model.rationale)
            .setPositiveButton(model.positiveButtonText, this)
            .setNegativeButton(model.negativeButtonText, this)
            .show()
    }

    override fun onClick(dialogInterface: DialogInterface?, buttonType: Int) {
        when (buttonType) {
            Dialog.BUTTON_POSITIVE -> {
                rationaleCallbacks?.onRationaleAccepted(model.code)
                when (context) {
                    is Fragment ->
                        PermissionsHelper
                            .newInstance(context)
                            .directRequestPermissions(model.code, model.perms)
                    is Activity ->
                        PermissionsHelper
                            .newInstance(context)
                            .directRequestPermissions(model.code, model.perms)
                    is AppCompatActivity ->
                        PermissionsHelper
                            .newInstance(context)
                            .directRequestPermissions(model.code, model.perms)
                }
            }
            Dialog.BUTTON_NEGATIVE -> {
                rationaleCallbacks?.onRationaleDenied(model.code)
                permissionCallbacks?.onPermissionsDenied(model.code, model.perms.toList())
            }
        }
    }
}
