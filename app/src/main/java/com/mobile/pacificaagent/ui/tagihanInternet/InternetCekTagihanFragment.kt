package com.mobile.pacificaagent.ui.tagihanInternet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.data.request.pascabayar.TopUpWifiRequest
import com.mobile.pacificaagent.data.request.pascabayar.WifiBill
import com.mobile.pacificaagent.databinding.FragmentInternetCekTagihanBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.viewmodel.ProdukPascabayarViewModel
import com.mobile.pacificaagent.utils.Helper.formatRupiah
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InternetCekTagihanFragment : Fragment() {
    private var _binding: FragmentInternetCekTagihanBinding? = null
    private val binding get() = _binding!!

    private val produkPascabayarViewModel: ProdukPascabayarViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

    private var operatorId: String? = null
    private var pelangganId: String? = null
    private var operatorName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentInternetCekTagihanBinding.inflate(inflater, container, false).also {
        _binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArgs()
        setupUI()
    }

    private fun initArgs() {
        arguments?.let {
            val args = InternetCekTagihanFragmentArgs.fromBundle(it)
            operatorId = args.operatorId
            operatorName = args.operatorName
        }
    }

    private fun setupUI() = with(binding) {
        namaProvider.text = operatorName
        setupBackButton()

        inputIdTagihan.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                pelangganId = inputIdTagihan.text.toString().trim()

                if (pelangganId!!.isEmpty()) {
                    Toast.makeText(requireContext(), "Masukkan ID Pelanggan", Toast.LENGTH_SHORT).show()
                    return@setOnEditorActionListener true
                }

                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(inputIdTagihan.windowToken, 0)

                operatorId?.let { fetchTagihan(it, pelangganId!!) }
                true
            } else {
                false
            }
        }
    }


    private fun fetchTagihan(operatorId: String, pelangganId: String) {
        produkPascabayarViewModel.getTagihanWifi(operatorId, pelangganId)

        viewLifecycleOwner.lifecycleScope.launch {
            produkPascabayarViewModel.tegiahanWifiState.collectLatest { result ->
                when (result) {
                    is ResultState.Loading -> showLoading(true)

                    is ResultState.Success -> {
                        showLoading(false)
                        result.data.data.let { dataItem ->
                            setupRecyclerView(
                                listOf(
                                    Item(
                                        nama = dataItem.operator,
                                        desc = dataItem.paketWifi,
                                        harga = formatRupiah(dataItem.tagihan)
                                    )
                                )
                            )
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showError("Terjadi kesalahan: ${result.error}")
                    }
                }
            }
        }
    }

    private fun setupRecyclerView(items: List<Item>) {
        val adapter = ItemAdapter(items) { selectedItem ->
            val request = TopUpWifiRequest(
                wifiBill = WifiBill(
                    tagihan = selectedItem.harga.replace(".", "").toInt(),
                    pelangganId = pelangganId.toString(),
                    paketWifi = selectedItem.desc,
                    bulan = "Juni",
                    operator = selectedItem.nama
                )
            )
            val action = InternetCekTagihanFragmentDirections
                .actionInternetCekTagihanFragmentToKonfirmasiWifiFragment(
                    request
                )
            findNavController().navigate(action)
        }

        binding.rvItem.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            this.adapter = adapter
        }
    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
