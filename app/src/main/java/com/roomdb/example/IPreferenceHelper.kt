package com.roomdb.example

interface IPreferenceHelper {

    fun setApiKey(apiKey: String)
    fun getApiKey(): String
    fun setUserId(userId: String)
    fun getUserId(): String

    fun setStartTimer(startTime: String)
    fun getStartTimer(): String

    fun setEndTimer(endTime: String)
    fun getEndTimer(): String


    fun setSerialNo(slNo: String)
    fun getSerialNo(): String

    fun setDistrict(district: String)
    fun getDistrict(): String

    fun setDivision(division: String)
    fun getDivision(): String


    fun clearPrefs()

}