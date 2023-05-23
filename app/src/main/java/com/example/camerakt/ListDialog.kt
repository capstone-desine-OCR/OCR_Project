package com.example.camerakt

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.camerakt.databinding.ListDialogBinding

class ListDialog(private val context: Context) : DialogFragment() {
    private lateinit var binding: ListDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = ListDialogBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("수정", DialogInterface.OnClickListener { dialog, id ->
                Log.d("click", "수정 버튼 눌렀다?")
            })
            .setNegativeButton("취소", DialogInterface.OnClickListener { dialog, id ->

            })
        return builder.create()
    }

}