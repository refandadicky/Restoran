package com.refanda.restoran.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.refanda.restoran.data.model.Profile

class ProfileViewModel : ViewModel() {
    val profileData = MutableLiveData(
        Profile(
            name = "Refanda Dicky Pradana",
            username = "refandadp",
            email = "refandadicky1@gmail.com",
            profileImg = "https://github.com/refandadicky/kokomputer-assets/blob/main/img_profile/img_profile.jpg?raw=true"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}