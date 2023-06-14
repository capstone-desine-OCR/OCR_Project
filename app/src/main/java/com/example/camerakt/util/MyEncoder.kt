package com.example.myocr.util

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

class MyEncoder {
    fun getBitmap(bm: Bitmap): Bitmap {
        var bitmap: Bitmap = bm
        return bitmap
    }

    fun encodeImage(bm: Bitmap): String? { // bitmap -> String 으로 반환
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 70, baos) // 70% 압축

        val bImage = baos.toByteArray()
        val base64 = Base64.encodeToString(bImage, Base64.NO_WRAP) // base64로 변환 + (\n) 제거
        return base64
    }
}