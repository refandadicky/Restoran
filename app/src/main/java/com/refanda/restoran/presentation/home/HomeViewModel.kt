package com.refanda.restoran.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.refanda.restoran.data.repository.CategoryRepository
import com.refanda.restoran.data.repository.MenuRepository
import com.refanda.restoran.data.source.local.pref.UserPreference
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val categoryRepository: CategoryRepository,
    private val menuRepository: MenuRepository,
    private val userPreference: UserPreference
) : ViewModel() {
    private val _isUsingGridMode = MutableLiveData(userPreference.isUsingGridMode())
    val isUsingGridMode: LiveData<Boolean>
        get() = _isUsingGridMode

    fun getListMode(): Int {
        return if (userPreference.isUsingGridMode()) 1 else 0
    }

    fun changeListMode() {
        val currentValue = _isUsingGridMode.value ?: false
        _isUsingGridMode.postValue(!currentValue)
        userPreference.setUsingGridMode(!currentValue)
    }

    fun getMenu(categorySlug: String? = null) =
        menuRepository.getMenu(categorySlug).asLiveData(Dispatchers.IO)
    fun getCategories() = categoryRepository.getCategory().asLiveData(Dispatchers.IO)
}