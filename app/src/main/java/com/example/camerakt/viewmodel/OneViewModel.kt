package com.example.camerakt.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OneViewModel : ViewModel() {

    val oneBitMapLiveData: MutableLiveData<Bitmap> = MutableLiveData()


    fun setInferred(data: String, context: Context) {

    }
    // 뷰 모델

}