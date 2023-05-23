package com.example.camerakt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.camerakt.databinding.RowItemBinding

class TableAdapter(private val data: List<List<String>>) : RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    //findByView 지양  binding으로 초기화
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableAdapter.ViewHolder {
        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //0~ data.size가 position으로 들어옴 RecyclerView이 매단계마다 onBindViewHolder를 불러서 List<String> 불러옴
    override fun onBindViewHolder(holder: TableAdapter.ViewHolder, position: Int) {
        val row = data[position]
        holder.bind(row)
    }

    //<List<String> 총 개수
    override fun getItemCount(): Int = data.size


    // 내부 뷰 홀더 클래스
    // viewHolder라는 템플릿을 처음에 생성하고 data.size 개수 만큼 반복해서 재활용 -> 방향에따라서 재사용해서 붙인다는 느낌
    inner class ViewHolder(private val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        //        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        fun bind(row: List<String>) {
            // 독립적으로 작동 ?
            binding.listNumber.text = row[0]
            binding.listCode.text = row[1]
            binding.listOrigin.text = row[2]
            binding.listCultivar.text = row[3]
            binding.listIndate.text = row[4]
            binding.listOutdate.text = row[5]
            binding.listWeight.text = row[6]
            binding.listCount.text = row[7]
            binding.listPrice.text = row[8]
            binding.listWon.text = row[9]
            binding.listExtra.text = row[10]
        }

//        private fun showEditDialog(view: View, index: Int) {
//            // 클릭된 칸에 대한 정보를 가져옴
//            val item = data[adapterPosition][index]
//
//            // 다이얼로그 생성
//            val editText = EditText(view.context)
//            editText.setText(item)
//            val dialog = AlertDialog.Builder(view.context)
//                .setTitle("Edit Item")
//                .setView(editText)
//                .setPositiveButton("Save") { dialog, which ->
//                    // 수정된 내용을 가져옴
//                    val updatedItem = editText.text.toString()
//                    val mutableData = data.toMutableList()
//                    // data를 업데이트
//                    data[adapterPosition][index] = updatedItem
//                    // RecyclerView를 업데이트
//                    notifyItemChanged(adapterPosition)
//                }
//                .setNegativeButton("Cancel", null)
//                .create()
//
//            dialog.show()
//        }


    }
}