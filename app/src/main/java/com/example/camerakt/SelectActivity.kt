package com.example.camerakt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.camerakt.adapter.ContractListAdapter
import com.example.camerakt.databinding.ActivityTableBinding
import com.example.camerakt.viewmodel.Contract

class SelectActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTableBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val intent = intent
        // ArrayList 객체를 가져옴
        val contracts = intent.extras?.getSerializable("contracts") as? ArrayList<Contract>
        Log.d("받아옴", "확인:${contracts.toString()}")
        val contractListAdapter = ContractListAdapter()
        contractListAdapter.submitList(contracts)
        binding.recyclerView.adapter = contractListAdapter

    }
}