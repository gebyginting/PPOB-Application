package com.mobile.pacificaagent.ui.pulsadata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.FragmentPaketDataBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.viewmodel.ProdukPrabayarViewModel
import com.mobile.pacificaagent.utils.Helper.formatRupiah
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaketDataFragment : Fragment() {

    private var _binding: FragmentPaketDataBinding? = null
    private val binding get() = _binding!!
    private val produkPrabayarViewModel: ProdukPrabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaketDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProdukPrabayar()
        fetchProdukWhenPhoneAvailable()
    }

    private fun fetchProdukWhenPhoneAvailable() {
        viewLifecycleOwner.lifecycleScope.launch {
            produkPrabayarViewModel.phoneNumber.collectLatest { number ->
                if (number.isNotBlank()) {
                    binding.tvEmptyMessage.visibility = View.GONE
                    produkPrabayarViewModel.loadProdukPaketData(number)
                }
            }
        }
    }

    private fun observeProdukPrabayar() {
        viewLifecycleOwner.lifecycleScope.launch {
            produkPrabayarViewModel.produkPaketDataState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> {
                        Toast.makeText(requireContext(), "Memuat data...", Toast.LENGTH_SHORT).show()
                    }

                    is ResultState.Success -> {
                        val data = result.data.data
                        if (data.isNullOrEmpty()) {
                            binding.tvEmptyMessage.visibility = View.VISIBLE
                            return@collectLatest
                        }

                        val itemList = data.map { dataItem ->
                            Item(
                                productId = dataItem.id,
                                nama = dataItem.productName,
                                harga = formatRupiah(dataItem.price)
                            )
                        }

                        val nomorHp = produkPrabayarViewModel.phoneNumber.value

                        val adapter = ItemAdapter(itemList) { selectedItem ->
                            // Tampilkan progress bar
                            binding.progressBar.visibility = View.VISIBLE

                            produkPrabayarViewModel.detailProdukPrabayar(selectedItem.productId)

                            viewLifecycleOwner.lifecycleScope.launch {
                                produkPrabayarViewModel.detailProdukState.collectLatest { detailResult ->
                                    when (detailResult) {
                                        is ResultState.Loading -> {
                                            // progressBar sudah tampil
                                        }

                                        is ResultState.Success -> {
                                            binding.progressBar.visibility = View.GONE
                                            val action = PulsaDataFragmentDirections
                                                .actionPulsaDataFragmentToKonfirmasiPulsaFragment(
                                                    selectedItem.productId,
                                                    nomorHp,
                                                    "data"
                                                )
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

                        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvItem.adapter = adapter
                    }

                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), "Terjadi kesalahan saat memuat data", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
