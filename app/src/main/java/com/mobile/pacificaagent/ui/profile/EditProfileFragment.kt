package com.mobile.pacificaagent.ui.profile

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobile.pacificaagent.data.request.UpdateProfileRequest
import com.mobile.pacificaagent.databinding.FragmentEditProfileBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.ui.auth.UserViewModel
import com.mobile.pacificaagent.utils.ResultState
import com.mobile.pacificaagent.utils.UserPreference
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference
    private val userViewModel: UserViewModel by activityViewModels {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

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
        userPreference = UserPreference(requireContext())
        setupProfile()
        setupGenderDropdown()
        setupButtons()
        observeUpdateProfileState()
    }

    private fun setupProfile() {
        val user = userPreference.getUser() ?: return

        with(binding) {
            etNamaPengguna.setText(user.name)
            etEmailPengguna.setText(user.email)
            etNoHpPengguna.setText(user.phone)
            jenisKelaminDropdown.setText(user.gender ?: "Jenis Kelamin")
            etAlamatPengguna.setText(user.address ?: "Alamat")
        }
    }

    private fun editProfile() {
        val name = binding.etNamaPengguna.text.toString().trim()
        val phone = binding.etNoHpPengguna.text.toString().trim()
        val gender = binding.jenisKelaminDropdown.text.toString().lowercase(Locale.ROOT).trim()
        val address = binding.etAlamatPengguna.text.toString().trim()

        val request = UpdateProfileRequest(
            name = name.ifEmpty { null },
            phone = phone.ifEmpty { null },
            gender = gender.ifEmpty { null },
        )

        userViewModel.updateProfile(request)
    }

    private fun observeUpdateProfileState() {
        viewLifecycleOwner.lifecycleScope.launch {
            userViewModel.updateProfileState.collectLatest { state ->
                when (state) {
                    is ResultState.Loading -> {
                        // Tampilkan loading jika perlu
                    }

                    is ResultState.Success -> {
                        userViewModel.refreshAndStoreUserProfile()
                        Toast.makeText(requireContext(), "Berhasil menyimpan profil", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }

                    is ResultState.Error -> {
                        Toast.makeText(requireContext(), "Gagal menyimpan profil: ${state.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun setupGenderDropdown() {
        val genderOptions = listOf("Pria", "Wanita")
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, genderOptions)
        binding.jenisKelaminDropdown.setAdapter(adapter)
    }

    private fun setupButtons() {
        binding.saveButton.setOnClickListener {
            editProfile()
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