package com.shaswat.smart_attendance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.shaswat.smart_attendance.other.EventObserver
import com.shaswat.smart_attendance.viewModels.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.shaswat.smart_attendance.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var studentButton: Button
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        subscribeToObservers()
        studentButton = binding.student


        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .requestServerAuthCode(getString(R.string.server_client_id))
            .build()
        googleSignInClient = GoogleSignIn.getClient(applicationContext, gso)
        val acc = GoogleSignIn.getLastSignedInAccount(this)

        if (acc != null) {
            d("LoginActivity", "Already signed in")
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }

        studentButton.setOnClickListener {
            googleSignIn()
        }
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            d("LoginActivity", it.data.toString())
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)!!
                d("TAG", "firebaseAuthWithGoogle:" + account.id)
                authWithGoogle(account.serverAuthCode!!)
            } catch (e: ApiException) {
                Log.e("TAG", "Google sign in failed", e)
            }
        }

    private fun authWithGoogle(idToken: String) {
        loginViewModel.login(idToken)
    }

    private fun subscribeToObservers() {
        loginViewModel.loginStatus.observe(this, EventObserver(
            onError = {
                binding.progressBar2.isVisible = false
                binding.student.isVisible = true
                binding.button2.isVisible = true
                binding.textView.isVisible = true
                d("LoginActivity", "Error: $it")
            },
            onLoading = {
                binding.progressBar2.isVisible = true
                binding.student.isVisible = false
                binding.button2.isVisible = false
                binding.textView.isVisible = false
                d("LoginActivity", "Loading")
            }
        ) { user ->

            binding.progressBar2.isVisible = false
            binding.student.isVisible = true
            binding.button2.isVisible = true
            binding.textView.isVisible = true
            d("LoginActivity", "Success: $user")
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        })
    }
}