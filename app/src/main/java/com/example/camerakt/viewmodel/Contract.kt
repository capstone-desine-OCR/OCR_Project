package com.example.camerakt.viewmodel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Contract(
    var code: String,
    var orgin: String,
    var cultival: String,
    var indate: String,
    var outdate: String,
    var weight: Int,
    var count: Int,
    var price: String,
    var won: String,
    var extra: String

): Serializable {
    @SerializedName("weight")
    private var weightStr: String = weight.toString()

    @SerializedName("count")
    private var countStr: String = count.toString()

    constructor(): this( "", "", "", "", "", 0, 0, "", "", "")

}