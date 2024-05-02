package com.refanda.restoran.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.refanda.restoran.data.model.Profile
import com.refanda.restoran.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    val profileData = MutableLiveData<Profile>()

    init {
        profileData.value =
            Profile(
                name = "",
                username = "",
                email = "",
                password = "",
                profileImg = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.flaticon.com%2Ffree-icon%2Fgithub_2111432&psig=AOvVaw350jvPWNYcDD3oIphdsKwA&ust=1713763030849000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCKDAnffG0oUDFQAAAAAdAAAAABAE",
            )
    }

    fun updateUsername(newName: String) =
        repository.updateProfile(
            fullName = newName,
        ).asLiveData(Dispatchers.IO)

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
