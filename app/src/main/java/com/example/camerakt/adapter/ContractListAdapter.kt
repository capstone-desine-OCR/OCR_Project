package com.example.camerakt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.camerakt.databinding.ItemBinding
import com.example.camerakt.viewmodel.Contract


class ContractListAdapter() : ListAdapter<Contract, ContractListAdapter.ViewHolder>(diffUtil) {
    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contract: Contract) {
            binding.codeView.text = contract.code
            binding.originView.text = contract.orgin
            binding.cultivarView.text = contract.cultival
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Contract>() {
            override fun areItemsTheSame(oldItem: Contract, newItem: Contract): Boolean {
                return oldItem.code == newItem.code
            }

            override fun areContentsTheSame(oldItem: Contract, newItem: Contract): Boolean {
                return oldItem.orgin == newItem.orgin
            }
        }
    }

}