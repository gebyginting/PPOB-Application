package com.mobile.pacificaagent.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mobile.pacificaagent.data.request.RegisterRequest
import com.mobile.pacificaagent.databinding.ActivityRegisterBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.utils.ResultState

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val authViewModel: AuthViewModel by viewModels {
        ViewModelFactory.getInstance(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRegister()
        setupButtons()
    }

    private fun setupRegister() {
        binding.registerButton.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val name = binding.etNama.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val noHp = binding.etNoHp.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (username.isEmpty() || name.isEmpty() || email.isEmpty() || noHp.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val request = RegisterRequest(
                password = password,
                passwordConfirm = password,
                name = name,
                phoneNumber = noHp,
                email = email,
                username = username
            )
            observeRegisterState()
            authViewModel.register(request)
        }
    }

    private fun observeRegisterState() {
        lifecycleScope.launchWhenStarted {
            authViewModel.registerState.collect { state ->
                when (state) {
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val response = state.data
                        Toast.makeText(this@RegisterActivity, response.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    }
                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@RegisterActivity, "Register gagal: ${state.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupButtons() {
        binding.loginText.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // cukup ini untuk "kembali"
        }
    }
}