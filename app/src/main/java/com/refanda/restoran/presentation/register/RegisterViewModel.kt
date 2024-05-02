package com.refanda.restoran.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun doRegister(
        fullName: String,
        password: String,
        email: String,
    ): LiveData<ResultWrapper<Boolean>> {
        return repository.doRegister(
            fullName = fullName,
            email = email,
            password = password,
        ).asLiveData(Dispatchers.IO)
    }
}
