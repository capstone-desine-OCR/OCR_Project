package com.example.camerakt

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.camerakt.databinding.ListDialogBinding

class ListDialog(private val row: MutableList<String>) : DialogFragment() {
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
            .setPositiveButton("수정", DialogInterface.OnClickListener { dialog, id ->
                Log.d("click", "수정 버튼 눌렀다?")
            })
            .setNegativeButton("취소", null)
        return builder.create()
    }


}