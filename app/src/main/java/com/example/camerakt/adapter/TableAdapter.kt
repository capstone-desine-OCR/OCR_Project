package com.example.camerakt.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.camerakt.ListDialog
import com.example.camerakt.databinding.RowItemBinding
import com.example.camerakt.viewmodel.ListViewModel

/*
class ContactListAdapter : ListAdapter<Contract, ContractListAdapter.ViewHolder>(diffUtil) {
    //private lateinit var binding: ContactItemBinding
    inner class ViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(contract: Contract) {
            binding.codeView.text = contract.code
            binding.originView.text = contract.orgin
            binding.cultivarView.text = contract.cultival
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
}*/


class TableAdapter(
    internal val data: ArrayList<ArrayList<String>>,
    private val listViewModel: ListViewModel
) :
//private lateinit var binding: ContactItemBinding


    RecyclerView.Adapter<TableAdapter.ViewHolder>() {

    //findByView 지양  binding으로 초기화
    // onCreateViewHolder: 한 화면에 그려지는 아이템 개수만큼 레이아웃 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableAdapter.ViewHolder {
        val binding = RowItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    //0~ data.size가 position으로 들어옴 RecyclerView이 매단계마다 onBindViewHolder를 불러서 ArrayList<String> 불러옴
    // onBindViewHolder: 생성된 아이템 레이아웃에 값 입력 후 목록에 출력(생성된 뷰홀더를 화면에 보여줌)
    override fun onBindViewHolder(holder: TableAdapter.ViewHolder, position: Int) {
        val row = data[position]
        holder.bind(row) //bind: 화면에 데이터를 세팅하는 함수

        holder.itemView.setOnClickListener {
            Log.d("click", "$row ")
            Log.d("click", "$position 입니다")

//            val dialog = ListDialog(row) , position을 생성자로 전달해서 dialog를 거쳐 fragment로 전달
            val dialog = ListDialog(row, position, listViewModel)
            Log.d("click", "$dialog 입니다")
            dialog.show(
                (holder.itemView.context as AppCompatActivity).supportFragmentManager,
                "list_dialog"
            )

//            listViewModel.editRowData.value = row
        } // 선택한 열을
    }

    //ArrayList<String> 총 개수
    override fun getItemCount(): Int = data.size //목록에 보여질 아이템의 개수


    // 내부 뷰 홀더 클래스
    // viewHolder라는 템플릿을 처음에 생성하고 data.size 개수 만큼 반복해서 재활용 -> 방향에따라서 재사용해서 붙인다는 느낌
    class ViewHolder(private val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root) {
        //        private val linearLayout: LinearLayout = itemView.findViewById(R.id.linearLayout)
        fun bind(row: ArrayList<String>) {

            // 독립적으로 작동 ?
//            binding.listNumber.text = row[0]
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