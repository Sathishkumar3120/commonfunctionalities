package com.kotlin.commonfunctionalities

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kotlin.commonfunctionalities.databinding.ActivityMainBinding
import com.kotlin.commonlibrary.permissions.EasyPermissions
import com.kotlin.commonlibrary.permissions.annotations.AfterPermissionGranted
import com.kotlin.commonlibrary.permissions.dialogs.DEFAULT_SETTINGS_REQ_CODE
import com.kotlin.commonlibrary.permissions.dialogs.SettingsDialog

class MainActivity : AppCompatActivity(),
    EasyPermissions.PermissionCallbacks, EasyPermissions.RationaleCallbacks {

    //permission
    private val TAG = "MainActivity"

    //Data Binding
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCamera.setOnClickListener {
            onClickRequestPermissionCameraButton()
        }
        binding.buttonStorage.setOnClickListener {
            onClickRequestPermissionStorageButton()
        }
        binding.buttonLocationAndContacts.setOnClickListener {
            onClickRequestPermissionLocationAndContactsButton()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DEFAULT_SETTINGS_REQ_CODE) {
            val yes = getString(R.string.yes)
            val no = getString(R.string.no)

            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(
                this,
                getString(
                    R.string.returned_from_app_settings_to_activity,
                    if (hasCameraPermission()) yes else no,
                    if (hasLocationAndContactsPermissions()) yes else no,
                    if (hasStoragePermission()) yes else no
                ),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    // ============================================================================================
    //  Implementation Permission Callbacks
    // ============================================================================================

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        Log.d(TAG, getString(R.string.log_permissions_granted, requestCode, perms.size))
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        Log.d(TAG, getString(R.string.log_permissions_denied, requestCode, perms.size))

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        }
    }

    // ============================================================================================
    //  Implementation Rationale Callbacks
    // ============================================================================================

    override fun onRationaleAccepted(requestCode: Int) {
        Log.d(TAG, getString(R.string.log_permission_rationale_accepted, requestCode))
    }

    override fun onRationaleDenied(requestCode: Int) {
        Log.d(TAG, getString(R.string.log_permission_rationale_denied, requestCode))
    }

    // ============================================================================================
    //  Private Methods
    // ============================================================================================

    @AfterPermissionGranted(Companion.REQUEST_CODE_CAMERA_PERMISSION)
    private fun onClickRequestPermissionCameraButton() {
        if (hasCameraPermission()) {
            // Have permission, do things!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_camera_rationale_message),
                Companion.REQUEST_CODE_CAMERA_PERMISSION,
                Manifest.permission.CAMERA
            )
        }
    }

    @AfterPermissionGranted(Companion.REQUEST_CODE_STORAGE_PERMISSION)
    private fun onClickRequestPermissionStorageButton() {
        if (hasCameraPermission()) {
            // Have permission, do things!
            Toast.makeText(this, "TODO: Storage things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_storage_rationale_message),
                Companion.REQUEST_CODE_STORAGE_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    @AfterPermissionGranted(Companion.REQUEST_CODE_LOCATION_AND_CONTACTS_PERMISSION)
    private fun onClickRequestPermissionLocationAndContactsButton() {
        if (hasLocationAndContactsPermissions()) {
            // Have permissions, do things!
            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.permission_location_and_contacts_rationale_message),
                Companion.REQUEST_CODE_LOCATION_AND_CONTACTS_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS
            )
        }
    }
    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
    }

    private fun hasLocationAndContactsPermissions(): Boolean {
        return EasyPermissions.hasPermissions(this,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
        )
    }

    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    companion object {
        private const val REQUEST_CODE_CAMERA_PERMISSION = 123
        private const val REQUEST_CODE_STORAGE_PERMISSION = 124
        private const val REQUEST_CODE_LOCATION_AND_CONTACTS_PERMISSION = 125
    }
}