package com.refanda.restoran.data.datasource.firebaseauth

import com.google.firebase.auth.FirebaseUser
import com.refanda.restoran.data.source.network.services.firebase.FirebaseService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseService
    private lateinit var dataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            coEvery { dataSource.doLogin(any(), any()) } returns true
            val actualResult = service.doLogin("refandadicky@gmail.com", "DickyRefanda")
            coVerify { dataSource.doLogin(any(), any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            coEvery { dataSource.doRegister(any(), any(), any()) } returns true
            val actualResult = service.doRegister("refandadicky@gmail.com", "Refanda Dicky", "DickyRefanda")
            coVerify { dataSource.doRegister(any(), any(), any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updateProfile() {
        runTest {
            coEvery { dataSource.updateProfile(any()) } returns true
            val actualResult = service.updateProfile("Refanda Dicky")
            coVerify { dataSource.updateProfile(any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            coEvery { dataSource.updatePassword(any()) } returns true
            val actualResult = service.updatePassword("RefandaDicky")
            coVerify { dataSource.updatePassword(any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            coEvery { dataSource.updateEmail(any()) } returns true
            val actualResult = service.updateEmail("dickyrefanda@gmail.com")
            coVerify { dataSource.updateEmail(any()) }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        runTest {
            every { dataSource.requestChangePasswordByEmail() } returns true
            val actualResult = service.requestChangePasswordByEmail()
            verify { dataSource.requestChangePasswordByEmail() }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun doLogout() {
        runTest {
            every { dataSource.doLogout() } returns true
            val actualResult = service.doLogout()
            verify { dataSource.doLogout() }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun isLoggedIn() {
        runTest {
            every { dataSource.isLoggedIn() } returns true
            val actualResult = service.isLoggedIn()
            verify { dataSource.isLoggedIn() }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun getCurrentUser() {
        runTest {
            val firebaseUser = mockk<FirebaseUser>()
            every { firebaseUser.displayName } returns "Refanda"
            every { firebaseUser.email } returns "refandadicky@gmail.com"
            every { service.getCurrentUser() } returns firebaseUser

            val result = dataSource.getCurrentUser()
            assertEquals("Refanda", result?.fullName)
            assertEquals("refandadicky@gmail.com", result?.email)
            verify { service.getCurrentUser() }
        }
    }
}
