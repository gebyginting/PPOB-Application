package com.mobile.pacificaagent.ui.tagihanInternet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.model.Item
import com.mobile.pacificaagent.databinding.FragmentInternetCekTagihanBinding

class InternetCekTagihanFragment : Fragment() {
    private var _binding: FragmentInternetCekTagihanBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentInternetCekTagihanBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val namaProvider = arguments?.let {
            InternetCekTagihanFragmentArgs.fromBundle(it).namaProvider
        }
        binding.namaProvider.text = namaProvider

        val dataItem = listOf(
            Item("Cek Tangihan ${namaProvider}", "0"),
        )

        val adapter = ItemAdapter(dataItem) { selectedItem ->
            val action = InternetCekTagihanFragmentDirections
                .actionInternetCekTagihanFragmentToKonfirmasiWifiFragment(selectedItem.nama, selectedItem.harga)
            findNavController().navigate(action)          }

        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItem.adapter = adapter
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}