package com.refanda.restoran.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.refanda.restoran.R
import com.refanda.restoran.data.model.Cart
import com.refanda.restoran.databinding.FragmentCartBinding
import com.refanda.restoran.presentation.checkout.CheckoutActivity
import com.refanda.restoran.presentation.common.adapter.CartListAdapter
import com.refanda.restoran.presentation.common.adapter.CartListener
import com.refanda.restoran.utils.hideKeyboard
import com.refanda.restoran.utils.proceedWhen
import com.refanda.restoran.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding

    private val viewModel: CartViewModel by viewModel()

    private val adapter: CartListAdapter by lazy {
        CartListAdapter(
            object : CartListener {
                override fun onPlusTotalItemCartClicked(cart: Cart) {
                    viewModel.increaseCart(cart)
                }

                override fun onMinusTotalItemCartClicked(cart: Cart) {
                    viewModel.decreaseCart(cart)
                }

                override fun onRemoveCartClicked(cart: Cart) {
                    viewModel.removeCart(cart)
                }

                override fun onUserDoneEditingNotes(cart: Cart) {
                    viewModel.setCartNotes(cart)
                    hideKeyboard()
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCartBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.btnCheckoutCart.setOnClickListener {
            startActivity(Intent(requireContext(), CheckoutActivity::class.java))
        }
    }

    private fun observeData() {
        viewModel.getAllCarts().observe(viewLifecycleOwner) { result ->
            result.proceedWhen(
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = false
                    binding.btnCheckoutCart.isVisible = false
                },
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvCart.isVisible = true
                    result.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                    binding.btnCheckoutCart.isVisible = true
                },
                doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.rvCart.isVisible = false
                    binding.btnCheckoutCart.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    binding.rvCart.isVisible = false
                    result.payload?.let { (carts, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                    binding.btnCheckoutCart.isVisible = false
                },
            )
        }
    }

    private fun setupList() {
        binding.rvCart.adapter = this@CartFragment.adapter
    }
}
