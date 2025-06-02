package com.mobile.pacificaagent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import autoScroll
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.adapter.PromoAdapter
import com.mobile.pacificaagent.databinding.FragmentHomeBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.utils.Helper
import com.mobile.pacificaagent.utils.ResultState
import com.mobile.pacificaagent.utils.UserPreference
import com.mobile.pacificaagent.utils.promoList
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPreference = UserPreference(requireContext())

        setupUser()
        setupPromo()
        setupServiceButton()
    }
    private fun setupUser() {
        val user = userPreference.getUser()
        user.let {
            binding.tvGreeting.text = "Hello, ${user?.name}"
        }
        userViewModel.getBalance()
        observeBalanceState()
    }

    private fun observeBalanceState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userViewModel.getBalanceState.collect { state ->
                    when (state) {
                        is ResultState.Loading -> {
                            binding.balanceLoading.visibility = View.VISIBLE
                            binding.userBalance.text = "Memuat..."
                        }
                        is ResultState.Success -> {
                            binding.balanceLoading.visibility = View.GONE
                            binding.userBalance.text = Helper.formatRupiah(state.data.data.balance)
                        }
                        is ResultState.Error -> {
                            binding.balanceLoading.visibility = View.GONE
                            binding.userBalance.text = "Gagal memuat"
                        }
                    }
                }
            }
        }
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