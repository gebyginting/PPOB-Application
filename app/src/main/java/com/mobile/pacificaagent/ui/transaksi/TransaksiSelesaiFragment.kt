package com.mobile.pacificaagent.ui.transaksi

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
import com.mobile.pacificaagent.data.adapter.TransaksiAdapter
import com.mobile.pacificaagent.databinding.FragmentTransaksiSelesaiBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.utils.Helper.toTransaksi
import com.mobile.pacificaagent.utils.ResultState
import kotlinx.coroutines.launch

class TransaksiSelesaiFragment : Fragment() {
    private var _binding: FragmentTransaksiSelesaiBinding? = null
    private val binding get() = _binding!!
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }
//    private val dataTransaksi = listOf(
//        Transaksi("DANA", "Successfull", R.drawable.ic_ewallet, "Ewallet Dana", "25 Juni 2025 08.00", "-200.000"),
//        Transaksi("DANA", "Successfull",R.drawable.ic_ewallet, "Ewallet Dana", "25 Juni 2025 08.00", "-200.000"),
//        Transaksi("DANA", "Successfull",R.drawable.ic_ewallet, "Ewallet Dana", "25 Juni 2025 08.00", "-200.000"),
//        Transaksi("Listrik Token", "Successfull",R.drawable.ic_pln, "Listrik Token", "25 Juni 2025 08.00", "-200.000"),
//    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTransaksiSelesaiBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListTransaksi()
    }

    private fun setupListTransaksi() {
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.historyState.collect { result ->
                when (result) {
                    is ResultState.Loading -> {
                        showLoading(true)
                    }

                    is ResultState.Success -> {
                        showLoading(false)
                        val filterItem = result.data.data.filter {
                            it.status.equals("SUCCESS", ignoreCase = true) ||
                                    it.status.equals("PAID", ignoreCase = true)
                        }

                        val adapter = TransaksiAdapter(filterItem.map { it.toTransaksi() }) { item ->
                            userViewModel.getHistoryDetail(item.id)
                            val action = TransaksiFragmentDirections
                                .actionTransaksiFragmentToRiwayatTransaksiFragment()
                            findNavController().navigate(action)
                        }

                        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
                        binding.rvItem.adapter = adapter
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        showToast(result.error)
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingOverlay.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}