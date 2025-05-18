package com.mobile.pacificaagent.ui.tagihanInternet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.adapter.PilihanItemAdapter
import com.mobile.pacificaagent.data.model.PilihanItem
import com.mobile.pacificaagent.databinding.FragmentInternetPilihBinding

class InternetPilihFragment : Fragment() {

    private var _binding: FragmentInternetPilihBinding? = null
    private val binding get() = _binding!!

    private val dataPilihan = listOf(
        PilihanItem(R.drawable.ic_wifi, "First Media"),
        PilihanItem(R.drawable.ic_wifi, "Telkom Indonesia"),
        PilihanItem(R.drawable.ic_wifi, "XL Home"),
    )

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
        val adapter = PilihanItemAdapter(dataPilihan) { selectedItem ->
            val action = InternetPilihFragmentDirections
                .actionInternetPilihFragmentToInternetCekTagihanFragment(selectedItem.nama)
            findNavController().navigate(action)
        }
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