package com.mobile.pacificaagent.data.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.pacificaagent.ui.pulsadata.PaketDataFragment
import com.mobile.pacificaagent.ui.pulsadata.PulsaFragment

class PulsaDataPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PulsaFragment()
            1 -> PaketDataFragment()
            else -> PulsaFragment()
        }
    }
}