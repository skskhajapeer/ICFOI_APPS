package com.example.room.mvvm.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.room.mvvm.model.LoginTableModel
import com.example.room.mvvm.repository.LoginRepository
import com.example.room.mvvm.room.DAOAccess
import com.example.room.mvvm.room.LoginDatabase
import com.roomdb.example.db.Movie
import com.roomdb.example.db.MovieDao
import com.roomdb.example.db.MoviesDatabase



class LoginViewModel (application: Application) : AndroidViewModel(application)  {

    var liveDataLogin: LiveData<List<LoginTableModel>>? = null


    private val movieDao: DAOAccess = LoginDatabase.getDataseClient(application).loginDao()

    fun insertData(context: Context, username: String, password: String) {
       LoginRepository.insertData(context, username, password)
    }

    /*init {
        liveDataLogin = movieDao.allMovies
        //     directorsList = directorDao.allDirectors
    }*/

    fun getLoginDetails(context: Context, username: String) : LiveData<List<LoginTableModel>>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username)
        return liveDataLogin
    }

}