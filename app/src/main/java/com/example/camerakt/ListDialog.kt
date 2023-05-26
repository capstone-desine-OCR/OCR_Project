package com.example.camerakt

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.camerakt.databinding.ListDialogBinding
import com.example.camerakt.viewmodel.ListViewModel

class ListDialog(
    private val row: ArrayList<String>,
    private val position: Int,
    private val listViewModel: ListViewModel
) : DialogFragment() {
    private lateinit var binding: ListDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ListDialogBinding.inflate(LayoutInflater.from(context))

        binding.editCode.setText(row[1])
        binding.editOrigin.setText(row[2])
        binding.editCultivar.setText(row[3])
        binding.editIndate.setText(row[4])
        binding.editOutdate.setText(row[5])
        binding.editWeight.setText(row[6])
        binding.editCount.setText(row[7])
        binding.editPrice.setText(row[8])
        binding.editWon.setText(row[9])
        binding.editExtra.setText(row[10])

        val builder = AlertDialog.Builder(context)
            .setTitle("목록 수정")
            .setView(binding.root)
            .setPositiveButton("수정", DialogInterface.OnClickListener { dialog, _ ->
                Log.d("수정 버튼1", "dialog : $dialog _ : 수정버튼 클릭 내용전달 x")
                val modifiedRow = ArrayList<String>().apply {

                    add(row[0])
                    add(binding.editCode.text.toString())
                    add(binding.editOrigin.text.toString())
                    add(binding.editCultivar.text.toString())
                    add(binding.editIndate.text.toString())
                    add(binding.editOutdate.text.toString())
                    add(binding.editWeight.text.toString())
                    add(binding.editCount.text.toString())
                    add(binding.editPrice.text.toString())
                    add(binding.editWon.text.toString())
                    add(binding.editExtra.text.toString())
                }
                Log.d("수정 버튼2", "수정 버튼2")
                
                listViewModel.editRowData.value = Pair(modifiedRow, position)
            })
            .setNegativeButton("취소", null)
        return builder.create()
    }


}