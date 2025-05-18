package com.mobile.pacificaagent.ui.pulsadata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.FragmentPaketDataBinding

class PaketDataFragment : Fragment() {
    private var _binding: FragmentPaketDataBinding? = null
    private val binding get() = _binding!!

    private val dataPulsa = listOf(
        Item("Kuota 20GB  30Hari Fredom Internet", "Rp50.500"),
        Item("Kuota 28GB  30Hari Fredom Internet", "Rp60.500"),
        Item("Kuota 37GB  30Hari Fredom Internet", "Rp75.500"),
        Item("Kuota 43GB  30Hari Fredom Internet", "Rp83.500"),
    )

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
        val adapter = ItemAdapter(dataPulsa) { selectedItem ->
            val action = PulsaDataFragmentDirections
                .actionPulsaDataFragmentToKonfirmasiPulsaFragment(selectedItem.nama, selectedItem.harga)
            findNavController().navigate(action)           }

        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItem.adapter = adapter
    }

}