package com.krisu.statusmaker.ui.activity

/*import com.theartofdev.edmodo.cropper.CropImage*/

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.canhub.cropper.CropImage
import com.canhub.cropper.CropImageActivity
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.canhub.cropper.sample.SampleCustomActivity
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActProfileLayoutBinding
import com.krisu.statusmaker.model.ProfileDetailModel
import com.krisu.statusmaker.ui.dialog.ProfileImgChooserBottomSheet
import com.krisu.statusmaker.utils.PermissionHelper
import com.krisu.statusmaker.utils.PreferenceConstant
import com.krisu.statusmaker.utils.Utils
import com.krisu.statusmaker.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import com.canhub.cropper.CropImage.ActivityResult
import com.krisu.statusmaker.utils.IntentConstants

@AndroidEntryPoint
class ProfileActivity : BaseActivity(), OnClickListener {
    private val viewModel by viewModels<ProfileViewModel>()
    private lateinit var binding: ActProfileLayoutBinding
    private val permissionHelper = PermissionHelper(this)
    private val profileDetail = ProfileDetailModel()
    private val AVATAR_REQ_CODE = 201
    private var imageuri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()
        binding = ActProfileLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
        setTopAndBottomMarginOfScreen()
        setData()
        addObservers()
    }

    private fun addObservers() {
        viewModel.avatarListLD.observe(this) {
            val avatarId = Utils.getIntInSP(this, PreferenceConstant.AVATAR_ID)
            if (avatarId != -1) {
                binding.profileImg.setImageDrawable(it[avatarId]?.drawable)
            }
        }
    }

    private fun setData() {
        if (Utils.getBooleanInSP(this, PreferenceConstant.IS_AVATAR_SELECTED)) {
            viewModel.getAvatarList()
        } else {
            if (!TextUtils.isEmpty(Utils.getStringInSP(this, PreferenceConstant.PROFILE_IMG))) {
                imageuri = Uri.parse(Utils.getStringInSP(this, PreferenceConstant.PROFILE_IMG))
                Picasso.with(this).load(Utils.getStringInSP(this, PreferenceConstant.PROFILE_IMG))
                    .into(binding.profileImg)
            }
        }

        if (!TextUtils.isEmpty(Utils.getStringInSP(this, PreferenceConstant.PROFILE_NAME))) {
            binding.nameEt.setText(Utils.getStringInSP(this, PreferenceConstant.PROFILE_NAME))
        }
        if (!TextUtils.isEmpty(Utils.getStringInSP(this, PreferenceConstant.MOBILE_NUMBER))) {
            binding.phoneNumberEt.setText(
                Utils.getStringInSP(
                    this, PreferenceConstant.MOBILE_NUMBER
                )
            )
        }
    }

    private fun setTopAndBottomMarginOfScreen() {
        val layoutParams = binding.toolbar.layoutParams as RelativeLayout.LayoutParams
        layoutParams.topMargin = getStatusBarHeight()
        binding.toolbar.layoutParams = layoutParams/*  layoutParams = binding.relativelayout.layoutParams as RelativeLayout.LayoutParams
          layoutParams.bottomMargin = getNavigationBarHeight()
          binding.relativelayout.layoutParams = layoutParams*/
    }

    private fun setListeners() {
        binding.backIv.setOnClickListener {
            finish()
        }
        binding.profileImgContainer.setOnClickListener {
            val modal = ProfileImgChooserBottomSheet()
            supportFragmentManager.let { modal.show(it, ProfileImgChooserBottomSheet.TAG) }/*showImagePicDialog()
               */
        }

        binding.continueBtn.setOnClickListener {
            val isAvatarSelected = Utils.getBooleanInSP(
                this, PreferenceConstant.IS_AVATAR_SELECTED
            )
            if ((imageuri != null || isAvatarSelected) && !TextUtils.isEmpty(binding.nameEt.text)) {
                profileDetail.name = binding.nameEt.text.toString()
                profileDetail.mobileNumber = binding.phoneNumberEt.text.toString()
                Utils.saveStringInSP(
                    this, PreferenceConstant.PROFILE_NAME, binding.nameEt.text.toString()
                )
                Utils.saveStringInSP(
                    this, PreferenceConstant.MOBILE_NUMBER, binding.phoneNumberEt.text.toString()
                )
                /* val returnIntent = Intent()*//*   returnIntent.putExtra("profile_name", binding.nameEt.text.toString())
                   returnIntent.putExtra("mobile_number", binding.phoneNumberEt.text.toString())
                   returnIntent.putExtra("img_url", Utils.getStringInSP(this, "img_url"))*//*
                setResult(RESULT_OK, returnIntent)*/
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showImagePicDialog() {
        val options = arrayOf("Camera", "Gallery")
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Image From")
        builder.setItems(options) { _, _ ->
            if (!permissionHelper.checkCameraPermission()) {
                permissionHelper.requestCameraPermission()
            } else {
                openGallery()
            }
        }
        builder.create().show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionHelper.CAMERA_REQUEST -> {
                if (grantResults.isNotEmpty()) {
                    val camera_accepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val writeStorageaccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (camera_accepted && writeStorageaccepted) {
                        openGallery()
                    } else {
                        Toast.makeText(
                            this, "Please Enable Camera and Storage Permissions", Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }

            permissionHelper.STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty()) {
                    val writeStorageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    if (writeStorageAccepted) {
                        openGallery()
                    } else {
                        Toast.makeText(this, "Please Enable Storage Permissions", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    fun openGallery() {
        if (!permissionHelper.checkCameraPermission()) {
            permissionHelper.requestCameraPermission()
        } else {
            val intent = Intent(this, CropImageActivity::class.java)
            startActivityForResult(intent, 1001)
        }

    }

    fun openCamera() {
        if (!permissionHelper.checkCameraPermission()) {
            permissionHelper.requestCameraPermission()
        } else {
            val intent = Intent(this, CropImageActivity::class.java)
            startActivityForResult(intent, 1001)
        }
    }

    fun openAvatar() {
        startActivityForResult(Intent(this, AvatarActivity::class.java), AVATAR_REQ_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {


            if (requestCode == 1001) {
                if (resultCode == RESULT_OK) {
                    val result: ActivityResult =
                        data.extras?.getParcelable(CropImage.CROP_IMAGE_EXTRA_RESULT)!!
                    imageuri = result.uriContent
                    profileDetail.imageUrl = imageuri.toString()
                    Utils.saveBooleanInSP(this, PreferenceConstant.IS_AVATAR_SELECTED, false)
                    Utils.saveStringInSP(this, PreferenceConstant.PROFILE_IMG, imageuri.toString())
                    Utils.saveIntInSP(this, PreferenceConstant.AVATAR_ID, -1)
                    Picasso.with(this).load(imageuri).into(binding.profileImg)
                }
            } else if (requestCode == AVATAR_REQ_CODE) {
                if (resultCode == RESULT_OK) {
                    val avatarId = data.getIntExtra(IntentConstants.AVATAR_ID, -1)
                    if (avatarId != null && avatarId != -1) {
                        Utils.saveBooleanInSP(this, PreferenceConstant.IS_AVATAR_SELECTED, true)
                        Utils.saveStringInSP(this, PreferenceConstant.PROFILE_IMG, "")
                        Utils.saveIntInSP(this, PreferenceConstant.AVATAR_ID, avatarId)
                        viewModel.getAvatarList()
                    }
                }
            }
        }

    }
    /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (data != null) {


             if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                 val result: CropImage.ActivityResult = CropImage.getActivityResult(data)
                 if (resultCode == RESULT_OK) {
                     imageuri = result.uri
                     profileDetail.imageUrl = imageuri.toString()
                     Utils.saveBooleanInSP(this, PreferenceConstant.IS_AVATAR_SELECTED, false)
                     Utils.saveStringInSP(this, PreferenceConstant.PROFILE_IMG, imageuri.toString())
                     Utils.saveIntInSP(this, PreferenceConstant.AVATAR_ID, -1)
                     Picasso.with(this).load(imageuri).into(binding.profileImg)
                 }
             } else if (requestCode == AVATAR_REQ_CODE) {
                 if (resultCode == RESULT_OK) {
                     val avatarId = data.getIntExtra(IntentConstants.AVATAR_ID, -1)
                     if (avatarId != null && avatarId != -1) {
                         Utils.saveBooleanInSP(this, PreferenceConstant.IS_AVATAR_SELECTED, true)
                         Utils.saveStringInSP(this, PreferenceConstant.PROFILE_IMG, "")
                         Utils.saveIntInSP(this, PreferenceConstant.AVATAR_ID, avatarId)
                         viewModel.getAvatarList()
                     }
                 }
             }
         }
     }*/

    override fun onClick(v: View) {
        when (v.id) {
            R.id.back_iv -> {
                finish()
            }
        }
    }
}