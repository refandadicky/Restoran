package com.refanda.restoran.presentation.menudetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.refanda.restoran.R
import com.refanda.restoran.data.datasource.cart.CartDataSource
import com.refanda.restoran.data.datasource.cart.CartDatabaseDataSource
import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.data.repository.CartRepository
import com.refanda.restoran.data.repository.CartRepositoryImpl
import com.refanda.restoran.data.source.local.database.AppDatabase
import com.refanda.restoran.databinding.ActivityMenuDetailBinding
import com.refanda.restoran.utils.GenericViewModelFactory
import com.refanda.restoran.utils.proceedWhen
import com.refanda.restoran.utils.toIndonesianFormat

class MenuDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRAS_ITEM = "EXTRAS_ITEM"
        fun startActivity(context: Context, person: Menu) {
            val intent = Intent(context, MenuDetailActivity::class.java)
            intent.putExtra(EXTRAS_ITEM, person)
            context.startActivity(intent)
        }
    }

    private val binding: ActivityMenuDetailBinding by lazy {
        ActivityMenuDetailBinding.inflate(layoutInflater)
    }
    private val viewModel: MenuDetailViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(
            MenuDetailViewModel(intent?.extras, rp)
        )
    }
    private var total: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(viewModel.menu)
        setClickListener()
        observeData()

    }

    private fun setClickListener() {
        binding.layoutAddToCart.icPlusCart.setOnClickListener {
            viewModel.add()
        }
        binding.layoutAddToCart.icMinCart.setOnClickListener {
            viewModel.minus()
        }
        binding.layoutAddToCart.btnAddToCart.setOnClickListener {
            addMenuToCart()
        }
    }

    private fun addMenuToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success), Toast.LENGTH_SHORT
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, getString(R.string.add_to_cart_failed), Toast.LENGTH_SHORT)
                        .show()
                },
                doOnLoading = {
                    Toast.makeText(this, getString(R.string.loading), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.ivFoodPhoto.load(item.imgUrl) {
                crossfade(true)
            }
            binding.layoutMenuDetail.tvDetailName.text = item.name
            binding.layoutMenuDetail.tvDetailPrice.text = item.price.toIndonesianFormat()
            binding.layoutMenuDetail.tvDetailMenu.text = item.desc
            binding.layoutLocationDetail.tvDetailLocation.text = item.address
            binding.layoutLocationDetail.tvDetailLocation.setOnClickListener {
                val i = Intent(Intent.ACTION_VIEW)
                i.setData(Uri.parse(item.mapsUrl))
                startActivity(i)
            }
        }

    }
    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.layoutAddToCart.btnAddToCart.isEnabled = it != 0.0
            binding.layoutAddToCart.btnAddToCart.text = getString(R.string.add_to_string, it.toIndonesianFormat())
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.layoutAddToCart.tvAddition.text = it.toString()
        }
    }

}
