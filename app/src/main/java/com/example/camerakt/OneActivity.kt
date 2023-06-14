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
    private var oneOcrFragment: OneOcrFragment? = null


    private lateinit var binding: ActivityOneBinding

    private val myEncoder = MyEncoder()

    var data: String? = null

    // 뷰 모델
    private val oneViewModel: OneViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // showCameraButton을 관찰하고 버튼 가시성 조정
        oneViewModel.visibilityData.observe(this) { visibilityPair ->
            binding.btnCameraOne.visibility = if (visibilityPair.first) View.VISIBLE else View.GONE
            binding.btnOcrOne.visibility = if (visibilityPair.second) View.VISIBLE else View.GONE
        }

        oneViewModel.oneBitMapLiveData.observe(this) // this : oneActivity
        { bitmap -> binding.oneImage.setImageBitmap(bitmap) }

        binding.btnCameraOne.setOnClickListener {
            // list_Activity 의 frameLayOut 의 fragment 위치를 찾는 것
            val currentFragment =
                supportFragmentManager.findFragmentById(R.id.fragment_container_one)

            if (currentFragment is OneOcrFragment) {
                supportFragmentManager.beginTransaction()
                    .remove(currentFragment)
                    .commit()
                Log.d("btnCamera", "기존 fragment 삭제  ")

            }

            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show()
            takeCapture()  // 기본 카메라 앱을 실행하여 사진 촬영
        }

        binding.btnOcrOne.setOnClickListener {

            if (binding.oneImage.drawable != null) {

                if (supportFragmentManager.findFragmentById(R.id.fragment_container_one) is OneOcrFragment) {
                    Toast.makeText(this, "인식 결과가 존재합니다. 재촬영 해주시길 바랍니다.", Toast.LENGTH_SHORT).show()
                } else {
                    onClick(it)
                    val fragment = OneOcrFragment()
                    val transaction = supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment_container_one, fragment)
                    transaction.commit()

                    // ocr인식 버튼 클릭 시 촬영, 인식 버튼 프래그먼트 화면에서 안보이게 설정
                    binding.btnCameraOne.visibility = View.GONE
                    binding.btnOcrOne.visibility = View.GONE
                }
            } else {
                Toast.makeText(this, "oneImage가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                Log.d("empty Image", "이미지 존재하지 않음")
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(v: View) {
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
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this, "com.example.camerakt.fileprovider", it
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE) // 21 이라 괜찮

                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val file = File(CameraUtil.curPhotoPath)
            val decode = ImageDecoder.createSource(
                this.contentResolver,
                Uri.fromFile(file)
            )
            val bitmap = ImageDecoder.decodeBitmap(decode)
            oneViewModel.oneBitMapLiveData.value = bitmap
            CameraUtil.savePhoto(this, bitmap)
        }
    }

    // oneActivity에서 인식 오류 시 clearImage=true 설정, OneActivity로 돌아옴
    // 이전에 촬영한 이미지 삭제
    private fun clearImage() {
        val imageView = binding.oneImage
        imageView.setImageDrawable(null)
    }

    override fun onResume() {
        super.onResume()
        if (OneActivity.clearImage) {
            clearImage()
            OneActivity.clearImage = false // 플래그 초기화
        }

    }

    companion object {
        var clearImage: Boolean = false
    }


}