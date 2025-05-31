package com.mobile.pacificaagent.ui.tokenListrik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.FragmentTokenListrikBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.viewmodel.ProdukPrabayarViewModel

class TokenListrikFragment : Fragment() {
    private var _binding: FragmentTokenListrikBinding? = null
    private val binding get() = _binding!!
    private val produkPrabayarViewModel: ProdukPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private val dataPulsa = listOf(
        Item("20.000", "Rp20.570"),
        Item("50.000", "Rp50.570"),
        Item("100.000", "Rp100.570"),
        Item("200.000", "Rp200.570"),
        Item("500.000", "Rp500.570"),
        Item("1.000.000", "Rp1.000.570"),
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTokenListrikBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupItem()
        setupBackButton()
    }

    private fun setupItem() {
        val adapter = ItemAdapter(dataPulsa) { selectedItem ->
            val noMeter = binding.inputNomorMeter.text.toString() // pindahkan ke sini
            if (noMeter.isBlank()) {
                Toast.makeText(requireContext(), "Harap isi nomor meter dahulu", Toast.LENGTH_SHORT).show()
            } else {
                produkPrabayarViewModel.produkPrabayar("KT-00001", noMeter)
                val action = TokenListrikFragmentDirections
                    .actionTokenListrikFragmentToKonfirmasiTokenFragment(noMeter, selectedItem.nama, selectedItem.harga)
                findNavController().navigate(action)
            }
        }

        binding.rvItem.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvItem.adapter = adapter
    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }



}