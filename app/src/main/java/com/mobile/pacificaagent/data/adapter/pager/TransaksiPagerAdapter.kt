package com.mobile.pacificaagent.data.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mobile.pacificaagent.ui.transaksi.TransaksiProsesFragment
import com.mobile.pacificaagent.ui.transaksi.TransaksiSelesaiFragment

class TransaksiPagerAdapter (fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TransaksiProsesFragment()
            1 -> TransaksiSelesaiFragment()
            else -> TransaksiProsesFragment()
        }
    }
}