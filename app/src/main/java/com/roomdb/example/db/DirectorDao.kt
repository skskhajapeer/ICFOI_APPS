package com.roomdb.example.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Antonina
 */
@Dao
interface DirectorDao {

    @Query("SELECT * FROM director WHERE title = :title LIMIT 1")
    suspend fun findMovieByTitle(title: String?): Director?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg directors: Director)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(director: Director)

    @Query("DELETE FROM director")
    suspend fun deleteAll()

    @get:Query("SELECT * FROM movie ORDER BY title ASC")
    val allMovies: LiveData<List<Movie>>
}