package com.refanda.restoran.presentation.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.refanda.restoran.data.repository.CartRepository
import com.refanda.restoran.data.repository.MenuRepository
import com.refanda.restoran.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepository: CartRepository,
    private val userRepository: UserRepository,
    private val menuRepository: MenuRepository,
) : ViewModel() {
    val checkoutData = cartRepository.getCheckoutData().asLiveData(Dispatchers.IO)

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteAll().collect()
        }
    }

    fun checkoutCart() =
        menuRepository.createOrder(
            userRepository.getCurrentUser()?.fullName ?: "",
            checkoutData.value?.payload?.first.orEmpty(),
            checkoutData.value?.payload?.third?.toInt() ?: 0,
        ).asLiveData(Dispatchers.IO)

    fun isLoggedIn(): Boolean {
        return userRepository.isLoggedIn()
    }
}
