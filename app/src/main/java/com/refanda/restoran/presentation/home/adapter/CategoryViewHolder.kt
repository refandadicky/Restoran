package com.refanda.restoran.presentation.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.refanda.restoran.base.ViewHolderBinder
import com.refanda.restoran.data.model.Category
import com.refanda.restoran.databinding.ItemCategoryMenuBinding

class CategoryViewHolder (
    private val binding: ItemCategoryMenuBinding,
    private val itemClick: (Category) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Category> {
    override fun bind(item: Category) {
        item.let {
            binding.ivCategoryImage.load(item.imgUrl) {
                crossfade(true)
            }
            binding.tvCategoryName.text = it.name
            itemView.setOnClickListener{itemClick(item)}
        }
    }
}