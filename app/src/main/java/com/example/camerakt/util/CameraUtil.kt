package com.example.camerakt.util

import android.content.Context
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.os.Environment
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.util.*

class CameraUtil {
    companion object {
        lateinit var curPhotoPath: String // 문자열 형태의 사진 경로 값

        fun createImageFile(context: Context): File? {
            val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
                .apply {
                    curPhotoPath = absolutePath
                }
        }

        fun savePhoto(context: Context, bitmap: Bitmap) {
            val folderPath =
                Environment.getExternalStorageDirectory().absolutePath + "/Pictures/" // 사진폴더로 저장하기 위한 경로 선언
            val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val fileName = "${timestamp}.jpeg"
            val folder = File(folderPath)

            if (!folder.isDirectory) {
                folder.mkdirs()
            }
            val out = FileOutputStream(folderPath + fileName)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)
            Toast.makeText(context, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}