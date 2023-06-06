package com.example.camerakt.adapter

import android.app.AlertDialog
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
            binding.code.text = contract.code
            binding.origin.text = contract.origin
            binding.cultivar.text = contract.cultivar

            binding.delete.setOnClickListener() {
                val builder = AlertDialog.Builder(binding.root.context)
                builder.setTitle("삭제")
                builder.setMessage("[${contract.code}]를 삭제하시겠습니까?")
                builder.setPositiveButton("확인") { dialog, which ->
                    // 삭제 동작 수행
                    Log.d("확인", "삭제버튼 : ${contract.code}")
                    // removeContract(contract)
                }
                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }

            binding.detail.setOnClickListener() {
                Log.d("확인", "상세보기 버튼 : ${contract.code}")
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