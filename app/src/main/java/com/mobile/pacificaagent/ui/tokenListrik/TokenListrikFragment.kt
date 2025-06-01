package com.mobile.pacificaagent.ui.tokenListrik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.FragmentTokenListrikBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.viewmodel.ProdukPrabayarViewModel
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TokenListrikFragment : Fragment() {
    private var _binding: FragmentTokenListrikBinding? = null
    private val binding get() = _binding!!
    private val produkPrabayarViewModel: ProdukPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
    private val dataPulsa = listOf(
        Item(nama = "20.000", harga = "Rp20.570"),
        Item(nama = "50.000", harga = "Rp50.570"),
        Item(nama = "100.000", harga = "Rp100.570"),
        Item(nama = "200.000", harga = "Rp200.570"),
        Item(nama = "500.000", harga = "Rp500.570"),
        Item(nama = "1.000.000", harga = "Rp1.000.570"),
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

        setupDataToken()
        setupBackButton()
    }

    private fun setupDataToken() {
        val adapter = ItemAdapter(dataPulsa) { selectedItem ->
            val noMeter = binding.inputNomorMeter.text.toString() // pindahkan ke sini            binding.progressBar.visibility = View.VISIBLE
            if (noMeter.isBlank()) {
                Toast.makeText(requireContext(), "Harap isi nomor meter dahulu", Toast.LENGTH_SHORT).show()
            } else {
//                produkPrabayarViewModel
                produkPrabayarViewModel.detailProdukPrabayar(selectedItem.productId)

                viewLifecycleOwner.lifecycleScope.launch {
                    produkPrabayarViewModel.detailProdukState.collectLatest { detailResult ->
                        when (detailResult) {
                            is ResultState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                            }

                            is ResultState.Success -> {
                                delay(1000) // opsional: jeda 1 detik untuk efek transisi
                                binding.progressBar.visibility = View.GONE
                                val action = TokenListrikFragmentDirections
                                    .actionTokenListrikFragmentToKonfirmasiTokenFragment(selectedItem.productId)
                                findNavController().navigate(action)
                            }

                            is ResultState.Error -> {
                                binding.progressBar.visibility = View.GONE
                                Toast.makeText(requireContext(), "Gagal ambil detail produk", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
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