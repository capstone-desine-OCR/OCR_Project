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
import com.example.camerakt.databinding.FragmentListOcrBinding
import com.example.camerakt.viewmodel.ListViewModel

class ListOcrFragment : Fragment() {

    // activityViewModel : activity 에서 생성된 특정 viewModel(ListViewModel) 을 공유할 수 있다 = fragment viewModel의 생명주기는 activity에 종속되어있음
    // viewModel liveData의 변경사항을 activity에서도 observe 할수 있고 , fragment에서도 observe 할 수 있다.
    // activity를 거치는 방법보단 바로 fragment로 보내는 방법을 택했기 때문에 동일한 viewModel을 공유하는 것으로 
    private val listViewModel: ListViewModel by activityViewModels()

    //fragment viewBinding 하는방법..?
    private var _binding: FragmentListOcrBinding? = null
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
        _binding = FragmentListOcrBinding.inflate(inflater, container, false)
        return binding.root
    }

    // onCreateView 에서의 view 객체가 파라미터로 전달되어짐 -> INITIALIZED 상태
    // View의 초기값 설정 , LiveData 옵저빙 ,RecyclerView , Adapter 세팅은 이 메소드에서 해주는 것
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        //           Test용
//        val data = arrayListOf(
//            arrayListOf("번호", "코드", "원산지", "품종", "수입 날짜", "반입 날짜", "중량", "수량", "단가", "총액", "비고"),
//            arrayListOf("1", "A", "Seoula", "a", "2023-05-01", "2023-05-30a", "akg", "10", "10000", "100000", "None"),
//            arrayListOf("2", "b", "Seoulb", "b", "2023-05-01", "2023-05-30b", "bkg", "10", "10000", "100000", "None"),
//            arrayListOf("3", "c", "Seoulc", "c", "2023-05-01", "2023-05-30c", "ckg", "10", "10000", "100000", "None"),
//            arrayListOf("4", "d", "Seould", "d", "2023-05-01", "2023-05-30d", "dkg", "10", "10000", "100000", "None"),
//            arrayListOf("5", "e", "Seoule", "e", "2023-05-01", "2023-05-30e", "ekg", "10", "10000", "100000", "None"),
//        )
//
//        val adapter = TableAdapter(data, listViewModel) // 생성자로써 전달
//        binding.recyclerView.adapter = adapter
//        binding.recyclerView.layoutManager =
//            LinearLayoutManager(requireContext()) // context , requiredContext() 동일 -> null 포함여부
//        listViewModel.listTableData.value = adapter.data
//          Test 용 data 종료


        // adapter 초기 init -> adapter는 ArrayList<>() 한줄임
        var adapter: TableAdapter = TableAdapter(ArrayList(), listViewModel)
        // requireContext() : Activity -> fragment 로 주는 context
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext()) // 선형 배치


        // data -> : observe에 의해  ArrayList<ArrayList<String>>의 변경이 감지되었을때 -> 이후 실행 ( data를 adapter로 넘긴다)
        // repository  -> adapter -> recy
        listViewModel.listTableData.observe(viewLifecycleOwner) { data ->
            adapter = TableAdapter(data, listViewModel)
            binding.recyclerView.adapter = adapter // recyclerView 로 adapter 를 붙여주는 것 -> ViewHolder를 붙여주는 것
        }

        listViewModel.listTableData.value = adapter.data
        Log.d("viewModel-table1 ", "초기화? : ${listViewModel.listTableData.value} ")


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

            // fragment - 종속된 activity 같이 종료 -> mainActivity로?
            requireActivity().finish()

            // 현재 fragment 창만 꺼지는듯 -> listActivity로
//            activity?.supportFragmentManager
//                ?.beginTransaction()
//                ?.remove(this)
//                ?.commit()

        }

    }


    // fragment 가 fragmentView 보다 오래 유지되기 떄문에 null을 쓴다.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}