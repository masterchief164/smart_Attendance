package com.example.smart_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.smart_attendance.data.QRData
import com.example.smart_attendance.databinding.ActivityMainBinding
import com.example.smart_attendance.di.GlideApp
import com.example.smart_attendance.other.EventObserver
import com.example.smart_attendance.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.getUser()
        subscribeToObservers()

        val fromScanner = intent.getBooleanExtra("fromScanner", false)
        if (fromScanner) {
            val qrString = intent.getStringExtra("qrString")
            if (qrString != null) {
                val qrData = mainViewModel.getQRData(qrString)
                d("qrScanner", qrData.toString())
            } else {
                Toast.makeText(this, "No QR code scanned", Toast.LENGTH_SHORT).show()
            }
        }

        binding.courses.setOnClickListener{
            Intent(this, CoursesActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.logout.setOnClickListener {
            mainViewModel.logoutUser()
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        binding.scanQR.setOnClickListener {
            Intent(this, QRScanner::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun attendSession(sessionDetails: QRData) {
        mainViewModel.attendSession(sessionDetails)
    }

    private fun subscribeToObservers() {
        mainViewModel.getUserStatus.observe(this, EventObserver(
            onError = {
                d("LoginActivity", "Error: $it")
            },
            onLoading = {
                d("LoginActivity", "Loading")
                binding.progressBar.isVisible = true
                binding.email.isVisible = false
                binding.userName.text = "Welcome Back, User!"
            }
        ) { user ->
            d("LoginActivity", "Success: $user")
            binding.progressBar.isVisible = false
            binding.email.isVisible = true
            binding.userName.isVisible = true

            binding.userName.text = "Welcome Back, ${user.name.split(" ")[0]}!"
            binding.email.text = user.email

            GlideApp.with(this)
                .load(user.picture).into(binding.ivProfileImage)
        })

        mainViewModel.getQRDataStatus.observe(this, EventObserver(
            onError = {
                d("LoginActivity", "Error: $it")
                Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
            },
            onLoading = {
                d("LoginActivity", "Loading")
                binding.progressBar.isVisible = true
                binding.email.isVisible = false
                binding.userName.isVisible = false
            }
        ) { qrData ->
            binding.progressBar.isVisible = false
            binding.email.isVisible = true
            binding.userName.isVisible = true


            d("efweg", qrData.toString())
            attendSession(qrData)
            binding.qrData.text = "Session ID: ${qrData.session_id}, nonce: ${qrData.nonce}"
        })

        mainViewModel.attendSessionStatus.observe(this, EventObserver(
            onError = {
                d("LoginActivity", "Error: $it")
                Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
            },
            onLoading = {
                d("LoginActivity", "Loading")
            }
        ) { attendSession ->
            d("efweg", attendSession.toString())
            Toast.makeText(this, "Session attended", Toast.LENGTH_SHORT).show()
        })
    }
}