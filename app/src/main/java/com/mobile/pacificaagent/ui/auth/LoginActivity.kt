package com.mobile.pacificaagent.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mobile.pacificaagent.MainActivity
import com.mobile.pacificaagent.data.request.LoginRequest
import com.mobile.pacificaagent.databinding.ActivityLoginBinding
import com.mobile.pacificaagent.ui.ViewModelFactory
import com.mobile.pacificaagent.utils.ResultState
import com.mobile.pacificaagent.utils.UserPreference

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreference: UserPreference
    private lateinit var factory: ViewModelFactory
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Inisialisasi setelah context siap
        userPreference = UserPreference(applicationContext)
        factory = ViewModelFactory.getInstance(applicationContext)
        authViewModel = factory.create(AuthViewModel::class.java)
        userViewModel = factory.create(UserViewModel::class.java)

        setupLogin()
        setupRegister()
    }

    private fun setupLogin() {
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                observeLoginState()
                val request = LoginRequest(username = username, password = password)
                authViewModel.login(request)
            } else {
                Toast.makeText(this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeLoginState() {
        lifecycleScope.launchWhenStarted {
            authViewModel.loginState.collect { state ->
                when (state) {
                    is ResultState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is ResultState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val response = state.data
                        val token = response.data.token
                        userPreference.saveToken(token)
                        setupProfile()

                    }
                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Login gagal: ${state.error}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun setupRegister() {
        binding.registerText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupProfile() {
        userViewModel.getProfile()
        lifecycleScope.launchWhenStarted {
            userViewModel.profileState.collect { state ->
                when (state) {
                    is ResultState.Loading -> {
                    }

                    is ResultState.Success -> {
                        val response = state.data.data
                        userPreference.saveUser(response)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)                    }

                    is ResultState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Log.d("UserPref", "Saved profile: Gagal")
                    }
                }
            }
        }
    }
}