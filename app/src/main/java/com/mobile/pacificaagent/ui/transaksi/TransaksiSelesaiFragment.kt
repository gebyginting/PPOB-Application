package com.mobile.pacificaagent.ui.transaksi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.adapter.TransaksiAdapter
import com.mobile.pacificaagent.data.model.Transaksi
import com.mobile.pacificaagent.databinding.FragmentTransaksiSelesaiBinding

class TransaksiSelesaiFragment : Fragment() {
    private var _binding: FragmentTransaksiSelesaiBinding? = null
    private val binding get() = _binding!!

    private val dataTransaksi = listOf(
        Transaksi("DANA", "Successfull", R.drawable.ic_ewallet, "Ewallet Dana", "25 Juni 2025 08.00", "-200.000"),
        Transaksi("DANA", "Successfull",R.drawable.ic_ewallet, "Ewallet Dana", "25 Juni 2025 08.00", "-200.000"),
        Transaksi("DANA", "Successfull",R.drawable.ic_ewallet, "Ewallet Dana", "25 Juni 2025 08.00", "-200.000"),
        Transaksi("Listrik Token", "Successfull",R.drawable.ic_pln, "Listrik Token", "25 Juni 2025 08.00", "-200.000"),
    )

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
        val adapter = TransaksiAdapter(dataTransaksi) { selectedItem ->
            val action = TransaksiFragmentDirections
                .actionTransaksiFragmentToRiwayatTransaksiFragment(selectedItem.jenisTransaksi, selectedItem.statusTransaksi, selectedItem.namaTransaksi, selectedItem.nominalTransaksi)
            findNavController().navigate(action)            }

        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItem.adapter = adapter
    }
}