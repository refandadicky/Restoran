package com.refanda.restoran.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.refanda.restoran.data.datasource.firebaseauth.AuthDataSource
import com.refanda.restoran.data.datasource.firebaseauth.FirebaseAuthDataSource
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.data.repository.UserRepositoryImpl
import com.refanda.restoran.data.source.network.services.firebase.FirebaseService
import com.refanda.restoran.data.source.network.services.firebase.FirebaseServiceImpl
import com.refanda.restoran.databinding.ActivityLoginBinding
import com.refanda.restoran.presentation.main.MainActivity
import com.refanda.restoran.presentation.register.RegisterActivity
import com.refanda.restoran.utils.GenericViewModelFactory
import com.refanda.restoran.utils.highLightWord
import com.refanda.restoran.utils.proceedWhen

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels {
        val service: FirebaseService = FirebaseServiceImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(LoginViewModel(repository))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener() {
        binding.btnLogin.setOnClickListener {
            inputLogin()
        }
        binding.tvNavToRegister.highLightWord("Register Here"){
            navigateRegister()
        }
    }

    private fun inputLogin() {
        val email = binding.layoutForm.etEmail.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        doLogin(email, password)
    }

    private fun doLogin(email: String, password: String) {
        viewModel.doLogin(email, password).observe(this){result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    binding.pbLoading.isEnabled = true
                    Toast.makeText(
                        this,
                        "Login Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToMain()
                },
                doOnLoading = {
                    binding.pbLoading.isVisible = true
                    binding.pbLoading.isEnabled = false
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    binding.pbLoading.isEnabled = true
                    Toast.makeText(
                        this,
                        "Login Failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateRegister() {
        startActivity(Intent(this, RegisterActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }
}