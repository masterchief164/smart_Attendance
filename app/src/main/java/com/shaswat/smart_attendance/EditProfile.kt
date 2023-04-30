package com.shaswat.smart_attendance

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.shaswat.smart_attendance.data.UpdateProfile
import com.shaswat.smart_attendance.databinding.ActivityEditProfileBinding
import com.shaswat.smart_attendance.other.EventObserver
import com.shaswat.smart_attendance.other.snackbar
import com.shaswat.smart_attendance.viewModels.EditProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfile : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var editProfileViewModel: EditProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        editProfileViewModel = ViewModelProvider(this)[EditProfileViewModel::class.java]

        binding.name.addTextChangedListener {
            if (binding.name.text != null &&
                binding.phone.text != null &&
                binding.name.text!!.isNotEmpty() &&
                binding.phone.text!!.isNotEmpty() &&
                binding.department.text != null &&
                binding.department.text!!.isNotEmpty() &&
                binding.roomNumber.text != null &&
                binding.roomNumber.text!!.isNotEmpty() &&
                binding.batch.text != null &&
                binding.batch.text!!.isNotEmpty()
            ) {
                binding.update.isEnabled = true
            }
        }

        binding.phone.addTextChangedListener {
            if (binding.name.text != null &&
                binding.phone.text != null &&
                binding.name.text!!.isNotEmpty() &&
                binding.phone.text!!.isNotEmpty() &&
                binding.department.text != null &&
                binding.department.text!!.isNotEmpty() &&
                binding.roomNumber.text != null &&
                binding.roomNumber.text!!.isNotEmpty() &&
                binding.batch.text != null &&
                binding.batch.text!!.isNotEmpty()
            ) {
                binding.update.isEnabled = true
            }
        }

        binding.department.addTextChangedListener {
            if (binding.name.text != null &&
                binding.phone.text != null &&
                binding.name.text!!.isNotEmpty() &&
                binding.phone.text!!.isNotEmpty() &&
                binding.department.text != null &&
                binding.department.text!!.isNotEmpty() &&
                binding.roomNumber.text != null &&
                binding.roomNumber.text!!.isNotEmpty() &&
                binding.batch.text != null &&
                binding.batch.text!!.isNotEmpty()
            ) {
                binding.update.isEnabled = true
            }
        }

        binding.roomNumber.addTextChangedListener {
            if (binding.name.text != null &&
                binding.phone.text != null &&
                binding.name.text!!.isNotEmpty() &&
                binding.phone.text!!.isNotEmpty() &&
                binding.department.text != null &&
                binding.department.text!!.isNotEmpty() &&
                binding.roomNumber.text != null &&
                binding.roomNumber.text!!.isNotEmpty() &&
                binding.batch.text != null &&
                binding.batch.text!!.isNotEmpty()
            ) {
                binding.update.isEnabled = true
            }
        }

        binding.batch.addTextChangedListener {
            if (binding.name.text != null &&
                binding.phone.text != null &&
                binding.name.text!!.isNotEmpty() &&
                binding.phone.text!!.isNotEmpty() &&
                binding.department.text != null &&
                binding.department.text!!.isNotEmpty() &&
                binding.roomNumber.text != null &&
                binding.roomNumber.text!!.isNotEmpty() &&
                binding.batch.text != null &&
                binding.batch.text!!.isNotEmpty()
            ) {
                binding.update.isEnabled = true
            }
        }

        binding.update.setOnClickListener{
            val userProfile = UpdateProfile(
                binding.name.text.toString(),
                binding.phone.text.toString(),
                binding.department.text.toString(),
                binding.roomNumber.text.toString(),
                binding.batch.text.toString()
            )
            editProfileViewModel.updateProfile(
                userProfile
            )
        }
        subscribeToObservers()
    }

    private fun subscribeToObservers(){
        editProfileViewModel.updateProfileStatus.observe(this, EventObserver(
            onError = {
                binding.progressBar.isVisible = false
                snackbar(it, binding.root)
            },
            onLoading = { binding.progressBar.isVisible = true }
        ) { user ->
            binding.progressBar.isVisible = false
            editProfileViewModel.saveUser(user)
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
            snackbar("Profile updated successfully", binding.root)

        })
    }
}