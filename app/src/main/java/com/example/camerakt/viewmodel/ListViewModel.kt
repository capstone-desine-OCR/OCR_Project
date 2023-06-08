package com.example.camerakt.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.camerakt.repository.RepositoryImpl

class ListViewModel : ViewModel() {
    var repository = RepositoryImpl

    // 원래 private - getter로 전달해야하지만 ...
    val listBitMapLiveData: MutableLiveData<Bitmap> = MutableLiveData()

    // fragment
    val listTableData = MutableLiveData<ArrayList<ArrayList<String>>>()
    val listTableLiveData: LiveData<ArrayList<ArrayList<String>>> get() = listTableData

    //
    // 2가지 값을 한번에 묶어서 전달할 수 있음
    val editRowData = MutableLiveData<Pair<ArrayList<String>, Int>>()


    fun setInferred(data: String, context: Context) {
        repository.getResult(data) // 데이터를 가져오고

        val result = ArrayList<ArrayList<String>>()

        repository.lineReturn = {

            for (current in it) {
                var add_result = ArrayList<String>()
                if (current.first().contains("번호")) {
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

            listTableData.value =
                result  // ArrayList<ArrayList<String>>> MutableLiveData로 담아서 obsever가 인식할 수 있도록 view로 던짐
        }
    }
}
