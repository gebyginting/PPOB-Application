package com.mobile.pacificaagent.ui.topupsaldo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.adapter.TopUpPilihanPembayaranAdapter
import com.mobile.pacificaagent.data.model.PilihanItem
import com.mobile.pacificaagent.databinding.FragmentTopUpPilihPembayaranBinding

class TopUpPilihPembayaranFragment : Fragment() {
    private var _binding: FragmentTopUpPilihPembayaranBinding? = null
    private val binding get() = _binding!!
    private var pilihanNominal = 0

    private val pilihanPembayaran = listOf(
        PilihanItem(R.drawable.bca_logo, "Transfer Bank BCA", "BCAVA"),
        PilihanItem(R.drawable.bri_logo, "Transfer Bank BRI", "BRIVA"),
        PilihanItem(R.drawable.mandiri_logo, "Transfer Bank Mandiri", "MANDIRIVA"),
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTopUpPilihPembayaranBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getNominal()
        setupPilihanNominal()
    }

    private fun setupPilihanNominal() {
        val adapter = TopUpPilihanPembayaranAdapter(pilihanPembayaran) { selectedItem ->
            val navController = findNavController()

            navController.previousBackStackEntry?.savedStateHandle?.apply {
                set("nominal", pilihanNominal)
                set("logoBank", selectedItem.imgUrl)
                set("nama", selectedItem.nama)
                set("metode", selectedItem.metode)
            }

            navController.popBackStack()
        }

        binding.rvItem.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItem.adapter = adapter
    }

    private fun getNominal() {
        arguments?.let {
            val nominal = TopUpPilihPembayaranFragmentArgs.fromBundle(it).nominal
            pilihanNominal = nominal
        }
    }
}