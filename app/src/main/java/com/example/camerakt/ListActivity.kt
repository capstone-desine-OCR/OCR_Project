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
import com.example.camerakt.databinding.ActivityListBinding
import com.example.camerakt.util.CameraUtil
import com.example.camerakt.viewmodel.ListViewModel
import com.example.myocr.util.MyEncoder
import java.io.File
import java.io.IOException
import java.util.*


class ListActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 1 // 카메라 사진 촬영 요청 코드
    lateinit var curPhotoPath: String // 문자열 형태의 사진 경로 값

    private lateinit var binding: ActivityListBinding

    private val myEncoder = MyEncoder()

    var data: String? = null

    // 뷰 모델
    // val listViewModel: ListViewModel = ViewModelProvider(this).get(ListViewModel::class.java) // 동일한 역할...?
    // by viewModels() : 독립적인 수명주기를 가질 수 있도록 한다.
    private val listViewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val viewModel = ViewModelProvider(this).get(ListViewModel::class.java)

        listViewModel.visibilityData.observe(this) { visibilityPair ->
            binding.btnCameraList.visibility = if (visibilityPair.first) View.VISIBLE else View.GONE
            binding.btnOcrList.visibility = if (visibilityPair.second) View.VISIBLE else View.GONE
        }

        //onActivityResult 의 결과 listBitMapLiveData 변경 감지 -> 화면에 나타나도록 함 = 데이터 유지
        listViewModel.listBitMapLiveData.observe(this) // this : listActivity
        { bitmap -> binding.listImage.setImageBitmap(bitmap) }


        binding.btnCameraList.setOnClickListener {

            // list_Activity 의 frameLayOut 의 fragment 위치를 찾는 것
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

            // fragment가 있을떄 다시 사진을 찍는다면 fragment를 삭제한다
            if (currentFragment is ListOcrFragment) {
                supportFragmentManager.beginTransaction()
                    .remove(currentFragment)
                    .commit()
                Log.d("btnCamera", "기존 fragment 삭제  ")
            }


            takeCapture()  // 기본 카메라 앱을 실행하여 사진 촬영
        }

        binding.btnOcrList.setOnClickListener {
            if (binding.listImage.drawable != null) {

                //fragment 생성 해서 activity 위에 붙여놓음
                val fragment = ListOcrFragment()
                // fragment 해주는 역할
                val transaction = supportFragmentManager.beginTransaction()
                //listActivity
                transaction.replace(R.id.fragment_container, fragment)
                transaction.commit()

                // ocr인식 버튼 클릭 시 촬영, 인식 버튼 프래그먼트 화면에서 안보이게 설정
                binding.btnCameraList.visibility = View.GONE
                binding.btnOcrList.visibility = View.GONE

                onClick(it)// 기본 카메라 앱을 실행하여 사진 촬영
            } else {
                Toast.makeText(this, "listImage가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                Log.d("empty Image", "이미지 존재하지 않음")
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(v: View) {
        when (v) {
            binding.btnOcrList -> {
                Toast.makeText(this, "List - clicked", Toast.LENGTH_SHORT).show()
                data =
                    myEncoder.encodeImage(myEncoder.getBitmap(binding.listImage.drawable.toBitmap())) // bitmap 가져와서 -> base64로 변환
                data?.let { listViewModel.setInferred(it, this) } // 클릭시 post 값 띄움

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
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    // 사진을 찍은 결과값을 listViewModel 의 listBitMapLiveData 의 변화감지 data로 설정
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val file = File(CameraUtil.curPhotoPath)
            val decode = ImageDecoder.createSource(
                this.contentResolver,
                Uri.fromFile(file)
            )
            val bitmap = ImageDecoder.decodeBitmap(decode)

            listViewModel.listBitMapLiveData.value = bitmap

            CameraUtil.savePhoto(this, bitmap)
        }
    }

    // ListActivity에서 인식 오류 시 clearImage=true 설정, ListActivity로 돌아옴
    private fun clearImage() {
        val imageView = binding.listImage
        imageView.setImageDrawable(null)
    }


    override fun onStart() {
        super.onStart()
        Log.d("listActivity", "onStart List")
    }

    override fun onResume() {
        super.onResume()
        Log.d("listActivity", "onResume List")
        if (clearImage) {
            clearImage()
            clearImage = false // 플래그 초기화
        }
    }


    override fun onPause() {
        super.onPause()
        Log.d("listActivity", "onPause List")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("listActivity", "onRestart List")
    }

    override fun onStop() {
        super.onStop()
        Log.d("listActivity", "onStop List")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("listActivity", "onDestroy List")
    }

    companion object {
        var clearImage: Boolean = false
    }
    
}