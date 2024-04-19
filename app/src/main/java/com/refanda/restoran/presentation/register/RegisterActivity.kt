package com.refanda.restoran.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import com.refanda.restoran.R
import com.refanda.restoran.data.datasource.firebaseauth.AuthDataSource
import com.refanda.restoran.data.datasource.firebaseauth.FirebaseAuthDataSource
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.data.repository.UserRepositoryImpl
import com.refanda.restoran.data.source.network.services.firebase.FirebaseService
import com.refanda.restoran.data.source.network.services.firebase.FirebaseServiceImpl
import com.refanda.restoran.databinding.ActivityRegisterBinding
import com.refanda.restoran.presentation.login.LoginActivity
import com.refanda.restoran.presentation.main.MainActivity
import com.refanda.restoran.utils.GenericViewModelFactory
import com.refanda.restoran.utils.highLightWord
import com.refanda.restoran.utils.proceedWhen

class RegisterActivity : AppCompatActivity() {
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(RegisterViewModel(r))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnRegister.setOnClickListener {
            doRegister()
        }
        binding.tvNavToLogin.highLightWord(getString(R.string.text_highlight_login_here)) {
            navigateToLogin()
        }
    }

    private fun doRegister() {
        if (isFormValid()) {
            val fullName = binding.layoutForm.etName.text.toString().trim()
            val password = binding.layoutForm.etPassword.text.toString().trim()
            val email = binding.layoutForm.etEmail.text.toString().trim()
            proceedRegister(fullName, password, email)
        }
    }

    private fun proceedRegister(fullName: String, password: String, email: String) {
            viewModel.doRegister(fullName, password, email).observe(this) {
                it.proceedWhen(
                    doOnSuccess = {
                        binding.pbLoading.isVisible = false
                        binding.btnRegister.isVisible = true
                        navigateToMain()
                    },
                    doOnError = {
                        binding.pbLoading.isVisible = false
                        binding.btnRegister.isVisible = true
                        Toast.makeText(
                            this,
                            "Login Failed : ${it.exception?.message.orEmpty()}",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    doOnLoading = {
                        binding.pbLoading.isVisible = true
                        binding.btnRegister.isVisible = false
                    }
                )
            }
        }

    private fun isFormValid(): Boolean {
        val fullName = binding.layoutForm.etName.text.toString().trim()
        val password = binding.layoutForm.etPassword.text.toString().trim()
        val confirmPassword = binding.layoutForm.etConfirmPassword.text.toString().trim()
        val email = binding.layoutForm.etEmail.text.toString().trim()

        return checkNameValidation(fullName) &&
                checkEmailValidation(email) &&
                checkPasswordValidation(password, binding.layoutForm.tilPassword) &&
                checkPasswordValidation(confirmPassword, binding.layoutForm.tilConfirmPassword) &&
                checkPwdAndConfirmPwd(password, confirmPassword)
    }
    private fun checkNameValidation(fullName: String): Boolean {
        return if (fullName.isEmpty()) {
            binding.layoutForm.tilName.isErrorEnabled = true
            binding.layoutForm.tilName.error = getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.layoutForm.tilName.isErrorEnabled = false
            true
        }
    }
    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutForm.tilEmail.isErrorEnabled = true
            binding.layoutForm.tilEmail.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.layoutForm.tilEmail.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        confirmPassword: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_empty)
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkPwdAndConfirmPwd(password: String, confirmPassword: String): Boolean {
        return if (password != confirmPassword) {
            binding.layoutForm.tilPassword.isErrorEnabled = true
            binding.layoutForm.tilPassword.error =
                getString(R.string.text_password_does_not_match)
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = true
            binding.layoutForm.tilConfirmPassword.error =
                getString(R.string.text_password_does_not_match)
            false
        } else {
            binding.layoutForm.tilPassword.isErrorEnabled = false
            binding.layoutForm.tilConfirmPassword.isErrorEnabled = false
            true
        }
    }

    private fun setupForm() {
        with(binding.layoutForm) {
            tilName.isVisible = true
            tilPassword.isVisible = true
            tilEmail.isVisible = true
            tilConfirmPassword.isVisible = true
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        })
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}