package com.refanda.restoran.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.refanda.restoran.data.model.Profile
import com.refanda.restoran.data.repository.UserRepository

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    val profileData = MutableLiveData<Profile>()
    init {
        profileData.value = Profile(
            name = "",
            username = "",
            email = "",
            password = "",
            profileImg = "https://github.com/refandadicky/kokomputer-assets/blob/main/img_profile/img_profile.jpg?raw=true"
        )
    }

    fun getCurrentUser() = repository.getCurrentUser()

    val isEditMode = MutableLiveData<Boolean>()

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }

    fun doLogout() {
        repository.doLogout()
    }
}