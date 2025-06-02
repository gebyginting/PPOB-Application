package com.mobile.pacificaagent.ui.topupsaldo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentTopUpSaldoPembayaranBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.utils.Helper
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TopUpSaldoPembayaranFragment : Fragment() {
    private var _binding: FragmentTopUpSaldoPembayaranBinding? = null
    private val binding get() = _binding!!
    private val depositiVewModel: DepositViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTopUpSaldoPembayaranBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeDepositStatus()
        setupButtons()
    }

    private fun observeDepositStatus() {
        viewLifecycleOwner.lifecycleScope.launch {
            depositiVewModel.depositState.collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                    }

                    is ResultState.Success -> {
                        val data = result.data.data
                        setupDetailPembayaran(data.detail.subDetail.amount, data.detail.va, data.detail.bank)
                    }

                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupDetailPembayaran(nominal: Int, nomorVA: String, metode: String) {
        arguments?.let {
            val logoBank = TopUpSaldoPembayaranFragmentArgs.fromBundle(it).logoBank

            with(binding) {
                val batasWaktu = Calendar.getInstance().apply {
                    add(Calendar.HOUR_OF_DAY, 24)
                }
                val sdf = SimpleDateFormat("dd MMMM yyyy 'pukul' HH:mm", Locale("id", "ID"))
                val batasWaktuString = sdf.format(batasWaktu.time)
                tvNoVa.text = nomorVA
                tvTotalPembayaran.text = Helper.formatToRupiah(nominal)
                ivLogoBank.setImageResource(logoBank)
                tvTanggalKadaluarsa.text = batasWaktuString
            }
        }
    }

    private fun setupButtons() {
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.selesaiBtn.setOnClickListener {
            findNavController().popBackStack(R.id.navigation_home, false)

        }
    }
}