package com.roomdb.example.model

import com.google.gson.annotations.SerializedName

data class Sites(

    @SerializedName("Sl.No") val slNo: Int,
    @SerializedName("DISTRICT") val district: String,
    @SerializedName("DIVISION") val division: String,
    @SerializedName("RF BLOCK") val rfBlock: String,
    @SerializedName("RANGE") val range: String,
    @SerializedName("BEAT NAME") val beatName: String,
    @SerializedName("Area (Ha)") val areaHa: Double,
    @SerializedName("COMPARTMENT") val compartment: String,
    val startTime: String,
    val endTime: String,
    val latValue: String,
    val longitudeValue: String,
    val observerValue: String,
    val treeId: String,
    val sciName: String,
    val botanicalName: String,
    val gbhVal: String,
    val heightVal: String

)