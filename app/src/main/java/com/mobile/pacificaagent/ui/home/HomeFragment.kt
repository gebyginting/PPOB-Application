package com.mobile.pacificaagent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import autoScroll
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.adapter.PromoAdapter
import com.mobile.pacificaagent.databinding.FragmentHomeBinding
import com.mobile.pacificaagent.utils.promoList


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.tvGreeting
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        setupPromo()
        setupServiceButton()

        return root
    }

    private fun setupServiceButton() {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.nav_host_fragment_activity_main, false)
            .build()

        binding.ivPulsaData.setOnClickListener {
            findNavController().navigate(R.id.pulsaDataFragment, null, navOptions)
        }

        binding.ivTokenListrik.setOnClickListener {
            findNavController().navigate(R.id.tokenListrikFragment, null, navOptions)
        }

        binding.ivTagihanInternet.setOnClickListener {
            findNavController().navigate(R.id.internetPilihFragment, null, navOptions)
        }

        binding.ivTopUpEWallet.setOnClickListener {
            findNavController().navigate(R.id.eWalletPilihFragment, null, navOptions)
        }

        binding.ivTopUpSaldo.setOnClickListener {
            findNavController().navigate(R.id.topUpNominalSaldoFragment, null, navOptions)
        }
    }

    private fun setupPromo() {
        val adapter = PromoAdapter(promoList)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.rvPromo.layoutManager = layoutManager
        binding.rvPromo.adapter = adapter

        binding.rvPromo.autoScroll(
            scope = viewLifecycleOwner.lifecycleScope,
            interval = 4000,
            getItemCount = { promoList.size }
        )
    }

//    private fun setupPromo() {
//        val adapter = PromoAdapter(promoList)
//
//        binding.rvPromo.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        binding.rvPromo.adapter = adapter
//
//        val snapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(binding.rvPromo)
//
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}