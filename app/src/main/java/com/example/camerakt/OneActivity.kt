package com.example.camerakt

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import com.example.camerakt.databinding.ActivityOneBinding
import com.example.camerakt.util.CameraUtil
import com.example.camerakt.viewmodel.OneViewModel
import com.example.myocr.util.MyEncoder
import java.io.File
import java.io.IOException


class OneActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1 // 카메라 사진 촬영 요청 코드
    lateinit var curPhotoPath: String // 문자열 형태의 사진 경로 값

    private lateinit var binding: ActivityOneBinding

    private val myEncoder = MyEncoder()

    var data: String? = null

    // 뷰 모델
    private val oneViewModel: OneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        oneViewModel.oneBitMapLiveData.observe(this) // this : oneActivity
        { bitmap -> binding.oneImage.setImageBitmap(bitmap) }

        binding.btnCameraOne.setOnClickListener {
            // list_Activity 의 frameLayOut 의 fragment 위치를 찾는 것
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container_one)
            // fragment가 있다면..?
            if (currentFragment is OneOcrFragment) {
                supportFragmentManager.beginTransaction()
                    .remove(currentFragment)
                    .commit()
                Log.d("btnCamera", "기존 fragment 삭제  ")
            }
            takeCapture()  // 기본 카메라 앱을 실행하여 사진 촬영
        }

        binding.btnOcrOne.setOnClickListener {
            //onClick(it)
            if (binding.oneImage.drawable != null) {

                onClick(it)

                //fragment 생성 해서 activity 위에 붙여놓음
                val fragment = OneOcrFragment()
                // fragment 해주는 역할
                val transaction = supportFragmentManager.beginTransaction()
                //listActivity
                transaction.replace(R.id.fragment_container_one, fragment)
                transaction.commit()
                
            } else {
                Toast.makeText(this, "oneImage가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                Log.d("empty Image", "이미지 존재하지 않음")
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(v: View) {
//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()

        when (v) {
            binding.btnOcrOne -> {
                Toast.makeText(this, "One - clicked", Toast.LENGTH_SHORT).show()
                data =
                    myEncoder.encodeImage(myEncoder.getBitmap(binding.oneImage.drawable.toBitmap())) // bitmap 가져와서 -> base64로 변환
                Log.d("시작", "시작")
                data?.let { oneViewModel.setInferred(it, this) } // 클릭시 post 값 띄움
            }
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
//            binding.oneImage.setImageBitmap(bitmap)
            oneViewModel.oneBitMapLiveData.value = bitmap
            Log.d("CHECK4", "여기까지 전달이 되나?")
            CameraUtil.savePhoto(this, bitmap)
        }
    }


}