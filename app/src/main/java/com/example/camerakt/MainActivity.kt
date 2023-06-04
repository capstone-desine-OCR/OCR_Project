package com.example.camerakt

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.camerakt.databinding.ActivityMainBinding
import com.example.camerakt.viewmodel.Contract
import com.example.myocr.util.MyEncoder
import com.example.myocr.viewmodel.MyViewModel
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

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


    var contracts = ArrayList<Contract>()

    //private lateinit var db : DatabaseHelper
    // 메인
    @RequiresApi(Build.VERSION_CODES.O) // 버전체크
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //db = DatabaseHelper(this)


//       <뷰 바인더로 변경>
//       자동으로 완성된 액티비티 메인 바인딩 클래스 인스턴스 가져왔다
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myViewModel: MyViewModel = ViewModelProvider(this).get(MyViewModel::class.java) //

        // Obserber 객체 생성 : 뷰 모델이 데이터를 전달하기 위해 필요함
        val nameObserver = Observer<java.lang.StringBuilder> {
//            binding.textView.text = it
        }

        // LifecycleOwner(이 코드에서는 액티비티에 해당)와 Observer객체를 넣어 LiveData 관찰
        myViewModel.liveData_String.observe(this, nameObserver)

        setPermission() // 앱 시작할때 권한 체크하는 메소드 수행

        binding.btnTable.setOnClickListener {
            //val intent = Intent(this, SelectActivity::class.java)
            //startActivity(intent)
            contracts.clear()
            val contract1 = Contract(
                "001", "Origin 1", "Cultival 1", "2023-06-01", "2023-06-10",
                10, 2, "100", "KRW", "Extra 1"
            )

            val contract2 = Contract(
                "002", "Origin 2", "Cultival 2", "2023-06-05", "2023-06-15",
                15, 3, "150", "KRW", "Extra 2"
            )
            contracts.add(contract1)
            contracts.add(contract2)
            contracts.add(
                Contract(
                    "003", "Origin 3", "Cultival 3", "2023-06-12", "2023-06-20",
                    8, 1, "80", "KRW", "Extra 3"
                )
            )

            contracts.add(
                Contract(
                    "004", "Origin 4", "Cultival 4", "2023-06-07", "2023-06-14",
                    12, 4, "200", "KRW", "Extra 4"
                )
            )

            contracts.add(
                Contract(
                    "005", "Origin 5", "Cultival 5", "2023-06-18", "2023-06-25",
                    20, 3, "180", "KRW", "Extra 5"
                )
            )

            contracts.add(
                Contract(
                    "006", "Origin 6", "Cultival 6", "2023-06-22", "2023-06-29",
                    14, 2, "120", "KRW", "Extra 6"
                )
            )

            contracts.add(
                Contract(
                    "007", "Origin 7", "Cultival 7", "2023-06-03", "2023-06-08",
                    5, 2, "90", "KRW", "Extra 7"
                )
            )

            contracts.add(
                Contract(
                    "008", "Origin 8", "Cultival 8", "2023-06-17", "2023-06-23",
                    18, 5, "250", "KRW", "Extra 8"
                )
            )

            contracts.add(
                Contract(
                    "009", "Origin 9", "Cultival 9", "2023-06-10", "2023-06-17",
                    16, 3, "150", "KRW", "Extra 9"
                )
            )

            contracts.add(
                Contract(
                    "010", "Origin 10", "Cultival 10", "2023-06-25", "2023-07-02",
                    22, 4, "200", "KRW", "Extra 10"
                )
            )


            Log.d("인텐트", "넘김 : ${contracts.toString()}")
            val intent = Intent(this, SelectActivity::class.java) // 인텐트 객체 생성
            intent.putExtra("contracts", ArrayList(contracts)) // 인텐트에 데이터 전달
            startActivity(intent)

        }

        binding.btnList.setOnClickListener {
            val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
        }

        binding.btnOne.setOnClickListener {
            val intent = Intent(this, OneActivity::class.java)
            startActivity(intent)
        }
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
}


