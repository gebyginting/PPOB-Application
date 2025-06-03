package com.mobile.pacificaagent.ui.tokenListrik

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mobile.pacificaagent.data.adapter.ItemAdapter
import com.mobile.pacificaagent.data.item.DummyItems.dataPulsa
import com.mobile.pacificaagent.databinding.FragmentTokenListrikBinding

class TokenListrikFragment : Fragment() {
    private var _binding: FragmentTokenListrikBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTokenListrikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDataToken()
        setupBackButton()
    }

    private fun setupDataToken() {
        val adapter = ItemAdapter(dataPulsa) { selectedItem ->
            val noMeter = binding.inputNomorMeter.text.toString()
            if (noMeter.isBlank()) {
                Toast.makeText(requireContext(), "Harap isi nomor meter dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val nomorMeter = binding.inputNomorMeter.text.toString()
                if (nomorMeter.isNotBlank()) {
                    val action = TokenListrikFragmentDirections
                        .actionTokenListrikFragmentToKonfirmasiTokenFragment(nomorMeter,selectedItem.nama.replace(".", "").toInt())
                    findNavController().navigate(action)
                }
            }
        }

        binding.rvItem.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvItem.adapter = adapter
    }


    private fun setupBackButton() {
        binding.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}