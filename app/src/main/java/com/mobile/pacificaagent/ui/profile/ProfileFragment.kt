package com.mobile.pacificaagent.ui.profile

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.R
import com.mobile.pacificaagent.data.response.DataUser
import com.mobile.pacificaagent.databinding.FragmentProfileBinding
import com.mobile.pacificaagent.ui.auth.LoginActivity
import com.mobile.pacificaagent.utils.UserPreference

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference

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
        userPreference = UserPreference(requireContext())

        observeUser()  // panggil fungsi observeUser() yang rapi
        setupLayout()
        setupButtons()
    }

    private fun observeUser() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            userPreference.userFlow.collect { user ->
                updateProfileUI(user)
            }
        }
    }

    private fun updateProfileUI(user: DataUser?) {
        with(binding) {
            tvNamaPengguna.text = user?.name ?: "-"
            tvEmailPengguna.text = user?.email ?: "-"
            tvNoHpPengguna.text = user?.phone ?: "-"
            tvJenisKelamin.text = user?.gender ?: "-"
            tvAlamatPengguna.text = user?.address ?: "-"
        }
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
                .setPositiveButton("Oke") { _, _ ->
                    userPreference.clear()
                    startActivity(Intent(requireContext(), LoginActivity::class.java))
                    requireActivity().finish()
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