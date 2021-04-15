package com.roomdb.example

import android.Manifest
import android.R.attr.data
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import com.roomdb.example.db.Movie
import com.roomdb.example.movie.MoviesListAdapter
import com.roomdb.example.movie.MoviesViewModel
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class ResultActivity : AppCompatActivity() {
    private lateinit var moviesListAdapter: MoviesListAdapter
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var moviesList: List<Movie>
    private val EXTERNAL_STORAGE_PERMISSION_CODE = 23

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_movies)
       // initToolbar(getString(R.string.app_name))
        val recyclerView: RecyclerView = findViewById(R.id.recyclerview_movies)

        val btn_Export:Button=findViewById(R.id.btn_Export);
//        val mToolbar :Toolbar= findViewById(R.id.toolbar)
        btn_Export.setOnClickListener {

            exportDatabaseToCSVFile()

        }
        btn_Home.setOnClickListener {

            val intent = Intent(this, AddingData::class.java)
            startActivity(intent)
        }

        setSupportActionBar(findViewById(R.id.toolbar))

//        mToolbar.title = title
//        setSupportActionBar(mToolbar)

        moviesListAdapter = MoviesListAdapter(this)
        recyclerView.adapter = moviesListAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = LinearLayoutManager(this)
        initData()
    }

   /* private fun initToolbar(@NonNull title: String?) {
        toolbar.title = title
        setSupportActionBar(toolbar)
    }*/

    private fun initData() {
        moviesViewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)
        moviesViewModel.moviesList.observe(this,
            Observer { movies: List<Movie> ->
                moviesList = movies
                moviesListAdapter.setMovieList(movies)
            }
        )

       /* moviesViewModel.directorsList.observe(this,
            Observer { _ ->
                // we need to refresh the movies list in case when director's name changed
                moviesViewModel.moviesList.value?.let {
                    moviesList = it
                    moviesListAdapter.setMovieList(it)
                }
            }
        )*/
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_list_data -> {
                deleteCurrentListData()
                true
            }
            R.id.action_re_create_database -> {
                //  reCreateDatabase()
                true
            }
            R.id.action_export_to_csv_file -> {
                 exportDatabaseToCSVFile()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteCurrentListData() {
        if (MOVIES_SHOWN) {

            removeData()
            //  (shownFragment as MoviesListFragment).removeData()
        } else {
            // (shownFragment as DirectorsListFragment).removeData()
        }
    }



    companion object {
        private var MOVIES_SHOWN = true
    }

    fun removeData() {
          GlobalScope.launch(Dispatchers.IO) { moviesViewModel.deleteAll() }
    }

    private fun getCSVFileName() : String =
        if (MOVIES_SHOWN) "MoviesRoomExample.csv" else "DirectorsRoomExample.csv"

    private fun exportDatabaseToCSVFile() {
        val csvFile = generateFile(this, getCSVFileName())


        if (csvFile != null) {
            if (MOVIES_SHOWN) {
              exportDirectorsToCSVFile(csvFile)




            } else {
               // exportDirectorsToCSVFile(csvFile)
            }

            val emailIntent: Intent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "message/rfc822"
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "csv file")
            val contentUri =  FileProvider.getUriForFile(
                this,
                "${this.applicationContext.packageName}.fileprovider",
                csvFile
            )


            emailIntent.putExtra(Intent.EXTRA_STREAM, contentUri)


            emailIntent.putExtra(Intent.EXTRA_TEXT, "BODY_EMAIL")
            try {
                startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))

            } catch (ex: ActivityNotFoundException) {
            }



        } else {
            Toast.makeText(this, getString(R.string.csv_file_not_generated_text), Toast.LENGTH_LONG).show()
        }
    }


    fun exportDirectorsToCSVFile(csvFile: File) {
        csvWriter().open(csvFile, append = false) {


            // Header
         //   writeRow(listOf("id","Latitude","Longitude","District","Site","StartTime","EndTime","Observer","Botanical","Tree Name","GBH","Height"))
            writeRow(listOf("id","Date","District","Division","RF Block","Range","Beat Name","Area","Compartment",

                "Start Time","End Timer","Lattitude","Longitude","Observer","Tree Type","Scientific Name","GBH","Height"))

            moviesList.forEachIndexed { index, director ->
//                writeRow(listOf(index, director.directorId,director.title,director.latVal,
//                    director.distSpinner,director.siteSpinner,director.startTimer,director.endTimer))

               /* writeRow(listOf(index,director.title,director.latVal,
                    director.distSpinner,director.siteSpinner,director.startTimer,director.endTimer,director.obsvrval,director.botVal,
                    director.treeName,director.gbhVal,director.heightVal))*/

                writeRow(listOf(index,director.cDate,director.distSpinner,director.divVal,
                    director.rfBlock,director.range,director.beatName,director.areaName,director.comptName,
                    director.startTimer,director.endTimer,director.title,director.latVal,
                    director.obsvrval,director.botVal,director.treeName,director.gbhVal,director.heightVal))
            }
        }
    }




}