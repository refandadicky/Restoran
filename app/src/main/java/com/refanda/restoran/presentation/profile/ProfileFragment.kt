package com.refanda.restoran.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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
import com.refanda.restoran.utils.GenericViewModelFactory

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
        viewModel.profileData.observe(viewLifecycleOwner) {
            binding.ivProfile.load(it.profileImg) {
                crossfade(true)
                error(R.drawable.ic_tab_profile)
                transformations(CircleCropTransformation())
            }
            binding.nameEditText.setText(it.name)
            binding.usernameEditText.setText(it.username)
            binding.emailEditText.setText(it.email)
        }
    }

    private fun setClickListener() {
        binding.layoutHeaderProfile.icEditProfile.setOnClickListener {
            viewModel.changeEditMode()
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.emailEditText.isEnabled = it
            binding.nameEditText.isEnabled = it
            binding.usernameEditText.isEnabled = it
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}