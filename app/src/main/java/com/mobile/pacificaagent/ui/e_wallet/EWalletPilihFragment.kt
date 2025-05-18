package com.mobile.pacificaagent.ui.e_wallet

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
import com.mobile.pacificaagent.databinding.FragmentEWalletPilihBinding


class EWalletPilihFragment : Fragment() {
    private var _binding: FragmentEWalletPilihBinding? = null
    private val binding get() = _binding!!

    private val dataPilihan = listOf(
        PilihanItem(R.drawable.ic_ewallet, "DANA"),
        PilihanItem(R.drawable.ic_ewallet, "OVO"),
        PilihanItem(R.drawable.ic_ewallet, "GOPAY")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEWalletPilihBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = PilihanItemAdapter(dataPilihan) { selectedItem ->
            val action = EWalletPilihFragmentDirections
                .actionEWalletPilihFragmentToEWalletNominalFragment(selectedItem.nama)
            findNavController().navigate(action)        }

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