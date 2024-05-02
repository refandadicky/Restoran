package com.refanda.restoran.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.refanda.restoran.R
import com.refanda.restoran.base.ViewHolderBinder
import com.refanda.restoran.data.model.Cart
import com.refanda.restoran.databinding.ItemCartMenuBinding
import com.refanda.restoran.databinding.ItemCartMenuOrderBinding
import com.refanda.restoran.utils.doneEditing

class CartListAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<ViewHolder>() {
    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Cart>() {
                override fun areItemsTheSame(
                    oldItem: Cart,
                    newItem: Cart,
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.menuId == newItem.menuId
                }

                override fun areContentsTheSame(
                    oldItem: Cart,
                    newItem: Cart,
                ): Boolean {
                    return oldItem.hashCode() == newItem.hashCode()
                }
            },
        )

    fun submitData(data: List<Cart>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return if (cartListener != null) {
            CartViewHolder(
                ItemCartMenuBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                cartListener,
            )
        } else {
            CartOrderViewHolder(
                ItemCartMenuOrderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
            )
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
    }
}

class CartViewHolder(
    private val binding: ItemCartMenuBinding,
    private val cartListener: CartListener?,
) : ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivProductImage.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvProductCount.text = item.itemQuantity.toString()
            tvProductName.text = item.menuName
            tvProductPrice.text = (item.itemQuantity * item.menuPrice).toString()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotesItem.setText(item.itemNotes)
        binding.etNotesItem.doneEditing {
            binding.etNotesItem.clearFocus()
            val newItem =
                item.copy().apply {
                    itemNotes = binding.etNotesItem.text.toString().trim()
                }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding) {
            ivMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item) }
            ivPlus.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item) }
            ivRemoveCart.setOnClickListener { cartListener?.onRemoveCartClicked(item) }
        }
    }
}

class CartOrderViewHolder(
    private val binding: ItemCartMenuOrderBinding,
) : ViewHolder(binding.root), ViewHolderBinder<Cart> {
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding) {
            binding.ivProductImage.load(item.menuImgUrl) {
                crossfade(true)
            }
            tvTotalQuantity.text =
                itemView.rootView.context.getString(
                    R.string.total_quantity,
                    item.itemQuantity.toString(),
                )
            tvProductName.text = item.menuName
            tvProductPrice.text = (item.itemQuantity * item.menuPrice).toString()
        }
    }

    private fun setCartNotes(item: Cart) {
        binding.tvNotes.text = item.itemNotes
    }
}

interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)

    fun onMinusTotalItemCartClicked(cart: Cart)

    fun onRemoveCartClicked(cart: Cart)

    fun onUserDoneEditingNotes(cart: Cart)
}
