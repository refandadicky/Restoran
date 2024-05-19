package com.refanda.restoran.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.catnip.kokomputer.tools.MainCoroutineRule
import com.refanda.restoran.data.model.User
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(userRepository))
        viewModel.isEditMode.value = false
    }

    @Test
    fun getProfileData() {
    }

    @Test
    fun updateUsername() {
        every { userRepository.updateProfile(any()) } returns
            flow {
                emit(ResultWrapper.Success(true))
            }
        viewModel.updateUsername("Dicky Refanda")
        verify { userRepository.updateProfile(any()) }
    }

    @Test
    fun getCurrentUser() {
        val currentUser = mockk<User>()
        every { userRepository.getCurrentUser() } returns currentUser
        val result = viewModel.getCurrentUser()
        assertEquals(currentUser, result)
        verify { userRepository.getCurrentUser() }
    }

    @Test
    fun isEditMode() {
        assertFalse(viewModel.isEditMode.value ?: true)
    }

    @Test
    fun changeEditMode() {
        val initialValue = viewModel.isEditMode.value
        viewModel.changeEditMode()
        val finalValue = viewModel.isEditMode.value
        assertNotEquals(initialValue, finalValue)
    }

    @Test
    fun isLoggedIn() {
        every { userRepository.isLoggedIn() } returns true
        val result = viewModel.isLoggedIn()
        assertEquals(true, result)
        verify { userRepository.isLoggedIn() }
    }

    /*@Test
    fun doLogout() {
        every { userRepository.doLogout() } returns true
        val result = viewModel.doLogout()
        assertEquals(true, result)
        verify { userRepository.doLogout() }
    }*/
}
