package com.roomdb.example.db

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * @author Antonina
 */
@Dao
interface FaunDataDao {

    @Query("SELECT * FROM faunadd WHERE slno = :sNo LIMIT 1")
    suspend fun findMovieByTitle(sNo: String?): FaunaAdding?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg directors: FaunaAdding)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(director: FaunaAdding)

    @Query("DELETE FROM director")
    suspend fun deleteAll()

    @get:Query("SELECT * FROM movie ORDER BY title ASC")
    val allMovies: LiveData<List<Movie>>
}