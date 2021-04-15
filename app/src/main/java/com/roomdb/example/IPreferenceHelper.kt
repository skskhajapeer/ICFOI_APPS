package com.roomdb.example

import com.roomdb.example.model.Sites

interface IPreferenceHelper {

    fun setApiKey(apiKey: String)
    fun getApiKey(): String
    fun setUserId(userId: String)
    fun getUserId(): String

    fun setStartTimer(startTime: String)
    fun getStartTimer(): String

    fun setEndTimer(endTime: String)
    fun getEndTimer(): String

    fun setProfileData(sites: Sites)
    fun getProfileData(): Sites

    fun isLogin(islogin:Boolean)


    fun setPassword(password: String)
    fun getPassword(): String

    fun setUserName(userName: String)
    fun getUserName(): String

    fun clearPrefs()

}