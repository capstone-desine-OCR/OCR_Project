package com.example.camerakt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.camerakt.databinding.ActivityOneBinding
import com.example.camerakt.util.CameraUtil

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class OneActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1 // 카메라 사진 촬영 요청 코드
    lateinit var curPhotoPath: String // 문자열 형태의 사진 경로 값

    private lateinit var binding: ActivityOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCameraOne.setOnClickListener {
            takeCapture()  // 기본 카메라 앱을 실행하여 사진 촬영
            //viewModel.setInferred(it)
        }
    }

    private fun takeCapture() {
        // 기본 카메라 앱 실행
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    CameraUtil.createImageFile(this)// 이미지 파일 생성
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also { // null check 하려면 ? 을 써줘야 한다. -> ? 없을시 it null 인식해서 오류
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this, "com.example.camerakt.fileprovider", it
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    // 사진 결과물을 다시 가져와야함
                    // forResult : mainActivity -> serverActivity -> mainActivity 받게되는 결과값
                    // 카메라 기본 앱도 Activity 형태라 찍은 사진 결과물을 result를 통해서 받는것
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE) // 21 이라 괜찮

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("CHECK1", "여기까지 전달이 되나?")
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val file = File(CameraUtil.curPhotoPath)
            val decode = ImageDecoder.createSource(
                this.contentResolver,
                Uri.fromFile(file)
            )
            Log.d("CHECK2", "여기까지 전달이 되나?")
            val bitmap = ImageDecoder.decodeBitmap(decode)

            Log.d("CHECK3", "여기까지 전달이 되나?")
            binding.oneImage.setImageBitmap(bitmap)
            Log.d("CHECK4", "여기까지 전달이 되나?")
            CameraUtil.savePhoto(this, bitmap)
        }
    }


}