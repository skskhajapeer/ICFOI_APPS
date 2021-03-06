package com.example.room.mvvm.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.room.mvvm.model.LoginTableModel
import com.roomdb.example.db.Movie

@Dao
interface DAOAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertData(loginTableModel: LoginTableModel)

    @Query("SELECT * FROM Login WHERE Username =:username")
    fun getLoginDetails(username: String?) : LiveData<List<LoginTableModel>>


    @Query("SELECT * FROM Login")
    fun getUserDetails() : LiveData<List<LoginTableModel>>

}