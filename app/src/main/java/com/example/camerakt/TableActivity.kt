package com.example.camerakt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
//import com.example.camerakt.adapter.TableAdapter
import com.example.camerakt.databinding.ActivityTableBinding


class TableActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTableBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)
        binding = ActivityTableBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        val intent = intent
        // ArrayList 객체를 가져옴
        val contracts = intent.extras?.getSerializable("contracts") as? ArrayList<Contract>
         */

        //val tableAdapter = TableAdapter()
        //contractListAdapter.submitList(contracts)
        //binding.recyclerView.adapter = tableAdapter


    }
}