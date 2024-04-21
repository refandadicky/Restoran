package com.refanda.restoran.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.refanda.restoran.R
import com.refanda.restoran.data.datasource.firebaseauth.AuthDataSource
import com.refanda.restoran.data.datasource.firebaseauth.FirebaseAuthDataSource
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.data.repository.UserRepositoryImpl
import com.refanda.restoran.data.source.network.services.firebase.FirebaseService
import com.refanda.restoran.data.source.network.services.firebase.FirebaseServiceImpl
import com.refanda.restoran.databinding.FragmentProfileBinding
import com.refanda.restoran.presentation.login.LoginActivity
import com.refanda.restoran.presentation.main.MainActivity
import com.refanda.restoran.utils.GenericViewModelFactory
import com.refanda.restoran.utils.proceedWhen

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels{
        val service: FirebaseService = FirebaseServiceImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repository))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoginStatus()
        setClickListener()
        observeEditMode()
        observeProfileData()
    }

    private fun observeLoginStatus() {
        if (!viewModel.isLoggedIn()) {
            navigateToLogin()
        }
    }

    private fun observeProfileData() {
        viewModel.getCurrentUser()?.let { user ->
            binding.nameEditText.setText(user.fullName)
            binding.emailEditText.setText(user.email)
        }
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.ivProfile.load(it.profileImg) {
                crossfade(true)
                error(R.drawable.ic_tab_profile)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            viewModel.changeEditMode()
        }
        binding.layoutHeaderProfile.btnLogout.setOnClickListener {
            viewModel.doLogout()
            navigateToLogin()
            val navController = findNavController()
            navController.navigate(R.id.menu_tab_home)
        }
        binding.btnSave.setOnClickListener {
            val username = binding.nameEditText.text.toString()
            updateProfile(username)
        }
    }

    private fun updateProfile(username: String) {
        viewModel.updateUsername(username).observe(viewLifecycleOwner){
            it.proceedWhen(
                doOnSuccess = {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.success_to_change),
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                },
                doOnError = {
                    binding.pbLoading.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.update_username_failed, it.exception?.message.orEmpty()),
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.btnSave.isVisible = true
                    binding.btnEdit.isVisible = false
                },
                doOnLoading = {
                    binding.pbLoading.isVisible  = true
                    binding.btnSave.isVisible = false
                    binding.btnEdit.isVisible = false
                }
            )
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.emailEditText.isVisible = false
            binding.nameEditText.isEnabled = it
            binding.usernameEditText.isVisible = false
            binding.passwordEditText.isVisible = false
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}