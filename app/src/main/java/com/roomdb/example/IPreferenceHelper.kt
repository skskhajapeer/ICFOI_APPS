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

    fun clearPrefs()

}