package com.mobile.pacificaagent.ui.topupsaldo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.request.DepositRequest
import com.mobile.pacificaagent.databinding.ValidasiDepositBottomSheetBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.utils.Helper

class ValidasiDepositBottomSheet : BottomSheetDialogFragment() {

    private var _binding: ValidasiDepositBottomSheetBinding? = null
    private val binding get() = _binding!!
    private val depositiVewModel: DepositViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private var namaBank: String? = null
    private var nominal: Float? = null
    private var logoBank: Int? = null
    private var metodePembayaran: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nominal = it.getFloat("nominal")
            logoBank = it.getInt("logoBank")
            namaBank = it.getString("nama")
            metodePembayaran = it.getString("metode")
        }
        Log.d("METODE:",metodePembayaran.toString() )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ValidasiDepositBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvNominal.text = nominal?.let { Helper.formatRupiah(it.toInt()) }
        logoBank?.let { binding.imgLogoBank.setImageResource(it) }
        binding.tvNamaBank.text = namaBank
        binding.cancelBtn.setOnClickListener {
            dismiss()
            findNavController().popBackStack(R.id.navigation_home, false)
        }
        binding.depositBtn.setOnClickListener {
            val request = DepositRequest(
                metodePembayaran = metodePembayaran.toString(),
                jumlah = nominal?.toInt() ?: 0
            )
            depositiVewModel.deposit(request)
            // Kirim sinyal ke fragment asal
            parentFragmentManager.setFragmentResult(
                "lanjut_ke_pembayaran",
                bundleOf(
                    "nominal" to nominal,
                    "logoBank" to logoBank
                )
            )
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(nominal: Float, logoBank: Int, metode: String): ValidasiDepositBottomSheet {
            val fragment = ValidasiDepositBottomSheet()
            val args = Bundle()
            args.putFloat("nominal", nominal)
            args.putInt("logoBank", logoBank)
            args.putString("metode", metode)
            fragment.arguments = args
            return fragment
        }
    }
}