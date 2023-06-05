package com.example.camerakt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.databinding.ActivityTableBinding


class TableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTableBinding
    private val ocrTableService: OCRTableService = OCRTableService()
    private lateinit var dbList: List<OCRTable>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("check1", "check1")
        dbList = ocrTableService.allProducts
        Log.d("check2", "check2")
        for (table in dbList) {
            Log.d("table 요소", "$table")
            Log.d("table 코드", "${table.code}")

        }

    }
}