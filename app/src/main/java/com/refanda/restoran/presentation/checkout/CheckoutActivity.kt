package com.refanda.restoran.presentation.checkout

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.refanda.restoran.R
import com.refanda.restoran.data.datasource.cart.CartDataSource
import com.refanda.restoran.data.datasource.cart.CartDatabaseDataSource
import com.refanda.restoran.data.datasource.firebaseauth.AuthDataSource
import com.refanda.restoran.data.datasource.firebaseauth.FirebaseAuthDataSource
import com.refanda.restoran.data.datasource.menu.MenuApiDataSource
import com.refanda.restoran.data.datasource.menu.MenuDataSource
import com.refanda.restoran.data.repository.CartRepository
import com.refanda.restoran.data.repository.CartRepositoryImpl
import com.refanda.restoran.data.repository.MenuRepositoryImpl
import com.refanda.restoran.data.repository.UserRepository
import com.refanda.restoran.data.repository.UserRepositoryImpl
import com.refanda.restoran.data.source.local.database.AppDatabase
import com.refanda.restoran.data.source.network.services.RestoranApiService
import com.refanda.restoran.data.source.network.services.firebase.FirebaseService
import com.refanda.restoran.data.source.network.services.firebase.FirebaseServiceImpl
import com.refanda.restoran.databinding.ActivityCheckoutBinding
import com.refanda.restoran.presentation.common.adapter.CartListAdapter
import com.refanda.restoran.presentation.checkout.adapter.PriceListAdapter
import com.refanda.restoran.presentation.login.LoginActivity
import com.refanda.restoran.presentation.main.MainActivity
import com.refanda.restoran.utils.GenericViewModelFactory
import com.refanda.restoran.utils.proceedWhen
import com.refanda.restoran.utils.toIndonesianFormat

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val ras = RestoranApiService.invoke()
        val mds: MenuDataSource = MenuApiDataSource(ras)
        val mr = MenuRepositoryImpl(mds)
        val fs: FirebaseService = FirebaseServiceImpl()
        val fds: AuthDataSource = FirebaseAuthDataSource(fs)
        val fr: UserRepository = UserRepositoryImpl(fds)
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(CheckoutViewModel(rp, fr, mr))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun customDialog() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.layout_dialog)
                    val layoutParams = WindowManager.LayoutParams()
                    layoutParams.copyFrom(dialog.window?.attributes)
                    dialog.window?.attributes = layoutParams
                    val btnBacktoHome = dialog.findViewById<Button>(R.id.btn_back_to_home)
                    dialog.show()
                    btnBacktoHome.setOnClickListener {
                        viewModel.deleteAll()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        dialog.dismiss()
                        finish()
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.checkout_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            if (viewModel.isLoggedIn()) {
                customDialog()
            } else {
                navigateToLogin()
            }
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }


    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = true
                    binding.layoutContent.rvCart.isVisible = true
                    binding.cvSectionOrder.isVisible = true
                    result.payload?.let { (carts, priceItems, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                        priceItemAdapter.submitData(priceItems)
                    }
                }, doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                }, doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                }, doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    data.payload?.let { (_, _, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                })
        }
    }
}