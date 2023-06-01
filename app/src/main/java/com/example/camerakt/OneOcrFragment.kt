package com.example.camerakt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.databinding.FragmentOneOcrBinding
import com.example.camerakt.viewmodel.OneViewModel

class OneOcrFragment : Fragment() {

    // activityViewModel : activity 에서 생성된 특정 viewModel(ListViewModel) 을 공유할 수 있다 = fragment viewModel의 생명주기는 activity에 종속되어있음
    // viewModel liveData의 변경사항을 activity에서도 observe 할수 있고 , fragment에서도 observe 할 수 있다.
    // activity를 거치는 방법보단 바로 fragment로 보내는 방법을 택했기 때문에 동일한 viewModel을 공유하는 것으로
    private val oneViewModel: OneViewModel by activityViewModels()
    private val ocrTableService: OCRTableService = OCRTableService()

    //fragment viewBinding 하는방법..?
    private var _binding: FragmentOneOcrBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // activity->fragment bundle로 가져오는 데이터 fragments 내의 arguments 로 가져옴 -> bundle - arguments 는 대량의 데이터 가져오지못함
        // -> viewModel로 데이터 전달하는 것으로 교체
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOneOcrBinding.inflate(inflater, container, false)
        return binding.root
    }

    // onCreateView 에서의 view 객체가 파라미터로 전달되어짐 -> INITIALIZED 상태
    // View의 초기값 설정 , LiveData 옵저빙 ,RecyclerView , Adapter 세팅은 이 메소드에서 해주는 것
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        oneViewModel.oneTableData.observe(viewLifecycleOwner) { data ->
            Log.d("data", "data : $data")
            if (data.size != 0) {
                val oneList: ArrayList<String> = data[1]
                Log.d("oneList", "oneList : $oneList")
                binding.codeEditText.setText(oneList[0])
                binding.orginEditText.setText(oneList[1])
                binding.cultivarEditText.setText(oneList[2])
                binding.indateEditText.setText(oneList[3])
                binding.outdateEditText.setText(oneList[4])
                binding.weightEditText.setText(oneList[5])
                binding.countEditText.setText(oneList[6])
                binding.priceEditText.setText(oneList[7])
                binding.wonEditText.setText(oneList[8])
                binding.exrtaEditText.setText(oneList[9])
            }

        }


        binding.btnSave.setOnClickListener {
            Log.d("btn_save", "one_click")

            val modifiedRow = ArrayList<String>().apply {
                add(binding.codeEditText.text.toString())
                add(binding.orginEditText.text.toString())
                add(binding.cultivarEditText.text.toString())
                add(binding.indateEditText.text.toString())
                add(binding.outdateEditText.text.toString())
                add(binding.weightEditText.text.toString())
                add(binding.countEditText.text.toString())
                add(binding.priceEditText.text.toString())
                add(binding.wonEditText.text.toString())
                add(binding.exrtaEditText.text.toString())
            }

            for (modify in modifiedRow) {
                Log.d("modify", "modify : $modify")
            }
            val current_ocr = OCRTable()
            current_ocr.fromList(modifiedRow)
            ocrTableService.addProduct(current_ocr)
            
            requireActivity().finish()

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
