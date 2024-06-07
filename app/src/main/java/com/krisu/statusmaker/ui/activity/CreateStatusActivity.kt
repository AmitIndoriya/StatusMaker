package com.krisu.statusmaker.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import com.krisu.statusmaker.R
import com.krisu.statusmaker.databinding.ActCreateStatusLayoutBinding
import com.krisu.statusmaker.ui.adapter.ViewPagerAdapter
import com.krisu.statusmaker.ui.callback.TextEditListener
import com.krisu.statusmaker.ui.fragment.AlignmentFragment
import com.krisu.statusmaker.ui.fragment.ColorFragment
import com.krisu.statusmaker.ui.fragment.CreateStatusFragment
import com.krisu.statusmaker.ui.fragment.FontFragment
import com.krisu.statusmaker.ui.fragment.ShadowFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CreateStatusActivity : BaseActivity() {

    lateinit var binding: ActCreateStatusLayoutBinding


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActCreateStatusLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addFragment()
        setStatusBarColor(R.color.white)
    }


    fun addFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container_view,
                CreateStatusFragment.getInstance(),
                "CreateStatusFragment"
            )
            .disallowAddToBackStack()
            .commit()
    }

    fun replaceFragment(
        @IdRes containerViewId: Int,
        fragment: Fragment,
        fragmentTag: String,
        @Nullable backStackStateName: String?
    ) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment, fragmentTag)
            .addToBackStack(backStackStateName)
            .commit()
    }


}