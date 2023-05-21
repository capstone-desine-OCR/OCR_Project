package com.example.camerakt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.camerakt.adapter.TableAdapter
import com.example.camerakt.databinding.FragmentListOcrBinding
import com.example.camerakt.viewmodel.ListViewModel

class ListOcrFragment : Fragment() {
    private val listViewModel: ListViewModel by activityViewModels()

    //fragment viewBinding 하는방법..?
    private var _binding: FragmentListOcrBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // activity->fragment bundle로 가져오는 데이터 fragments 내의 arguments 로 가져옴
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
//        val data = listOf(
//            listOf(
//                "Number",
//                "Code",
//                "Origin",
//                "Cultivar",
//                "In Date",
//                "Out Date",
//                "Weight",
//                "Count",
//                "Price",
//                "Won",
//                "Extra"
//            ),
//            listOf("1", "A", "Seoula", "a", "2023-05-01", "2023-05-30a", "akg", "10", "10000", "100000", "None"),
//            listOf("2", "b", "Seoulb", "b", "2023-05-01", "2023-05-30b", "bkg", "10", "10000", "100000", "None"),
//            listOf("3", "c", "Seoulc", "c", "2023-05-01", "2023-05-30c", "ckg", "10", "10000", "100000", "None"),
//            listOf("4", "d", "Seould", "d", "2023-05-01", "2023-05-30d", "dkg", "10", "10000", "100000", "None"),
//            listOf("5", "e", "Seoule", "e", "2023-05-01", "2023-05-30e", "ekg", "10", "10000", "100000", "None"),
//        )

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        listViewModel.listTableData.observe(viewLifecycleOwner) { data ->
            val adapter = TableAdapter(data)
            binding.recyclerView.adapter = adapter
        }
    }

    // fragment 가 fragmentView 보다 오래 유지되기 떄문에 null을 쓴다.
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}