package com.example.camerakt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.camerakt.adapter.TableAdapter
import com.example.camerakt.database.callback.ProductCallBack
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.databinding.ActivityTableBinding


class TableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTableBinding
    private val ocrTableService: OCRTableService = OCRTableService()

    private var dbList: MutableList<OCRTable>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ocrTableService.getAllProducts(object : ProductCallBack {
            override fun onSuccess(products: MutableList<OCRTable>?) {
                if (products != null) {
                    dbList = products
                    dbList?.let { list ->
                        if (list.isNotEmpty()) {
                            processData(list)
                        }
                    }
                } else {
                    // 비어있을 경우 logic
                }
            }

            override fun onFailure(e: Exception?) {
                Log.e("error", "activity getAllProdcuts 오류", e)
            }
        })
    }

    // table oncreate 초기화 용
    private fun processData(dbList: MutableList<OCRTable>) {
        val tableAdapter = TableAdapter()
        tableAdapter.submitList(dbList) // adapter에 받아온 dbList를 반영함
        binding.recyclerView.adapter = tableAdapter
    }
}
