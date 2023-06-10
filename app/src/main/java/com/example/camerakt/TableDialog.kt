package com.example.camerakt

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.databinding.DialogBinding

class TableDialog(
    private val row: OCRTable
) : DialogFragment() {
    private lateinit var binding: DialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("확인", "들어옴")
        binding = DialogBinding.inflate(LayoutInflater.from(context))


        binding.textCode.text = row.code
        binding.textOrigin.text = row.origin
        binding.textCultivar.text = row.cultivar
        binding.textIndate.text = row.indate
        binding.textOutdate.text = row.outdate
        binding.textWeight.text = row.weight.toString()
        binding.textCount.text = row.count.toString()
        binding.textPrice.text = row.price
        binding.textWon.text = row.won
        binding.textExtra.text = row.extra


        val builder = AlertDialog.Builder(context)
            .setTitle("개체 확인")
            .setView(binding.root)
            .setPositiveButton("확인", null)
        return builder.create()
        Log.d("확인", "만듦")
        builder.show()

    }
}