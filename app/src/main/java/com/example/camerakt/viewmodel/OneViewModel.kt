package com.example.camerakt.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.camerakt.OneActivity
import com.example.camerakt.R
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.repository.RepositoryImpl
import com.example.camerakt.util.Variable

class OneViewModel : ViewModel() {
    var repository = RepositoryImpl

    val oneBitMapLiveData: MutableLiveData<Bitmap> = MutableLiveData()

    var oneTableData = MutableLiveData<ArrayList<ArrayList<String>>>()

    val visibilityData: MutableLiveData<Pair<Boolean, Boolean>> = MutableLiveData()

    val oneTableLiveData: LiveData<ArrayList<ArrayList<String>>> get() = oneTableData

    val editRowData = MutableLiveData<Pair<ArrayList<String>, Int>>()

    private val ocrTableService: OCRTableService = OCRTableService()

    //fragment에서 오류 인식 -> 오류 인식 -> oneActivity로 돌아감
    fun handleRecognitionError(context: Context) {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        fragmentManager.beginTransaction()
            .remove(fragmentManager.findFragmentById(R.id.fragment_container_one)!!)
            .commit()

        val alertDialog = AlertDialog.Builder(context)
            .setTitle("인식 오류")
            .setMessage(
                "사진이 흔들렸거나 템플릿 양식이 올바르지 않습니다.\n" +
                        "올바르게 입력 후 재촬영 해주세요."
            )
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(context, OneActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                OneActivity.clearImage = true
                visibilityData.value = Pair(true, true)
                context.startActivity(intent)
            }
            .create()
        alertDialog.show()

    }


    fun setInferred(data: String, context: Context) {

        val ocrTable = OCRTable()
        var tmpList: ArrayList<ArrayList<String>>
        var oneList: ArrayList<ArrayList<String>>

        repository.getResult(data) // 데이터를 가져오고

        repository.lineReturn = lineReturn@{

            /* it
                [코드,BL-11],[원산지, 베트남],[중량,20,수량,356],,,*/

            /*tmpList
                [[코드, BL-11], [원산지, 베트남], [품종, 고추], [수입날짜, 2023-06-15], [반입날짜, 2023-06-27],
                 [중량, 20], [수량, 345], [단가, $15], [금액, W230.68], [비고, 하자X]]*/

            /* //error-1
             if (it.size != 10) {
                 Log.d("인식오류", "확인")
                 handleRecognitionError(context)
                 return@lineReturn // viewModel 종료
             }
 */
            //error-2
            try {
                tmpList = recombineArrayList(it, context)
            } catch (e: IllegalStateException) {
                e.message?.let { it1 -> Log.e("IllegalStateException", it1) }
                handleRecognitionError(context)
                return@lineReturn// viewModel 종료
            }
            Log.d("tmpList", tmpList.toString())
            /*oneList
                [[코드, 원산지, 품종, 수입날짜, 반입날짜, 중량, 수량, 단가, 금액, 비고],
                 [BL-11, 베트남, 고추, 2023-06-15, 2023-06-27, 20, 345, $15, W230.68, 하자X]]*/

            oneList = seperateList(tmpList)
            Log.d("oneList", oneList.toString())

            oneTableData.value = oneList

            //            for (i in oneList.indices) {
            //                if (i == 0) {
            //                    Log.d("pass", ".") //index 0 : [번호, 코드 , 원산지, 품종, 수입날짜 ,...,비고]
            //                } else {
            //                    val currentList = oneList[i]
            ////                    ocrTable.fromList(currentList)
            ////                    ocrTableService.addProduct(ocrTable)
            //                }
            //            }

            //            Log.d("products", ocrTableService.getAllProducts().toString())

            //liveData_String.postValue(result)


        }
    }

    fun recombineArrayList(
        inputList: ArrayList<ArrayList<String>>,
        context: Context
    ): ArrayList<ArrayList<String>> {
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

                    // 열 이름이 "코드", "원산지", "품종"...중에 하나라도 해당하지 않는 경우, 즉 잘못된 템플릿을 인식 시킨 경우
                    Log.d("name", name)
                    val recognitionError = nameCheck(name)
                    if (recognitionError == false) {
                        productData.clear()
                        //handleRecognitionError(context)
                        Log.d("productdata", productData.toString())
                        Log.d("error2", "체크 완료")
                        if (productData.isEmpty()) {
                            throw IllegalStateException("recognition error")
                        }
                    }

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
            val value: String =
                try {
                    inputList[i][1]
                } catch (e: IndexOutOfBoundsException) {
                    Variable.RECOGNITION_ERROR //인식이 잘못 되었을 경우 "인식 오류"값을 넣어주기
                }
            List2.add(value)
        }
        outputList.add(List1)
        outputList.add(List2)

        return outputList
    }

    fun nameCheck(name: String): Boolean {

        val validNames = listOf(
            "코드", "원산지", "품종", "반입날짜", "수입날짜",
            "중량", "수량", "금액", "비고", "단가"
        )
        return validNames.contains(name)
    }


}
