package com.example.camerakt.util

class Variable {
    companion object {
        const val RECOGNITION_ERROR: String = "인식 오류"
        val nameList: ArrayList<String> by lazy {
            arrayListOf(
                "코드",
                "원산지",
                "품종",
                "수입날짜",
                "반입날짜",
                "중량",
                "수량",
                "단가",
                "금액",
                "비고"
            )
        }
    }
}