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
    @SerializedName("START TIME") val startTime: String,
    @SerializedName("END TIME") val endTime: String,
    @SerializedName("LATITUDE") val latValue: String,
    @SerializedName("LONGITUDE") val longitudeValue: String,
    @SerializedName("OBSERVER") val observerValue: String,
    @SerializedName("TREEID") val treeId: String,
    @SerializedName("SCIENAME") val sciName: String,
    @SerializedName("BOTNAME") val botanicalName: String,
    @SerializedName("GBH") val gbhVal: String,
    @SerializedName("HEIGHT") val heightVal: String

)