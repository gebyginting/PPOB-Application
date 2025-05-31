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
import androidx.recyclerview.widget.GridLayoutManager
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
    ): View? {

        _binding = FragmentPaketDataBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUserNumber()
    }

    private fun getUserNumber() {
        viewLifecycleOwner.lifecycleScope.launch {
            produkPrabayarViewModel.phoneNumber.collect { number ->
                if (number.isNotBlank()) {
                    setupPaketData(number)
                }
            }
        }
    }
    private fun setupPaketData(number: String) {
        produkPrabayarViewModel.produkPrabayar("KT-00002", number)
        viewLifecycleOwner.lifecycleScope.launch {
            produkPrabayarViewModel.produkPrabayarState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> {
                        Toast.makeText(requireContext(), "LOADING", Toast.LENGTH_SHORT).show()
                    }
                    is ResultState.Success -> {
                        val data = result.data.data
                        val itemList = data.map { dataItem ->
                            Item(
                                nama = dataItem.productName,
                                harga = formatRupiah(dataItem.price)
                            )
                        }
                        val adapter = ItemAdapter(itemList) { selectedItem ->
                            val action = PulsaDataFragmentDirections
                                .actionPulsaDataFragmentToKonfirmasiPulsaFragment(selectedItem.nama, selectedItem.harga)
                            findNavController().navigate(action)
                        }
                        binding.rvItem.layoutManager = GridLayoutManager(requireContext(), 2)
                        binding.rvItem.adapter = adapter
                    }
                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
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