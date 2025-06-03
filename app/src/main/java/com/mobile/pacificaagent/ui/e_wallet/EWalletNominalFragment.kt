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
import com.mobile.pacificaagent.data.item.DummyItems.dataEWallet
import com.mobile.pacificaagent.databinding.FragmentEWalletNominalBinding

class EWalletNominalFragment : Fragment() {
    private var _binding: FragmentEWalletNominalBinding? = null
    private val binding get() = _binding!!

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

        val adapter = ItemAdapter(dataEWallet) { selectedItem ->
            val nomor = binding.inputNomor.text.toString()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}