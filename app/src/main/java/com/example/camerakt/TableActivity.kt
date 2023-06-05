package com.example.camerakt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.camerakt.database.callback.ProductCallBack
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
        ocrTableService.getAllProducts(object : ProductCallBack {
            override fun onSuccess(products: MutableList<OCRTable>?) {
                if (products != null) {
                    dbList = products
                    Log.d("check2", "callBack 받아옴")
                    for (dataList in dbList) {
                        Log.d(
                            "callback 출력",
                            " ${dataList.code}, ${dataList.origin}, ${dataList.cultivar}, ${dataList.indate}, ${dataList.outdate}, ${dataList.weight}, ${dataList.count}, ${dataList.won}, ${dataList.price}, ${dataList.extra}"
                        )
                    }
                    processData()
                }
            }

            override fun onFailure(e: Exception?) {
                Log.e("error", "activity getAllProdcuts 오류", e)
            }
        })


    }

    // table oncreate 초기화 용
    private fun processData() {
        // dbList 사용 가능한 위치에서 데이터 처리
        if (::dbList.isInitialized) {
            for (dataList in dbList) {
                Log.d(
                    "onCreate 초기화",
                    "${dataList.code}, ${dataList.count},${dataList.cultivar}, ${dataList.extra}, ${dataList.indate}, ${dataList.origin}, ${dataList.outdate}, ${dataList.price}, ${dataList.weight},${dataList.won} "
                )
            }
        }
    }
}