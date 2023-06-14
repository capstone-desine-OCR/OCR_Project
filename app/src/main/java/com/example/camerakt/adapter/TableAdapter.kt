package com.example.camerakt.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.camerakt.TableDialog
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.databinding.ItemBinding


class TableAdapter() : ListAdapter<OCRTable, TableAdapter.ViewHolder>(diffUtil) {
    private val ocrTableService: OCRTableService = OCRTableService()

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


    // ViewHolder 는 목록 1 줄임-> 여러 목록을 ViewHolder 1칸으로 재사용하는 것
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

                    // 1. db 상에서 삭제
                    ocrTableService.deleteProduct(contract.code)
                    // 2. 화면단에서 목록 삭제 초기화 -> submitList()
                    removeItem(adapterPosition)
                }

                builder.setNegativeButton("취소") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }

            binding.detail.setOnClickListener() {
                Log.d("확인", "상세보기 버튼 : ${contract.code}")
                val dialog = TableDialog(contract)
                dialog.show(
                    (binding.root.context as AppCompatActivity).supportFragmentManager,
                    "dialog_tag"
                )

            }
        }
    }


    fun removeItem(position: Int) {

        val newList = currentList.toMutableList()
//        for (new in newList)
//            Log.d("removeItem-new", "${new.code} 입니다")
        Log.d("삭제 위치", "삭제 위치 :$position 입니다.")
        Log.d("삭제  code", "삭제  code : ${newList[position].code} 입니다.")

        newList.removeAt(position)
//        Log.d("삭제 이후 위치", "삭제 이후 위치 :$position 입니다.")
//        Log.d("현재 삭제 위치", "삭제 이후 위치 code: ${newList[position].code}")

        for (new in newList)
            Log.d("변경 이후 목록", "${new.code} 입니다")

        submitList(newList)
        Log.d("submitList 동작", "submitList 동작")
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<OCRTable>() {

            //diffUtil

            //두 아이템이 같은 객체인지 여부를 반환한다. (고유값,ID,해쉬값)
            override fun areItemsTheSame(oldItem: OCRTable, newItem: OCRTable): Boolean {
                Log.d(
                    "areItemsTheSame",
                    "oldItem-code : ${oldItem.code} / newItem-code : ${newItem.code} / ${oldItem === newItem}"
                )
                // kotlin 주소값 비교 === <-> java 참조값 비교 == 동일
                return oldItem === newItem
            }

            //두 아이템이 같은 데이터를 갖고 있는지 여부를 반환한다. -> UI 변경용
            override fun areContentsTheSame(oldItem: OCRTable, newItem: OCRTable): Boolean {
                Log.d(
                    "areContentsTheSame",
                    "oldItem-code : ${oldItem.code} / newItem-code : ${newItem.code} / ${oldItem == newItem}"
                )
                // 객체 값 비교 java equals -> (ocrTable equals , hashcode 구현해줘야 할 것 같았다)
                return oldItem == newItem
            }
        }
    }
}