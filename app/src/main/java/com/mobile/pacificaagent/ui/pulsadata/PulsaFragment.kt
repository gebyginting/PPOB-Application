package com.mobile.pacificaagent.ui.pulsadata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.FragmentPulsaBinding

class PulsaFragment : Fragment() {

    private var _binding: FragmentPulsaBinding? = null
    private val binding get() = _binding!!

    private val dataPulsa = listOf(
        Item("5.000", "Rp5.500"),
        Item("10.000", "Rp10.500"),
        Item("20.000", "Rp 20.500"),
        Item("25.000", "Rp 25.500"),
        Item("25.000", "Rp 25.500"),
        Item("25.000", "Rp 25.500"),
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPulsaBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ItemAdapter(dataPulsa) { selectedItem ->
            val action = PulsaDataFragmentDirections
                .actionPulsaDataFragmentToKonfirmasiPulsaFragment(selectedItem.nama, selectedItem.harga)
            findNavController().navigate(action)             }

        binding.rvItem.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvItem.adapter = adapter
    }
}