package com.mobile.pacificaagent

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobile.pacificaagent.databinding.ActivityMainBinding
import com.mobile.pacificaagent.ui.auth.LoginActivity
import com.mobile.pacificaagent.utils.UserPreference

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userPreference = UserPreference(this)
        checkUser()


        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        // tambahan agar home dklik, stack dihapus.
        navView.setOnItemSelectedListener { menuItem ->
            val currentDest = navController.currentDestination?.id

            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    if (currentDest == R.id.navigation_home) {
                        true
                    } else {
                        navController.navigate(R.id.navigation_home)
                        true
                    }
                }

//                R.id.navigation_transaksi -> {
//                    if (currentDest == R.id.navigation_transaksi) {
//                        navController.popBackStack(R.id.navigation_transaksi, false)
//                        true
//                    } else {
//                        navController.navigate(R.id.navigation_transaksi)
//                        true
//                    }
//                }
//
//                R.id.navigation_profile -> {
//                    if (currentDest == R.id.navigation_profile) {
//                        navController.popBackStack(R.id.navigation_profile, false)
//                        true
//                    } else {
//                        navController.navigate(R.id.navigation_profile)
//                        true
//                    }
//                }

                else -> {
                    androidx.navigation.ui.NavigationUI.onNavDestinationSelected(menuItem, navController)
                }
            }
        }

//        navView.setOnItemSelectedListener { menuItem ->
//            when (menuItem.itemId) {
//                R.id.navigation_home -> {
//                    navController.popBackStack(R.id.navigation_home, false)
//                    true
//                }
//
//
//                else -> {
//                    androidx.navigation.ui.NavigationUI.onNavDestinationSelected(menuItem, navController)
//                }
//            }
//        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.konfirmasiWifiFragment,
                R.id.konfirmasiTokenFragment,
                R.id.konfirmasiEWalletFragment,
                R.id.topUpNominalSaldoFragment,
                R.id.topUpSaldoPembayaranFragment,
                R.id.konfirmasiPulsaFragment -> navView.visibility = View.GONE

                else -> navView.visibility = View.VISIBLE
            }
        }

    }

    private fun checkUser() {
        val tokenUser = userPreference.getToken()
        Log.d("TokenUserMain:", tokenUser)
        Log.d("TokenUserMain:", "Kosong")

        if (tokenUser.isEmpty()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}