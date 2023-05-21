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

    val listTableData = MutableLiveData<ArrayList<ArrayList<String>>>()
    val listLiveData: LiveData<ArrayList<ArrayList<String>>> get() = listTableData

    fun setInferred(data: String, context: Context) {
        repository.getResult(data) // 데이터를 가져오고

        repository.lineReturn = {
            listTableData.value = it  // ArrayList<ArrayList<String>>> MutableLiveData로 담아서 obsever가 인식할 수 있도록 view로 던짐
        }
    }
}
