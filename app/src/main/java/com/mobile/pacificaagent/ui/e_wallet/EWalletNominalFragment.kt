package com.mobile.pacificaagent.ui.e_wallet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.FragmentEWalletNominalBinding

class EWalletNominalFragment : Fragment() {
    private var _binding: FragmentEWalletNominalBinding? = null
    private val binding get() = _binding!!

    private val dataItem = listOf(
        Item(nama = "5.000", harga = "Rp5.500"),
        Item(nama = "10.000", harga = "Rp10.500"),
        Item(nama = "20.000", harga = "Rp 20.500"),
        Item(nama = "25.000", harga = "Rp 25.500"),
        Item(nama = "25.000", harga = "Rp 25.500"),
        Item(nama = "25.000", harga = "Rp 25.500"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEWalletNominalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val namaEWallet = arguments?.let {
            EWalletNominalFragmentArgs.fromBundle(it).namaEWallet
        }
        binding.tvTitlePage.text = namaEWallet
        binding.namaEWallet.text = "Nomor $namaEWallet"

        val adapter = ItemAdapter(dataItem) { selectedItem ->
            val nomor = binding.inputNomor.text.toString() // pindahkan ke sini
            if (nomor.isBlank()) {
                Toast.makeText(requireContext(), "Harap isi nomor dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val action = EWalletNominalFragmentDirections
                    .actionEWalletNominalFragmentToKonfirmasiEWalletFragment(nomor, namaEWallet.toString(), selectedItem.nama, selectedItem.harga)
                findNavController().navigate(action)
            }
        }

        binding.rvItem.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvItem.adapter = adapter
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}