package com.krisu.statusmaker.ui.activity

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActCreateStatusLayoutBinding
import com.krisu.statusmaker.ui.fragment.ChangeBackgroundFragment
import com.krisu.statusmaker.ui.fragment.CreateStatusFragment
import com.krisu.statusmaker.ui.fragment.ShareStatusFragment
import com.krisu.statusmaker.viewmodel.CreateStatusViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateStatusActivity : BaseActivity(), OnClickListener, ChangeBackgroundFragment.CallBack {

    lateinit var binding: ActCreateStatusLayoutBinding
    val viewModel by viewModels<CreateStatusViewModel>()

    var bgUrlLD = MutableLiveData<String>()
    private var fragTag = ""
    var suvicharStr = ""
    var textSize = 0f
    var scaleFact = 0f
    var textStrokeWidth = 5f
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCreateStatusLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment()
        setStatusBarColor(R.color.white)
        setListeners()
    }


    @Deprecated("Deprecated in Java")
    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is ChangeBackgroundFragment) {
            fragment.setCallbackListener(this)
        }
    }

    private fun addFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container_view,
                ChangeBackgroundFragment.getInstance(),
                "ChangeBackgroundFragment"
            )
            .disallowAddToBackStack()
            .commit()
    }

    private fun replaceFragment(
        fragment: Fragment,
        fragmentTag: String,
        backStackStateName: String,
        isAddToBackStack: Boolean = true
    ) {
        fragTag = "fragmentTag"
        if (isAddToBackStack) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment, fragmentTag)
                .addToBackStack(backStackStateName)
                .commit()
        } else {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment, fragmentTag)
                .commit()
        }

    }


    private fun setListeners() {
        binding.changeBgIv.setOnClickListener(this)
        binding.nextTv.setOnClickListener(this)
        binding.shareTv.setOnClickListener(this)
    }

    fun changeToolbar(title: String, fragNo: Int) {

        when (fragNo) {
            0 -> {
                binding.changeBgIv.visibility = View.GONE
                binding.nextTv.visibility = View.GONE
                binding.shareTv.visibility = View.GONE
            }

            1 -> {
                binding.changeBgIv.visibility = View.VISIBLE
                binding.shareTv.visibility = View.GONE
                binding.nextTv.visibility = View.VISIBLE
            }

            2 -> {
                binding.changeBgIv.visibility = View.VISIBLE
                binding.nextTv.visibility = View.GONE
                binding.shareTv.visibility = View.VISIBLE
            }
        }
        binding.titleTv.text = title
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.change_bg_iv -> {
                replaceFragment(
                    ChangeBackgroundFragment.getInstance(),
                    "ChangeBackgroundFragment",
                    "ChangeBackgroundFragment"
                )
                changeToolbar(resources.getString(R.string.select_background), 0)
            }

            R.id.next_tv -> {
                if (!TextUtils.isEmpty(suvicharStr)) {
                    val fragment =
                        supportFragmentManager.findFragmentByTag("CreateStatusFragment") as CreateStatusFragment
                    //fragment.saveImageInCache()
                    replaceFragment(
                        ShareStatusFragment.getInstance(),
                        "ShareStatusFragment",
                        "ShareStatusFragment"
                    )
                } else {
                    Toast.makeText(
                        this@CreateStatusActivity,
                        "Please add your status",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

            R.id.share_tv -> {
                val fragment =
                    supportFragmentManager.findFragmentByTag("ShareStatusFragment") as ShareStatusFragment
                fragment.shareStatus()

            }
        }
    }

    override fun changeBackground(url: String) {
        bgUrlLD.value = url
        when (fragTag) {
            "" -> {
                replaceFragment(
                    CreateStatusFragment.getInstance(),
                    "CreateStatusFragment",
                    "CreateStatusFragment",
                    false
                )
            }

            else -> {
                supportFragmentManager.popBackStack(
                    "ChangeBackgroundFragment",
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
        }
    }
}