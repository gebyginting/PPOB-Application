package com.mobile.pacificaagent.ui.topupsaldo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentTopUpSaldoPembayaranBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TopUpSaldoPembayaranFragment : Fragment() {
    private var _binding: FragmentTopUpSaldoPembayaranBinding? = null
    private val binding get() = _binding!!

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
        setupDetailPembayaran()
        setupButtons()
    }

    private fun setupDetailPembayaran() {
        arguments?.let {
            val logoBank = TopUpSaldoPembayaranFragmentArgs.fromBundle(it).logoBank
            val nominal = TopUpSaldoPembayaranFragmentArgs.fromBundle(it).nominal

            with(binding) {
//                val waktuSekarang = System.currentTimeMillis()
//                val deadlineMillis = waktuSekarang + 24 * 60 * 60 * 1000
//
//                val deadlineFormatted = SimpleDateFormat("dd MMMM yyyy, HH:mm", Locale("id", "ID"))
//                    .format(Date(deadlineMillis))
                val batasWaktu = Calendar.getInstance().apply {
                    add(Calendar.HOUR_OF_DAY, 24)
                }
                val sdf = SimpleDateFormat("dd MMMM yyyy 'pukul' HH:mm", Locale("id", "ID"))
                val batasWaktuString = sdf.format(batasWaktu.time)
                tvTotalPembayaran.text = "Rp $nominal"
                ivLogoBank.setImageResource(logoBank)
                tvTanggalKadaluarsa.text = "$batasWaktuString"
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