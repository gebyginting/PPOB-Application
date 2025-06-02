package com.mobile.pacificaagent.ui.topupsaldo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.FragmentTopUpNominalSaldoBinding
import com.mobile.pacificaagent.utils.Helper


class TopUpNominalSaldoFragment : Fragment() {

    private var _binding: FragmentTopUpNominalSaldoBinding? = null
    private val binding get() = _binding!!
    private var nominal: Int = 0

    private val pilihanNominal = listOf(
        Item(nama = "10.000"),
        Item(nama = "50.000"),
        Item(nama = "100.000"),
        Item(nama = "250.000"),
        Item(nama = "500.000"),
        Item(nama = "1.000.000"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTopUpNominalSaldoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPilihanNominal()
        setupDepositButton()
        showValidasiPembayaran()
        handleLanjutPembayaran()
    }

    private fun setupPilihanNominal() {
        val adapter = ItemAdapter(pilihanNominal, showHarga = false, enableSelection = true) { selectedItem ->
            nominal = Helper.convertRupiah(selectedItem.nama).toInt()

            binding.tvNominal.text = Helper.formatRupiah(nominal)
        }
        val flexboxLayoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            flexWrap = FlexWrap.WRAP
            justifyContent = JustifyContent.CENTER
        }

        binding.rvItem.layoutManager = flexboxLayoutManager
        binding.rvItem.adapter = adapter
    }


    private fun setupDepositButton() {
        binding.pilihPembayaranBtn.setOnClickListener {
            val action = TopUpNominalSaldoFragmentDirections
                .actionTopUpNominalSaldoFragmentToTopUpPilihPembayaranFragment(nominal)
            findNavController().navigate(action)
        }
    }
    private fun showValidasiPembayaran() {
        findNavController().currentBackStackEntry?.savedStateHandle?.let { handle ->
            handle.getLiveData<Int>("nominal").observe(viewLifecycleOwner) { nominal ->
                val logoBank = handle.get<Int>("logoBank")
                val metode = handle.get<String>("metode")
                if (metode != null) {
                    val bottomSheet = logoBank?.let { ValidasiDepositBottomSheet.newInstance(nominal.toFloat(), it, metode) }
                    bottomSheet?.show(parentFragmentManager, "KonfirmasiBottomSheet")
                    handle.remove<Int>("nominal")
                    handle.remove<Int>("logoBank")
                    handle.remove<String>("metode")
                }
            }
        }
    }

    private fun handleLanjutPembayaran() {
        parentFragmentManager.setFragmentResultListener(
            "lanjut_ke_pembayaran", viewLifecycleOwner
        ) { _, result ->
            val nominal = result.getInt("nominal")
            val logoBank = result.getInt("logoBank")

            val action = TopUpNominalSaldoFragmentDirections
                .actionTopUpNominalSaldoFragmentToTopUpSaldoPembayaranFragment(nominal, logoBank)

            findNavController().navigate(action)
        }
    }

}