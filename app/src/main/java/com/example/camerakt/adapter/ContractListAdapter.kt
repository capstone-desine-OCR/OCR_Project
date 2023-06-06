package com.example.camerakt.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.databinding.ItemBinding


class ContractListAdapter() : ListAdapter<OCRTable, ContractListAdapter.ViewHolder>(diffUtil) {
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

    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contract: OCRTable) {
            binding.codeView.text = contract.code
            binding.originView.text = contract.origin
            binding.cultivarView.text = contract.cultivar

            binding.Delete.setOnClickListener() {
                Log.d("확인", "삭제버튼 : $contract.code")
                //removeContract(contract)
            }
        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OCRTable>() {

            //두 아이템이 같은 객체인지 여부를 반환한다. (고유값,ID,해쉬값)
            override fun areItemsTheSame(oldItem: OCRTable, newItem: OCRTable): Boolean {
                // kotlin 주소값 비교 === <-> java ==
                return oldItem === newItem
            }

            //두 아이템이 같은 데이터를 갖고 있는지 여부를 반환한다.
            override fun areContentsTheSame(oldItem: OCRTable, newItem: OCRTable): Boolean {
                // 값
                return oldItem.code == newItem.code
            }
        }
    }

}