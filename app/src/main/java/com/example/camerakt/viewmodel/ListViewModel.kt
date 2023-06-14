package com.example.camerakt.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.camerakt.ListActivity
import com.example.camerakt.R
import com.example.camerakt.repository.RepositoryImpl

class ListViewModel : ViewModel() {
    var repository = RepositoryImpl
    val listBitMapLiveData: MutableLiveData<Bitmap> = MutableLiveData()

    val listTableData = MutableLiveData<ArrayList<ArrayList<String>>>()
    val listTableLiveData: LiveData<ArrayList<ArrayList<String>>> get() = listTableData

    val editRowData = MutableLiveData<Pair<ArrayList<String>, Int>>()

    val visibilityData: MutableLiveData<Pair<Boolean, Boolean>> = MutableLiveData()

    fun handleRecognitionError(context: Context) {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        fragmentManager.beginTransaction()
            .remove(fragmentManager.findFragmentById(R.id.fragment_container)!!)
            .commit()

        val alertDialog = AlertDialog.Builder(context)
            .setTitle("인식 오류")
            .setMessage(
                "사진이 흔들렸거나 템플릿 양식이 올바르지 않아 오류가 발생하였습니다." +
                        "올바르게 입력 후 재촬영 해주세요."
            )
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(context, ListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                ListActivity.clearImage = true
                visibilityData.value = Pair(true, true) // 촬영, ocr 인식 버튼이 보여지게
                context.startActivity(intent)
            }
            .create()
        alertDialog.show()
    }

    fun setInferred(data: String, context: Context) {
        repository.getResult(data) // 데이터를 가져오고

        val result = ArrayList<ArrayList<String>>()

        repository.lineReturn = lineReturn@{
            // 템플릿 양식에 맞지않는 이미지를 인식하는 등 잘못 인식 시
            for (i in 0 until it.size) {
                if (it[i].size != 10) {
                    handleRecognitionError(context)
                    return@lineReturn
                }
            }



            for (current in it) {
                var add_result = ArrayList<String>()
                if (current.first().contains("코드")) {
                    continue
                } else {
                    try {
                        Integer.valueOf(current.first())
                    } catch (e: NumberFormatException) {
                        result.add(current)
                        continue
                    }
                    current.remove(current.first())
                    result.add(current)
                }
            }

            listTableData.value = result
            // ArrayList<ArrayList<String>>> MutableLiveData로 담아서 obsever가 인식할 수 있도록 view로 던짐
        }
    }


}