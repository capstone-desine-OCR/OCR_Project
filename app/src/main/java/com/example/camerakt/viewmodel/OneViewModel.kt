package com.example.camerakt.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.repository.RepositoryImpl

class OneViewModel : ViewModel() {
    var repository = RepositoryImpl

    val oneBitMapLiveData: MutableLiveData<Bitmap> = MutableLiveData()
    val oneTableData = MutableLiveData<ArrayList<ArrayList<String>>>()
    //val oneTableLiveData: LiveData<ArrayList<ArrayList<String>>> get() = oneTableData

    private val ocrTableService: OCRTableService = OCRTableService()


    fun setInferred(data: String, context: Context) {

        val ocrTable = OCRTable()
        var tmpList: ArrayList<ArrayList<String>>
        var oneList: ArrayList<ArrayList<String>>
        repository.getResult(data) // 데이터를 가져오고

        repository.lineReturn = {
            oneTableData.value =
                it  // ArrayList<ArrayList<String>>> MutableLiveData로 담아서 obsever가 인식할 수 있도록 view로 던짐

            /* it
            [코드,BL-11],[원산지, 베트남],[중량,20,수량,356],,,*/

            /*tmpList
            [[코드, BL-11], [원산지, 베트남], [품종, 고추], [수입날짜, 2023-06-15], [반입날짜, 2023-06-27],
             [중량, 20], [수량, 345], [단가, $15], [금액, W230.68], [비고, 하자X]]*/

            tmpList = recombineArrayList(it)

            /*oneList
            [[코드, 원산지, 품종, 수입날짜, 반입날짜, 중량, 수량, 단가, 금액, 비고],
             [BL-11, 베트남, 고추, 2023-06-15, 2023-06-27, 20, 345, $15, W230.68, 하자X]]*/

            oneList = seperateList(tmpList)
            Log.d("List", oneList.toString())
            for (i in oneList.indices) {
                if (i == 0) {
                    Log.d("pass", ".") //index 0 : [번호, 코드 , 원산지, 품종, 수입날짜 ,...,비고]
                } else {
                    val currentList = oneList[i]
                    ocrTable.fromList(currentList)
                    ocrTableService.addProduct(ocrTable)
                }
            }

            Log.d("products", ocrTableService.getAllProducts().toString())

            //liveData_String.postValue(result)


        }
    }

    fun recombineArrayList(inputList: ArrayList<ArrayList<String>>): ArrayList<ArrayList<String>> {
        val originalList: ArrayList<ArrayList<String>> = inputList
        val productData: MutableMap<String, String> = HashMap<String, String>()

        var name: String = ""
        var value: String = ""

        for (i in 0 until originalList.size) {
            val currentRow = originalList[i] //ex.[중량,20,수량,20]
            Log.d("currentRow", currentRow.toString())
            for (j in 0 until currentRow.size) {

                if (j % 2 == 0) {
                    // 열 이름인 경우
                    name = currentRow[j]
                    Log.d("name", name)
                } else {
                    // 열 값인 경우
                    value = currentRow[j]
                    Log.d("value", value)
                    productData[name] = value
                }

            }
        }

        Log.d("productData", productData.toString())

        // productData = <원산지, 베트남>,<코드, BL-11>,<중량,20>,<수량,356>,,,
        // productData를 List [[코드,BL-11],[원산지, 베트남],[품종, 고추],[수입날짜, 2023-06-15],,,]로 변경

        val orderList: ArrayList<ArrayList<String>> = arrayListOf(
            arrayListOf("코드"),
            arrayListOf("원산지"),
            arrayListOf("품종"),
            arrayListOf("수입날짜"),
            arrayListOf("반입날짜"),
            arrayListOf("중량"),
            arrayListOf("수량"),
            arrayListOf("단가"),
            arrayListOf("금액"),
            arrayListOf("비고")
        )
        for (i in 0 until orderList.size) {
            val correct: String = orderList[i][0] // ex. 코드
            Log.d("correct", correct)

            val value: String? = productData.get(correct) // ex.BL-11
            Log.d("value", value.toString())

            if (value != null) {
                orderList[i].add(value)
            }
        }

        Log.d("orderList", orderList.toString())
        return orderList
    }

    fun seperateList(inputList: ArrayList<ArrayList<String>>): ArrayList<ArrayList<String>> {
        val outputList: ArrayList<ArrayList<String>> = ArrayList()
        val List1: ArrayList<String> = ArrayList()
        val List2: ArrayList<String> = ArrayList()
        for (i in 0 until inputList.size) {
            val column: String = inputList[i][0]
            List1.add(column)
        }
        for (i in 0 until inputList.size) {
            val value: String = inputList[i][1]
            List2.add(value)
        }
        outputList.add(List1)
        outputList.add(List2)

        return outputList


    }


}