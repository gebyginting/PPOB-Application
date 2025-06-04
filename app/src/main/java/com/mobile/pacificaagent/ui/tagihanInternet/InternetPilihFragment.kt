package com.mobile.pacificaagent.ui.tagihanInternet

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
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.adapter.PilihanItemAdapter
import com.mobile.pacificaagent.data.model.PilihanItem
import com.mobile.pacificaagent.databinding.FragmentInternetPilihBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.viewmodel.ProdukPascabayarViewModel
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InternetPilihFragment : Fragment() {

    private var _binding: FragmentInternetPilihBinding? = null
    private val binding get() = _binding!!
    private val produkPascabayarViewModel: ProdukPascabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInternetPilihBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadWifiProviders()
        setupBackButton()
    }

    private fun loadWifiProviders() {
        produkPascabayarViewModel.loadWifiProviders()
        viewLifecycleOwner.lifecycleScope.launch {
            produkPascabayarViewModel.wifiProvidersState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }
                    is ResultState.Success -> {
                        showLoading(false)
                        val data = result.data.data
                        val itemList = data.map { dataItem ->
                            PilihanItem(
                                id = dataItem.id,
                                imgUrl = R.drawable.ic_wifi,
                                nama = dataItem.operatorName,
                            )
                        }

                        val adapter = PilihanItemAdapter(itemList) { selectedItem ->
                            val action = InternetPilihFragmentDirections
                                .actionInternetPilihFragmentToInternetCekTagihanFragment(selectedItem.id, selectedItem.nama)
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

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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