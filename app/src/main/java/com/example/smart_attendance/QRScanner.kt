package com.example.smart_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.smart_attendance.databinding.ActivityQrscannerBinding

class QRScanner : AppCompatActivity() {

    private lateinit var binding: ActivityQrscannerBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrscannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        codeScanner()
        codeScanner.startPreview()
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, binding.scannerView)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback { code ->
                runOnUiThread {
                    d("qrScanner", code.text)
                    Intent(this@QRScanner, MainActivity::class.java).also {
                        it.putExtra("fromScanner", true)
                        it.putExtra("qrString", code.text)
                        startActivity(it)
                        finish()
                    }
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    d("QRScanner", "Camera initialization error: ${it.message}")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }
}