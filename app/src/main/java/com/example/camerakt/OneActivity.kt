package com.example.camerakt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.camerakt.databinding.ActivityOneBinding

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
                    createImageFile()  // 이미지 파일 생성
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also { // null check 하려면 ? 을 써줘야 한다. -> ? 없을시 it null 인식해서 오류
                    val photoURI: Uri =
                        FileProvider.getUriForFile( // 사진 가져오는 것도 안드로이드에서는 서명 통해 안전하게 가져오도록 함
                            this, "com.example.camerakt.fileprovider",
                            it
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

    // 이미지 파일 생성
    private fun createImageFile(): File? {
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            getExternalFilesDir(Environment.DIRECTORY_PICTURES) //File? 파일을 가져오는 동안에 실패할 수도 있다.
        //createTempFile 임시파일을 만들어서 이미지뷰에 세팅하기 위해서 사용하는 것 - 실제 파일 저장 x
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir) // ${} 로 문자열 연결
            .apply { curPhotoPath = absolutePath }  // 경로는 절대경로
    }

    // startActivityForResult를 통해서 기본 카메라 앱으로 부터 받아온 사진 결과 값
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) { // startActivityForResult 통해서 기본 카메라 앱으로 부터 받아온 사진 결과 값
        super.onActivityResult(requestCode, resultCode, data)

        // 이미지를 성공적으로 가져왔다면 REQUEST_IMAGE_CAPTURE =1
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val bitmap: Bitmap             // bitmap 형태로 가져온다
            val file = File(curPhotoPath) // 사진이 저장된 절대 경로 , 현재 사진이 저장된 값
            val decode = ImageDecoder.createSource( // minSdk 28 안드로이드 9.0  이상부터 사용가능 -> 가져오는 방식이 다름
                this.contentResolver,
                Uri.fromFile(file)
            )

            bitmap = ImageDecoder.decodeBitmap(decode) // decode 한번 해준뒤에
            binding.oneImage.setImageBitmap(bitmap) // 바인딩 객체 -> imageView 타입
            savePhoto(bitmap)
        }
    }

    // 갤러리에 저장
    private fun savePhoto(bitmap: Bitmap) {
        val folderPath =
            Environment.getExternalStorageDirectory().absolutePath + "/Pictures/" // 사진폴더로 저장하기 위한 경로 선언
        val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val fileName = "${timestamp}.jpeg"
        val folder = File(folderPath)
        if (!folder.isDirectory) { // 현재 해당 경로에 폴더가 존재하지 않는다면
            folder.mkdirs() // 폴더 생성
        }

        //실제 저장처리
        val out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out) // bitmap 압축
//        Bitmap.createScaledBitmap(bitmap, (bitmap.width * 40), (bitmap.height * 50).toInt(), true)
        Toast.makeText(this, "사진이 저장되었습니다.", Toast.LENGTH_SHORT).show()
    }

}