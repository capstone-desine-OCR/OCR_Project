package com.example.camerakt

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.camerakt.database.model.OCRTable
import com.example.camerakt.database.service.OCRTableService
import com.example.camerakt.databinding.FragmentOneOcrBinding
import com.example.camerakt.util.Variable
import com.example.camerakt.viewmodel.OneViewModel

class OneOcrFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private val oneViewModel: OneViewModel by activityViewModels()
    private val ocrTableService: OCRTableService = OCRTableService()

    private var _binding: FragmentOneOcrBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.isEnabled = false

        progressBar = binding.progressBar
        progressBar.isIndeterminate = true

        oneViewModel.oneTableData.observe(viewLifecycleOwner) { data ->
            Log.d("data", "data : $data")


            if (data.size != 0) {
                val oneList: ArrayList<String> = data[1]
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

                binding.btnSave.isEnabled = true // 저장버튼 활성화
            }

            progressBar.visibility = View.GONE

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

            val current_ocr = OCRTable()

            if (modifiedRow.contains(Variable.RECOGNITION_ERROR)) {
                Toast.makeText(requireContext(), Variable.OCRBTN_TOAST, Toast.LENGTH_SHORT).show()
            } else {
                current_ocr.fromList(modifiedRow)
                ocrTableService.addProduct(current_ocr)
                Toast.makeText(requireContext(), "저장되었습니다", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }

        }

    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
