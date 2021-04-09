package com.roomdb.example.movie

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.roomdb.example.R
import com.roomdb.example.db.Movie
import com.roomdb.example.db.MoviesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/**
 * @author Antonina
 */
class MoviesListAdapter(private val parent: Activity) : RecyclerView.Adapter<MoviesListAdapter.MoviesViewHolder>() {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(parent)
    private var movieList: List<Movie>? = null

    fun setMovieList(movieList: List<Movie>?) {
        this.movieList = movieList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_list_movie, parent, false)
        return MoviesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        movieList?.let { list ->
            val movie = list[position]

            holder.tvDistrict.text = movie.distSpinner
            holder.tvSite.text=movie.siteSpinner
            holder.tvStartTime.text=movie.startTimer
            holder.tvEndTime.text=movie.endTimer
            holder.tvLatitude.text=movie.title
            holder.tvLongitude.text=movie.latVal
            holder.tvObserver.text=movie.obsvrval
            holder.tvBotanical.text=movie.botVal
            holder.tvGbh.text=movie.gbhVal
            holder.tvHeight.text=movie.heightVal

            // holder.directorText.text= movie.directorId

            runBlocking {
                val directorFullName = withContext(Dispatchers.Default) {
                    getDirectorFullName(movie)
                }

               // holder.directorText.text = directorFullName ?: ""

              /*  holder.itemView.setOnClickListener {
                    val dialogFragment: DialogFragment = newInstance(movie.title, directorFullName,movie.latVal,movie.distSpinner)
                    dialogFragment.setTargetFragment(parent, 99)
                    dialogFragment.show(
                        (parent.activity as AppCompatActivity).supportFragmentManager,
                        MovieSaveDialogFragment.TAG_DIALOG_MOVIE_SAVE
                    )
                }*/
            }
        }
    }

    private suspend fun getDirectorFullName(movie: Movie): String? {
        return MoviesDatabase.getDatabase(parent).directorDao().findDirectorById(movie.directorId)?.fullName
    }

    override fun getItemCount(): Int {
        return if (movieList == null) {
            0
        } else {
            movieList!!.size
        }
    }

    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDistrict: TextView = itemView.findViewById(R.id.tvDistrict)
        val tvSite: TextView = itemView.findViewById(R.id.tvSite)
        val tvStartTime :TextView =itemView.findViewById(R.id.tvStartTime)
        val tvEndTime :TextView =itemView.findViewById(R.id.tvEndTime)
        val tvLatitude :TextView =itemView.findViewById(R.id.tvLatitude)
        val tvLongitude :TextView =itemView.findViewById(R.id.tvLongitude)
        val tvObserver :TextView =itemView.findViewById(R.id.tvObserver)
        val tvTreeId :TextView =itemView.findViewById(R.id.tvTreeId)
        val tvGbh :TextView =itemView.findViewById(R.id.tvGbh)
        val tvBotanical :TextView =itemView.findViewById(R.id.tvBotanical)
        val tvHeight :TextView =itemView.findViewById(R.id.tvHeight)



    }
}
