package com.example.camerakt.util

import android.content.Context
import android.graphics.Bitmap
import android.icu.text.SimpleDateFormat
import android.os.Environment
import android.util.Log
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
            Log.d("CHECK5", "여기까지 전달이 되나?")
            val folderPath =
                Environment.getExternalStorageDirectory().absolutePath + "/Pictures/" // 사진폴더로 저장하기 위한 경로 선언
            Log.d("folderPath", "folderPath: ${folderPath}")
            Log.d("CHECK6", "여기까지 전달이 되나?")
            val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            Log.d("timestamp", "timestamp: ${timestamp}")
            val fileName = "${timestamp}.jpeg"
            Log.d("fileName", "fileName: ${fileName}")
            val folder = File(folderPath)
            Log.d("folder", "folder: ${folder}")
            Log.d("CHECK7", "여기까지 전달이 되나?")
            if (!folder.isDirectory) {
                folder.mkdirs()
            }
            Log.d("CHECK8", "여기까지 전달이 되나?")
            val out = FileOutputStream(folderPath + fileName)
            Log.d("CHECK9", "여기까지 전달이 되나?")
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)
            Log.d("CHECK10", "여기까지 전달이 되나?")

            Toast.makeText(context, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}