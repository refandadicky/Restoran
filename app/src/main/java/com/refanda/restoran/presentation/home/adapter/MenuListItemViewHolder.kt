package com.example.and_km6_refandadickypradana_challengechapter2.feature.menudetail.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.refanda.restoran.base.ViewHolderBinder
import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.databinding.ItemMenuListBinding
import com.refanda.restoran.presentation.home.adapter.OnItemClickedListener
import com.refanda.restoran.utils.toIndonesianFormat

class MenuListItemViewHolder(
    private val binding: ItemMenuListBinding,
    private val itemClick: (Menu) -> Unit
) : ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.tvListmenuName.text = item.name
            binding.tvListmenuPrice.text = item.price.toIndonesianFormat()
            binding.ivListmenuImage.load(item.imgUrl) {
                crossfade(true)
            }
            itemView.setOnClickListener {
                itemClick(item)
            }
        }
    }
}