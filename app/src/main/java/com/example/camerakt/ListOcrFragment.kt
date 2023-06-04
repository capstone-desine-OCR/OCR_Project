package com.example.camerakt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.camerakt.adapter.CustomItemDecoration
import com.example.camerakt.adapter.TableAdapter
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.databinding.FragmentListOcrBinding
import com.example.camerakt.viewmodel.ListViewModel

class ListOcrFragment : Fragment() {

    // activityViewModel : activity 에서 생성된 특정 viewModel(ListViewModel) 을 공유할 수 있다 = fragment viewModel의 생명주기는 activity에 종속되어있음
    // viewModel liveData의 변경사항을 activity에서도 observe 할수 있고 , fragment에서도 observe 할 수 있다.
    // activity를 거치는 방법보단 바로 fragment로 보내는 방법을 택했기 때문에 동일한 viewModel을 공유하는 것으로 
    private val listViewModel: ListViewModel by activityViewModels()


    private val ocrTableService: OCRTableService = OCRTableService()

    //fragment viewBinding 하는방법..?
    private var _binding: FragmentListOcrBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TableAdapter
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
        _binding = FragmentListOcrBinding.inflate(inflater, container, false)
        return binding.root
    }

    // onCreateView 에서의 view 객체가 파라미터로 전달되어짐 -> INITIALIZED 상태
    // View의 초기값 설정 , LiveData 옵저빙 ,RecyclerView , Adapter 세팅은 이 메소드에서 해주는 것
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // requireContext() : Activity -> fragment 로 주는 context
        // RecyclerView 를 어떻게 배치할지 : 선형배치 방식으로!
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // adapter 초기 init -> adapter는 ArrayList<>() 한줄임
        adapter = TableAdapter(ArrayList(), listViewModel)
        binding.recyclerView.adapter = adapter


        // data -> : observe에 의해  ArrayList<ArrayList<String>>의 변경이 감지되었을때 -> 이후 실행 ( data를 adapter로 넘긴다)
        // repository  -> adapter -> recy
        listViewModel.listTableData.observe(viewLifecycleOwner) { data ->

            adapter = TableAdapter(data, listViewModel)
            binding.recyclerView.adapter =
                adapter // recyclerView 로 adapter 를 붙여주는 것 -> ViewHolder를 붙여주는 것
        }


        // 목록 수정 adapter -> dialog -> fragment
        listViewModel.editRowData.observe(viewLifecycleOwner) { pair ->
            val modifiedRow = pair.first
            val position = pair.second
            Log.d("modifiedRow 수정 가능", " modifiedRow : $modifiedRow ")
            Log.d("modifiedRow position확인", "position : $position 입니다")
            adapter.data[position] = modifiedRow
            adapter.notifyItemChanged(position)
            listViewModel.listTableData.value = adapter.data
            Log.d("viewModel-table-edit ", "변경시점 : ${listViewModel.listTableData.value} ")
        }

        // recyclerView 에 line_devider 이용해 줄 그리는 deco
        val itemDecoration = CustomItemDecoration(30)
        binding.recyclerView.addItemDecoration(itemDecoration)

        binding.btnSave.setOnClickListener {
            Log.d("btnSave", "ocr 결과 화면 종료")
            Log.d("viewModel-table2 ", "종료 시점 : ${listViewModel.listTableData.value} ")
            Log.d("viewModel-edit ", "최근에 변한 위치 : ${listViewModel.editRowData.value} ")

            listViewModel.listTableData.observe(viewLifecycleOwner) { pair ->
                val current_list: ArrayList<ArrayList<String>> = pair
                val current_ocr = OCRTable()
                for (innerList in current_list) {
//                    try {
//                        Integer.valueOf(innerList.first())
//                    } catch (e: NumberFormatException) {
//                        continue
//                    }
                    current_ocr.fromList(innerList)
                    ocrTableService.addProduct(current_ocr)
                }
            }
            // fragment - 종속된 activity 같이 종료 -> mainActivity로?
            requireActivity().finish()

        }
    }


    // fragment 가 fragmentView 보다 오래 유지되기 떄문에 null을 쓴다.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}