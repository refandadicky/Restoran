package com.refanda.restoran.presentation.main

import androidx.lifecycle.ViewModel
import com.refanda.restoran.data.datasource.firebaseauth.FirebaseAuthDataSource
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.data.repository.UserRepositoryImpl
import com.refanda.restoran.data.source.network.services.firebase.FirebaseServiceImpl

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    constructor() : this(UserRepositoryImpl(FirebaseAuthDataSource(FirebaseServiceImpl())))

    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
}
