package com.example.myocr.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.repository.RepositoryImpl


class MyViewModel : ViewModel() {
    //뷰모델을 이용하면 화면과 데이터 모델 및 모델을 처리하는 로직을 분리해서 개발 가능
    //live data: 뷰 모델이 데이터를 전달할 때 사용하는 방식으로, observer 패턴을 따르므로 데이터가 변경될 시 자동으로 UI 변경

    val liveData_String: MutableLiveData<java.lang.StringBuilder> =
        MutableLiveData<java.lang.StringBuilder>()

    //repository
    var repository = RepositoryImpl

    private val ocrTableService: OCRTableService = OCRTableService()

    fun setInferred(data: String, context: Context) {
        val result = StringBuilder()
        val ocrTable = OCRTable()
        repository.getResult(data) // 데이터를 가져오고
        // onReturn 콜백함수를 정의(onReturn: 결과 값이 도착했을 때 호출)
        // 결과 값을 처리하는 역할


//        repository.onReturn = {
        repository.lineReturn = {
            for (field in it) {
                val infertext = field
                val item = "$infertext "
                result.append(item)
                result.append(System.getProperty("line.separator"))
            }
            Log.d("repository.lineReturn", "check collBack")

            //dbHelper.addProduct : .add메소드 사용-> 자동으로 문서와 그에 해당하는 식별자를 생성하여 데이터를 추가해줌

            for (index in it.indices) {
                if (index == 0) {
                    Log.d("pass", ".") //index 0 : [번호, 코드 , 원산지, 품종, 수입날짜 ,...,비고]
                } else {
                    val currentItem = it[index]
                    ocrTable.fromList(currentItem)
                    ocrTableService.addProduct(ocrTable)
                }
            }
//            Log.d("products", ocrTableService.getAllProducts().toString())
            Log.d("개별 조회", ".")
            //Log.d("product",ocrTableService.getProduct("2cSHQlwD1QdaoRIUrIsO").toString())
            liveData_String.postValue(result)

        }
    }
}