package com.shaswat.smart_attendance

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.shaswat.smart_attendance.databinding.ActivitySelectImagesBinding
import com.shaswat.smart_attendance.di.GlideApp
import com.shaswat.smart_attendance.other.EventObserver
import com.shaswat.smart_attendance.viewModels.SelectImagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class SelectImages : AppCompatActivity() {

    private lateinit var binding: ActivitySelectImagesBinding
    private var imagesUri: ArrayList<Uri> = ArrayList()
    private lateinit var selectImagesViewModel: SelectImagesViewModel
    private val contract =
        registerForActivityResult(ActivityResultContracts.GetMultipleContents()) {
            imagesUri = it as ArrayList<Uri>
            if (imagesUri.size == 3) {
                if (imagesUri[0] != null) GlideApp.with(this).load(imagesUri[0])
                    .into(binding.image1)
                if (imagesUri[1] != null) GlideApp.with(this).load(imagesUri[1])
                    .into(binding.image2)
                if (imagesUri[2] != null) GlideApp.with(this).load(imagesUri[2])
                    .into(binding.image3)
            } else {
                Toast.makeText(this, "Select 3 images", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_images)
        binding = ActivitySelectImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        selectImagesViewModel = ViewModelProvider(this)[SelectImagesViewModel::class.java]

        binding.selectImages.setOnClickListener {
            contract.launch("image/*")
        }

        binding.submitImages.setOnClickListener {
            if (imagesUri.size != 3) {
                Toast.makeText(this, "Select 3 images", Toast.LENGTH_SHORT).show()
            } else {
                upload()
            }
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        selectImagesViewModel.uploadFileStatus.observe(this, EventObserver(onError = {
            binding.progressBar.isVisible = false
            binding.selectImages.isEnabled = true
            binding.submitImages.isEnabled = true
            Log.d("Select Images", "Error: $it")
        }, onLoading = {
            Log.d("Select Images", "Loading")
            binding.progressBar.isVisible = true
            binding.selectImages.isEnabled = false
            binding.submitImages.isEnabled = false
        }) { user ->
            Log.d("Select Images", "Success: $user")
            if (user){
                Toast.makeText(this, "Images Uploaded", Toast.LENGTH_SHORT).show()
                Intent(this, MainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
            binding.progressBar.isVisible = false
            binding.selectImages.isEnabled = true
            binding.submitImages.isEnabled = true
        })
    }

    private fun upload() {
        val filesDir = applicationContext.filesDir
        val file1 = File(filesDir, "file1")
        val file2 = File(filesDir, "file2")
        val file3 = File(filesDir, "file3")
        var inputStream = contentResolver.openInputStream(imagesUri[0])
        var outputStream = FileOutputStream(file1)
        inputStream!!.copyTo(outputStream)
        inputStream.close()
        inputStream = contentResolver.openInputStream(imagesUri[1])
        outputStream = FileOutputStream(file2)
        inputStream!!.copyTo(outputStream)
        inputStream.close()
        inputStream = contentResolver.openInputStream(imagesUri[2])
        outputStream = FileOutputStream(file3)
        inputStream!!.copyTo(outputStream)
        inputStream.close()

        val files: ArrayList<File> = arrayListOf(
            file1, file2, file3
        )
        e("SelectImages", files.toString())
        selectImagesViewModel.uploadImages(files)
    }

}