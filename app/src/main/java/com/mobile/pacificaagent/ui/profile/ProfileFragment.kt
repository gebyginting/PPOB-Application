package com.mobile.pacificaagent.ui.profile

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupButtons()
    }

    private fun setupLayout() {
        binding.btnProfile.setOnClickListener {
            binding.profileContent.visibility = View.VISIBLE
            binding.menuContent.visibility = View.GONE
            binding.btnProfile.setTextColor(resources.getColor(R.color.white))
            binding.btnMenu.setTextColor(resources.getColor(R.color.orange))
            binding.btnProfile.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5733")))
            binding.btnMenu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D3D3D3")))
        }

        binding.btnMenu.setOnClickListener {
            binding.profileContent.visibility = View.GONE
            binding.menuContent.visibility = View.VISIBLE
            binding.btnMenu.setTextColor(resources.getColor(R.color.white))
            binding.btnProfile.setTextColor(resources.getColor(R.color.orange))
            binding.btnMenu.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF5733")))
            binding.btnProfile.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#D3D3D3")))
        }
    }

    private fun setupButtons() {
        binding.logout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi")
                .setMessage("Apakah kamu yakin ingin keluar?")
                .setPositiveButton("Oke") { dialog, _ ->

                }
                .setNegativeButton("Batal", null)
                .show()
        }

        binding.ivEditProfile.setOnClickListener {
            findNavController().navigate(R.id.editProfileFragment, null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}