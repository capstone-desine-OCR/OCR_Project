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
    private val listViewModel: ListViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //onActivityResult 의 결과 listBitMapLiveData 변경 감지 -> 화면에 나타나도록 함 = 데이터 유지
        listViewModel.listBitMapLiveData.observe(this) // this : listActivity
        { bitmap -> binding.listImage.setImageBitmap(bitmap) }

        
        binding.btnCameraList.setOnClickListener {

            // list_Activity 의 frameLay 의 fragment 위치를 찾는 것
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            // fragment가 있다면..?
            if (currentFragment is ListOcrFragment) {
                supportFragmentManager.beginTransaction()
                    .remove(currentFragment)
                    .commit()
                Log.d("btnCamera", "기존 fragment 삭제  ")
            }
            takeCapture()  // 기본 카메라 앱을 실행하여 사진 촬영
        }
        binding.btnOcrList.setOnClickListener {

            //fragment 생성
            val fragment = ListOcrFragment()
            // fragment 해주는 역할
            val transaction = supportFragmentManager.beginTransaction()
            //listActivity
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()

            onClick(it)// 기본 카메라 앱을 실행하여 사진 촬영

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(v: View) {
//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
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
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE) // 21 이라 괜찮

                }
            }
        }
    }

    // 사진을 찍은 결과값을 listViewModel 의 listBitMapLiveData 의 변화감지 data로 설정
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
//            binding.listImage.setImageBitmap(bitmap)
            listViewModel.listBitMapLiveData.value = bitmap  // 이미지 뷰에 bitmap을 저장후 -> observer로 재 갱신???

            Log.d("CHECK4", "여기까지 전달이 되나?")
            CameraUtil.savePhoto(this, bitmap)
        }
    }

//


    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart List", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "onResume List", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "onPause List", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "onRestart List", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "onStop List", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy List", Toast.LENGTH_SHORT).show()
    }

}