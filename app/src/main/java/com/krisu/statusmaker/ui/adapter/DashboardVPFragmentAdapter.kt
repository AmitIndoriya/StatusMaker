package com.krisu.statusmaker.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class DashboardVPFragmentAdapter(fm: FragmentManager, val fragList: ArrayList<Fragment>) :
    FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return fragList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragList[position]
    }
}