package com.roomdb.example.director

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.roomdb.example.db.Director
import com.roomdb.example.db.DirectorDao
import com.roomdb.example.db.MoviesDatabase

/**
 * @author Antonina
 */
class DirectorsViewModel(application: Application) : AndroidViewModel(application) {

    private val directorDao: DirectorDao = MoviesDatabase.getDatabase(application).directorDao()
    val directorList: LiveData<List<Director>>

    init {
        directorList = directorDao.allDirectors
    }

    suspend fun insert(vararg directors: Director) {
        directorDao.insert(*directors)
    }

    suspend fun update(director: Director) {
        directorDao.update(director)
    }

    suspend fun deleteAll() {
        directorDao.deleteAll()
    }
}