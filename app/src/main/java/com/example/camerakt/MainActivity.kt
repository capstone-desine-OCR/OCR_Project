package com.example.camerakt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.camerakt.database.DatabaseHelper
import com.example.camerakt.databinding.ActivityMainBinding
import com.example.myocr.util.MyEncoder
import com.example.myocr.viewmodel.MyViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

class MainActivity : AppCompatActivity() {
//    var : 변동 가능
//    val : 변동 불가능

    val REQUEST_IMAGE_CAPTURE = 1 // 카메라 사진 촬영 요청 코드
    lateinit var curPhotoPath: String // 문자열 형태의 사진 경로 값

    //encoder
    private val myEncoder = MyEncoder()

    //picture data
    var data: String? = null

    // 뷰 모델
    private val viewModel: MyViewModel by viewModels()

    // 뷰 바인딩 : 메인 액티비티 -> 액티비티메인
    private lateinit var binding: ActivityMainBinding


    private lateinit var db : DatabaseHelper
    // 메인
    @RequiresApi(Build.VERSION_CODES.O) // 버전체크
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = DatabaseHelper(this)


//       <뷰 바인더로 변경>
//       자동으로 완성된 액티비티 메인 바인딩 클래스 인스턴스 가져왔다
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myViewModel: MyViewModel = ViewModelProvider(this).get(MyViewModel::class.java) //

        // Obserber 객체 생성 : 뷰 모델이 데이터를 전달하기 위해 필요함
        val nameObserver = Observer<java.lang.StringBuilder> {
            binding.textView.text = it
        }

        // LifecycleOwner(이 코드에서는 액티비티에 해당)와 Observer객체를 넣어 LiveData 관찰
        myViewModel.liveData_String.observe(this, nameObserver)

        setPermission() // 앱 시작할때 권한 체크하는 메소드 수행

        binding.btnCamera.setOnClickListener {
            takeCapture()  // 기본 카메라 앱을 실행하여 사진 촬영
            //viewModel.setInferred(it)
        }
        binding.btnOcr.setOnClickListener {
            onClick(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(v: View) {
//        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()

        when (v) {
            binding.btnOcr -> {
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
                data =
                    myEncoder.encodeImage(myEncoder.getBitmap(binding.ivProfile.drawable.toBitmap())) // bitmap 가져와서 -> base64로 변환
                data?.let { viewModel.setInferred(it,this) } // 클릭시 post 값 띄움

            }
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

    // 테드 퍼미션 설정
    private fun setPermission() {
        val permission = object : PermissionListener {

            override fun onPermissionGranted() {  // 설정해놓은 위험 권한 들이 허용되었을 경우 이곳을 수행
                Toast.makeText(this@MainActivity, "권한이 허용 되었습니다.", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) { //설정해놓은 위험권한 둘 중 거부를 한 경우 이곳을 수행 
                Toast.makeText(this@MainActivity, "권한이 거부 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        //TedPermission을 쓰는 이유는 안드로이드 권한을 만드는 작업이 매우 복잡함 -> tedPermission 구문을 이용하면 훨씬 간편함
        // 최근에 변경되었다고함 with -> create
        TedPermission.create()
            .setPermissionListener(permission)
            .setRationaleMessage("카메라 앱을 사용하시려면 권한을 허용해주세요")
            .setDeniedMessage("권한을 거부하셨습니다. [앱 설정] -> [권한] 항목에서 허용해주세요.")
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE, // 외부공간에 쓰기 권한
                android.Manifest.permission.CAMERA // 
            )
            .check()
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
            binding.ivProfile.setImageBitmap(bitmap) // 바인딩 객체 -> imageView 타입
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


    //AndroidManifest fileProvider 설정
}


