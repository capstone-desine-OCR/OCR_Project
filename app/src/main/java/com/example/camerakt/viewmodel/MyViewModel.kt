package com.example.myocr.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.camerakt.database.DatabaseHelper
import com.example.camerakt.database.model.OCTTable
import com.example.camerakt.repository.RepositoryImpl
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore


class MyViewModel : ViewModel() {
    //뷰모델을 이용하면 화면과 데이터 모델 및 모델을 처리하는 로직을 분리해서 개발 가능
    //live data: 뷰 모델이 데이터를 전달할 때 사용하는 방식으로, observer 패턴을 따르므로 데이터가 변경될 시 자동으로 UI 변경

    val liveData_String: MutableLiveData<java.lang.StringBuilder> =
        MutableLiveData<java.lang.StringBuilder>()

    //repository
    var repository = RepositoryImpl

    lateinit var db: DatabaseHelper
    fun setInferred(data: String, context: Context) {
        val result = StringBuilder()
//        db = DatabaseHelper(context)
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


            //파이어스토어 객체 얻어오기
            var fireDB: FirebaseFirestore = FirebaseFirestore.getInstance()

            // 'products' 이름의 컬렉션 생성
            val productsCollection = fireDB.collection("products")

            // 문서 배열 - 존재하는 product 수만큼 생성
            val productDocRef = Array<DocumentReference>(it.size) { index ->
                productsCollection.document("product" + index)
            }

            for (index in it.indices) {
                val currentItem = it[index]

                try {
                    Integer.valueOf(currentItem.first())
                } catch (e: NumberFormatException) {
                    continue
                }

                productDocRef[index].set(object : HashMap<String?, Any?>() {
                    init {
                        put("번호", currentItem[0])
                        put("코드", currentItem[1])
                        put("원산지", currentItem[2])
                        put("품종", currentItem[3])
                        put("수입날짜", currentItem[4])
                        put("반입날짜", currentItem[5])
                        put("중량", currentItem[6])
                        put("수량", currentItem[7])
                        put("단가", currentItem[8])
                        put("금액", currentItem[9])
                        //put("비고", currentItem[10])
                        if(currentItem.size==11){
                                put("비고", currentItem[10])}
                        else{
                                put("비고", null)
                            }
                    }
                })

            }


            /*
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
            }*/


            liveData_String.postValue(result)

        }
    }
}