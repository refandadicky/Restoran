package com.refanda.restoran.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.refanda.restoran.data.datasource.cart.CartDataSource
import com.refanda.restoran.data.datasource.cart.CartDatabaseDataSource
import com.refanda.restoran.data.datasource.category.CategoryApiDataSource
import com.refanda.restoran.data.datasource.category.CategoryDataSource
import com.refanda.restoran.data.datasource.firebaseauth.AuthDataSource
import com.refanda.restoran.data.datasource.firebaseauth.FirebaseAuthDataSource
import com.refanda.restoran.data.datasource.menu.MenuApiDataSource
import com.refanda.restoran.data.datasource.menu.MenuDataSource
import com.refanda.restoran.data.datasource.user.UserDataSource
import com.refanda.restoran.data.datasource.user.UserDataSourceImpl
import com.refanda.restoran.data.repository.CartRepository
import com.refanda.restoran.data.repository.CartRepositoryImpl
import com.refanda.restoran.data.repository.CategoryRepository
import com.refanda.restoran.data.repository.CategoryRepositoryImpl
import com.refanda.restoran.data.repository.MenuRepository
import com.refanda.restoran.data.repository.MenuRepositoryImpl
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.data.repository.UserRepositoryImpl
import com.refanda.restoran.data.source.local.database.AppDatabase
import com.refanda.restoran.data.source.local.database.dao.CartDao
import com.refanda.restoran.data.source.local.pref.UserPreference
import com.refanda.restoran.data.source.local.pref.UserPreferenceImpl
import com.refanda.restoran.data.source.network.services.RestoranApiService
import com.refanda.restoran.data.source.network.services.firebase.FirebaseService
import com.refanda.restoran.data.source.network.services.firebase.FirebaseServiceImpl
import com.refanda.restoran.presentation.cart.CartViewModel
import com.refanda.restoran.presentation.checkout.CheckoutViewModel
import com.refanda.restoran.presentation.home.HomeViewModel
import com.refanda.restoran.presentation.login.LoginViewModel
import com.refanda.restoran.presentation.main.MainViewModel
import com.refanda.restoran.presentation.menudetail.MenuDetailViewModel
import com.refanda.restoran.presentation.profile.ProfileViewModel
import com.refanda.restoran.presentation.register.RegisterViewModel
import com.refanda.restoran.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    val networkModule =
        module {
            single<RestoranApiService> { RestoranApiService.invoke() }
        }

    val firebaseModule =
        module {
            single<FirebaseAuth> { FirebaseAuth.getInstance() }
            single<FirebaseService> { FirebaseServiceImpl() }
        }

    val localModule =
        module {
            single<AppDatabase> { AppDatabase.createInstance(androidContext()) }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME,
                )
            }
            single<UserPreference> { UserPreferenceImpl(get()) }
        }

    private val datasource =
        module {
            single<CartDataSource> { CartDatabaseDataSource(get()) }
            single<CategoryDataSource> { CategoryApiDataSource(get()) }
            single<MenuDataSource> { MenuApiDataSource(get()) }
            single<AuthDataSource> { FirebaseAuthDataSource(get()) }
            single<UserDataSource> { UserDataSourceImpl(get()) }
        }

    private val repository =
        module {
            single<CartRepository> { CartRepositoryImpl(get()) }
            single<CategoryRepository> { CategoryRepositoryImpl(get()) }
            single<MenuRepository> { MenuRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::HomeViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::CheckoutViewModel)
            viewModel { params ->
                MenuDetailViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
            viewModel { MainViewModel(get()) }
            viewModelOf(::ProfileViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::RegisterViewModel)
        }

    val modules =
        listOf(
            networkModule,
            firebaseModule,
            localModule,
            datasource,
            repository,
            viewModelModule,
        )
}
