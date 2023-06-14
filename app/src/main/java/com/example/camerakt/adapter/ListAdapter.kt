package com.example.camerakt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.camerakt.ListDialog
import com.example.camerakt.databinding.RowItemBinding
import com.example.camerakt.viewmodel.ListViewModel

class ListAdapter(
    internal val data: ArrayList<ArrayList<String>>, private val listViewModel: ListViewModel
) :
    RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListAdapter.ViewHolder {
        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListAdapter.ViewHolder, position: Int) {
        val row = data[position]
        holder.bind(row) //bind: 화면에 데이터를 세팅하는 함수

        holder.itemView.setOnClickListener {

            val dialog = ListDialog(row, position, listViewModel)
            dialog.show(
                (holder.itemView.context as AppCompatActivity).supportFragmentManager,
                "list_dialog"
            )
        }
    }

    //ArrayList<String> 총 개수
    override fun getItemCount(): Int = data.size //목록에 보여질 아이템의 개수


    // 내부 뷰 홀더 클래스
    // viewHolder라는 템플릿을 처음에 생성하고 data.size 개수 만큼 반복해서 재활용 -> 방향에따라서 재사용해서 붙인다는 느낌
    class ViewHolder(private val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(row: ArrayList<String>) {
            binding.listCode.text = row[0]
            binding.listOrigin.text = row[1]
            binding.listCultivar.text = row[2]
            binding.listIndate.text = row[3]
            binding.listOutdate.text = row[4]
            binding.listWeight.text = row[5]
            binding.listCount.text = row[6]
            binding.listPrice.text = row[7]
            binding.listWon.text = row[8]
            binding.listExtra.text = row[9]
        }
    }
}