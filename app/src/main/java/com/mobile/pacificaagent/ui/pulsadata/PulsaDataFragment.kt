package com.mobile.pacificaagent.ui.pulsadata

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mobile.pacificaagent.data.adapter.pager.PulsaDataPagerAdapter
import com.mobile.pacificaagent.databinding.FragmentPulsaDataBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.viewmodel.ProdukPrabayarViewModel

class PulsaDataFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    private var _binding: FragmentPulsaDataBinding? = null
    private val binding get() = _binding!!

    private val produkPrabayarViewModel: ProdukPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPulsaDataBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        val adapter = PulsaDataPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Pulsa"
                1 -> "Paket Data"
                else -> ""
            }
        }.attach()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        produkPrabayarViewModel.setPhoneNumber("")
        getUserNumber()
        setupBackButton()
    }

    private fun getUserNumber() {
        binding.inputUserNumber.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.inputUserNumber.windowToken, 0)
                val number = binding.inputUserNumber.text.toString()
                if (number.isNotBlank()) {
                    produkPrabayarViewModel.setPhoneNumber(number)
                }
                true
            } else {
                false
            }
        }
    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}