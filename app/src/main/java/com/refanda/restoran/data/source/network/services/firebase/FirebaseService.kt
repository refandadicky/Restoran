package com.refanda.restoran.data.source.network.services.firebase

import com.google.firebase.auth.FirebaseUser

interface FirebaseService {
    @Throws(exceptionClasses = [Exception::class])
    suspend fun doLogin(
        email: String,
        password: String,
    ): Boolean

    @Throws(exceptionClasses = [Exception::class])
    suspend fun doRegister(
        email: String,
        fullName: String,
        password: String,
    ): Boolean

    suspend fun updateProfile(fullName: String? = null): Boolean

    suspend fun updatePassword(newPassword: String): Boolean

    suspend fun updateEmail(newEmail: String): Boolean

    fun requestChangePasswordByEmail(): Boolean

    fun doLogout(): Boolean

    fun isLoggedIn(): Boolean

    fun getCurrentUser(): FirebaseUser?
}
