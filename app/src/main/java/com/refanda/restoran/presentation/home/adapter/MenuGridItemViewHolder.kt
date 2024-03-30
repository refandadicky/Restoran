package com.example.and_km6_refandadickypradana_challengechapter2.feature.menudetail.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.refanda.restoran.base.ViewHolderBinder
import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.databinding.ItemMenuGridBinding
import com.refanda.restoran.presentation.home.adapter.OnItemClickedListener
import com.refanda.restoran.utils.toIndonesianFormat

class MenuGridItemViewHolder(
    private val binding: ItemMenuGridBinding,
    private val listener: OnItemClickedListener<Menu>
) : ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(item: Menu) {
        item.let {
            binding.tvGridmenuName.text = item.name
            binding.tvGridmenuPrice.text = item.price.toIndonesianFormat()
            binding.ivGridmenuImage.load(item.imgUrl) {
                crossfade(true)
            }
            itemView.setOnClickListener {
                listener.onItemClicked(item)
            }
        }
    }
}