package com.refanda.restoran.presentation.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.and_km6_refandadickypradana_challengechapter2.feature.menudetail.adapter.MenuGridItemViewHolder
import com.example.and_km6_refandadickypradana_challengechapter2.feature.menudetail.adapter.MenuListItemViewHolder
import com.refanda.restoran.base.ViewHolderBinder
import com.refanda.restoran.data.model.Menu
import com.refanda.restoran.databinding.ItemMenuGridBinding
import com.refanda.restoran.databinding.ItemMenuListBinding

class ListMenuAdapter(
    private val listener: OnItemClickedListener<Menu>,
    private val listMode: Int = MODE_LIST
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val MODE_LIST = 0
        const val MODE_GRID = 1
    }

    private var asyncDataDiffer = AsyncListDiffer(
        this, object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                //membandingkan apakah item tersebut sama
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
                // yang dibandingkan adalah kontennya
                return oldItem.hashCode() == newItem.hashCode()
            }

        }
    )

    fun submitData(items: List<Menu>) {
        asyncDataDiffer.submitList(items)
    }


    //creating data
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (listMode == MODE_GRID) MenuGridItemViewHolder(
            ItemMenuGridBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), listener
        ) else {
            MenuListItemViewHolder(
                ItemMenuListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener
            )
        }
    }

    // counting the data size
    override fun getItemCount(): Int = asyncDataDiffer.currentList.size

    //show the data from onCreateViewHolder
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is ViewHolderBinder<*>) return
        (holder as ViewHolderBinder<Menu>).bind(asyncDataDiffer.currentList[position])
    }

}

interface OnItemClickedListener<T> {
    fun onItemClicked(item: T)
}