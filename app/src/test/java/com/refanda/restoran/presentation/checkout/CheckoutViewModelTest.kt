package com.refanda.restoran.presentation.checkout

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.catnip.kokomputer.tools.MainCoroutineRule
import com.catnip.kokomputer.tools.getOrAwaitValue
import com.refanda.restoran.data.repository.CartRepository
import com.refanda.restoran.data.repository.MenuRepository
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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

class CheckoutViewModelTest {
    // khusuzon view model
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var cartRepository: CartRepository

    @MockK
    lateinit var userRepository: UserRepository

    @MockK
    lateinit var menuRepository: MenuRepository

    private lateinit var viewModel: CheckoutViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { cartRepository.getCheckoutData() } returns
            flow {
                emit(
                    ResultWrapper.Success(
                        Triple(
                            mockk(relaxed = true), mockk(relaxed = true), 0.0,
                        ),
                    ),
                )
            }

        viewModel =
            spyk(
                CheckoutViewModel(
                    cartRepository,
                    userRepository,
                    menuRepository,
                ),
            )
    }

    @Test
    fun deleteAll() {
        every { cartRepository.deleteAll() } returns
            flow {
                emit(ResultWrapper.Success(Unit))
            }
        viewModel.deleteAll()
        verify { cartRepository.deleteAll() }
    }

    @Test
    fun checkoutCart() {
        val mockUsername = "Refanda Dicky"
        every { userRepository.getCurrentUser()?.fullName } returns mockUsername
        val expected = ResultWrapper.Success(true)
        coEvery { menuRepository.createOrder(any(), any(), any()) } returns
            flow {
                emit(expected)
            }
        val result = viewModel.checkoutCart().getOrAwaitValue()
        assertEquals(expected, result)
    }

    @Test
    fun isLoggedIn() {
        val isLoggedIn = true
        every { userRepository.isLoggedIn() } returns isLoggedIn
        val result = viewModel.isLoggedIn()
        assertEquals(isLoggedIn, result)
    }
}
