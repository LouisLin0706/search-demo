package com.cryptoassignment.ui.currency

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cryptoassignment.databinding.AdapterCurrencyItemBinding

class CurrencyListAdapter :
    ListAdapter<CurrencyUIModel, CurrencyListAdapter.CurrencyViewHolder>(CurrencyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            AdapterCurrencyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindView(getItem(position))
    }

    inner class CurrencyViewHolder(
        private val binding: AdapterCurrencyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: CurrencyUIModel) {
            binding.model = item
        }
    }

    class CurrencyDiffCallback : DiffUtil.ItemCallback<CurrencyUIModel>() {
        override fun areItemsTheSame(oldItem: CurrencyUIModel, newItem: CurrencyUIModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CurrencyUIModel,
            newItem: CurrencyUIModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}