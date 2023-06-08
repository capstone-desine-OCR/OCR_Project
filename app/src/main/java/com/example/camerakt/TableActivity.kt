package com.example.camerakt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.camerakt.adapter.ContractListAdapter
import com.example.camerakt.database.callback.ProductCallBack
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.databinding.ActivityTableBinding


class TableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTableBinding
    private val ocrTableService: OCRTableService = OCRTableService()

    //    private lateinit var dbList: MutableList<OCRTable>
//    private var dbList: MutableList<OCRTable> = mutableListOf()
    private var dbList: MutableList<OCRTable>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("check1", "check1")
        ocrTableService.getAllProducts(object : ProductCallBack {
            override fun onSuccess(products: MutableList<OCRTable>?) {
                if (products != null) {
                    dbList = products
                    Log.d("check2", "callBack 받아옴")
                    dbList?.let { list ->
                        for (dataList in list) {
                            Log.d(
                                "callback 출력",
                                " ${dataList.code}, ${dataList.origin}, ${dataList.cultivar}, ${dataList.indate}, ${dataList.outdate}, ${dataList.weight}, ${dataList.count}, ${dataList.won}, ${dataList.price}, ${dataList.extra}"
                            )
                        }

                        if (list.isNotEmpty()) {
                            Log.d("getAllProducts-dbList", "dbList 존재")
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
        Log.d("precessData", "precessData")

        val contractListAdapter = ContractListAdapter()
        contractListAdapter.submitList(dbList) // adapter에 받아온 dbList를 반영함
        binding.recyclerView.adapter = contractListAdapter
        Log.d("precessData", "contractListAdapter 붙임")

    }
}
