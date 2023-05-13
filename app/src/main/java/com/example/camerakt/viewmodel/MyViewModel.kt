package com.example.myocr.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.camerakt.database.DatabaseHelper
import com.example.camerakt.database.model.OCTTable
import com.example.camerakt.repository.RepositoryImpl
import java.lang.NumberFormatException

class MyViewModel : ViewModel() {
    //뷰모델을 이용하면 화면과 데이터 모델 및 모델을 처리하는 로직을 분리해서 개발 가능
    //live data: 뷰 모델이 데이터를 전달할 때 사용하는 방식으로, observer 패턴을 따르므로 데이터가 변경될 시 자동으로 UI 변경

    val liveData_String: MutableLiveData<java.lang.StringBuilder> =
            MutableLiveData<java.lang.StringBuilder>()

    //repository
    var repository = RepositoryImpl

    lateinit var db : DatabaseHelper
    fun setInferred(data: String, context: Context) {
        val result = StringBuilder()
        db = DatabaseHelper(context)
        repository.getResult(data) // 데이터를 가져오고
        // onReturn 콜백함수를 정의(onReturn: 결과 값이 도착했을 때 호출)
        // 결과 값을 처리하는 역할
//        repository.onReturn = {
        repository.lineReturn = {
//            for (field in it) {
//                val infertext = field
//                val item = "$infertext "
//                result.append(item)
//                result.append(System.getProperty("line.separator"))
            Log.d("repository.lineReturn", "check collBack")

            for (innerList in it) {
                Log.d("innerList Start ", "  ")

                try{
                    Integer.valueOf(innerList.first())
                }catch (e: NumberFormatException){
                    continue
                }
                db.insertOCTTable(innerList)

                var r_list : List<OCTTable> = db.allTables

                for(inneerString in r_list){
                    var current = inneerString.toString()
                    Log.d("current", " current : $current" )
                }

                //for (innerString in innerList){
                 //   Log.d("innerString ", "innerString : $innerString")
                //}

                Log.d("innerList End ", "  ")
            }




//            liveData_String.postValue(result)

        }
    }
}