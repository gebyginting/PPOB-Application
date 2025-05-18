package com.mobile.pacificaagent.ui.transaksi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentRiwayatTransaksiBinding

class RiwayatTransaksiFragment : Fragment() {

    private var _binding: FragmentRiwayatTransaksiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRiwayatTransaksiBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDetailPembayaran()
        setupBackButton()
    }

    private fun setupDetailPembayaran() {
        arguments?.let {
            val jenisTransaksi = RiwayatTransaksiFragmentArgs.fromBundle(it).jenisTransaksi
            val statusTransaksi = RiwayatTransaksiFragmentArgs.fromBundle(it).statusTransaksi
            val namaTransaksi = RiwayatTransaksiFragmentArgs.fromBundle(it).namaTransaksi
            val nominalTransaksi = RiwayatTransaksiFragmentArgs.fromBundle(it).nominalTransaksi

            with(binding) {
                when (jenisTransaksi) {
                    "DANA" -> ivTransaksi.setImageResource(R.drawable.ic_dana)
                    "Listrik Token" -> ivTransaksi.setImageResource(R.drawable.ic_pln)
                }
                if (jenisTransaksi == "Listrik Token") {
                    layoutKodeToken.visibility = View.VISIBLE
                }
                tvJenisValue.text = jenisTransaksi
                tvTitle.text = "Pembelian $namaTransaksi"
                tvStatusLabel.text = statusTransaksi
                tvHargaValue.text = nominalTransaksi
                tvTotalTagihanValue.text = nominalTransaksi
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