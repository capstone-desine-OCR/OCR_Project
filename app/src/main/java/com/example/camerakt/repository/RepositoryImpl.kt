package com.example.camerakt.repository


import android.util.Log
import com.example.camerakt.demand.RecognitionRequest
import com.example.camerakt.network.RetrofitClient
import com.example.camerakt.network.RetrofitService
import com.example.camerakt.response.Example
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response

//저장소 모듈은 데이터 작업을 처리합니다.
// 저장소는 지속 모델, 웹 서비스, 캐시 등 다양한 데이터 소스 간 중재자로 간주할 수 있습니다.

object RepositoryImpl : Repository {

    //retrofit
    private val retrofit = RetrofitClient.getInstance() //retrofit: 서버와의 원활한 소통을 도와주는 도구 불러옴
    private val retrofitService = retrofit.create(RetrofitService::class.java) // Retrofit 인터페이스 구현
    var requestData = RecognitionRequest() //요청 바디

    //    var result = ArrayList<String>()
//    var onReturn: ((ArrayList<String>) -> Unit)? = null


    var lineReturn: ((ArrayList<ArrayList<String>>) -> Unit)? = null
//    var lineReturn: ((ArrayList<ArrayList<String>>))? = null  // ??  이렇게 쓰면 invoke 함수를 쓰지못하는듯 ->

    override fun getResult(data: String) {
//        result.clear()

        //set RecognitionRequest
        val imageData = mutableListOf<com.example.camerakt.demand.ImageFields>()  // list
        imageData.add(com.example.camerakt.demand.ImageFields().apply {
            this.format = "png"
            this.data = data
            this.name = "test"
        })

        // 요청 body
        requestData = RecognitionRequest().apply {
            this.version = "V2"
            this.requestId = "test"
            this.timestamp = 0
            this.images = imageData
            this.enableTableDetection = true
        }

        //retrofitService.postRequest(requestData)를 호출하여 POST 요청을 생성하고,
        // enqueue 메서드를 호출하여 비동기적으로 요청을 실행합니다.
        retrofitService.postRequest(requestData).enqueue(object : retrofit2.Callback<Example> {
            override fun onResponse(call: Call<Example>, response: Response<Example>) {

                // 초기화 필요 - 2번찍으면 계속 중첩
                var lineList = ArrayList<ArrayList<String>>()

                Log.d("ddd", "Response_Code : ${response.code()} == ${response.message()}")
                Log.d(
                    "ddd",
                    "Header : ${ // 요청의 헤더 정보
                        retrofitService.postRequest(requestData).request().headers().toString()
                    }"
                )
                Log.d(
                    "ddd", // 요청의 url 정보
                    "Url : ${retrofitService.postRequest(requestData).request().url().toString()}"
                )

                if (response.isSuccessful) {
                    Log.d("ddd", "onResponse: response= ${response.body()}")


                    response.body()?.let {
                        for (image in it.images) {
                            if (image.tables != null) {
                                // 텍스트 테이블이 존재하는 경우
                                val tables = image.tables  // List<ImageTable> : tables
                                // 행과 열 정보, 셀 내용 등 활용
                                for (table in tables) {
                                    val cells = table.cells        // List<Tablecell> : "cells"

                                    for (cell in cells) {               // cell : TableCell

                                        // cell 내부에 rowIndex , colIndex 가 있음
                                        // list<list<String>> rowIndex 로 바깥위치
                                        var result = ArrayList<String>()
                                        var row = cell.rowIndex
                                        var col = cell.columnIndex

                                        // tables 는 세로로나열되어 있어서 ( col,row ) -> (0,0) (0,1) (0,2) (0,3) (0,4) ...
                                        //                                          -> (1,0) (1,1) (1,2) (1,2) (1,4) ....
                                        // 그래서 처음 col이 0이 나올떄 ArrayList<String> 만들어서 ArrayList<ArrayList<String>> 인 linList에 넣어줌
                                        if (col == 0)
                                            lineList.add(result)  // ArrayList<ArrayList<String>> 생성


                                        val cellTextLines =
                                            cell.cellTextLines    //  List<CellTextLine> cellTextLines

                                        for (cellTextLine in cellTextLines) {
                                            val cellWords = cellTextLine.cellWords  // List<CellWord> cellWords

                                            for (cellWord in cellWords) {

                                                val cellText = cellWord.inferText  // String inferText  ( 세로 )
                                                Log.d("cellWord-inferText", "$cellText")
                                                //lineList.get(row) = ArrayList<String>
                                                try {
                                                    lineList[row].add(cellText)  // 코틀린에서는 indexing 문법을 더 선호한다고 함 . get 보다
                                                } catch (e: IndexOutOfBoundsException) {
                                                    Log.d("error", "repository error")
                                                    e.printStackTrace()
                                                }


                                            }
                                        }
                                    }

                                }
                            } else {
                                // 텍스트 테이블이 없는 경우
                                Log.d("tableDetectrion", "텍스트 테이블이 없습니다")
                            }
                        }
                    }



                    Log.d("TAG", "Response body: " + Gson().toJson(response.body()))
//                    onReturn?.invoke(result) // onReturn 콜백을 호출 -> 결과값 전달
                    for (innerlist in lineList) {
                        Log.d("innerList Start ", "  ")
                        for (innerString in innerlist)
                            Log.d("innerString", "innerString: $innerString ")
                        Log.d("innerList End ", "  ")
                    }

                    lineReturn?.invoke(lineList)

                } else {
                    Log.d("ddd", "onResponse: notSuccessful")
                }
            }

            override fun onFailure(call: Call<Example>, t: Throwable) {
                Log.d("ddd", "onFailure: failed, ${t.message}")
            }
        })

    }
}
