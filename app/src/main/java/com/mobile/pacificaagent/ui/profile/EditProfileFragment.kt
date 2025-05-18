package com.mobile.pacificaagent.ui.profile

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGenderDropdown()
        setupButtons()
    }

    private fun setupGenderDropdown() {
        val genderOptions = listOf("Laki-laki", "Perempuan")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, genderOptions)
        binding.jenisKelaminDropdown.setAdapter(adapter)
    }

    private fun setupButtons() {
        binding.saveButton.setOnClickListener {
            // Simpan data ke server atau local
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}