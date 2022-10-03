package com.example.smart_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
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

        val logoutButton = binding.logout
        logoutButton.setOnClickListener {
            mainViewModel.logoutUser()
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }
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
                binding.name.isVisible = false
                binding.roll.isVisible = false
            }
        ) { user ->
            d("LoginActivity", "Success: $user")
            binding.progressBar.isVisible = false
            binding.email.isVisible = true
            binding.name.isVisible = true
            binding.roll.isVisible = true

            binding.name.text = user.name
            binding.email.text = user.email
            binding.roll.text = user.roll

            GlideApp.with(this)
                .load(user.picture).into(binding.imageView)
        })
    }
}