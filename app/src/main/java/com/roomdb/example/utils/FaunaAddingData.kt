package com.roomdb.example.utils

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.dev.materialspinner.MaterialSpinner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.roomdb.example.*
import com.roomdb.example.databinding.ActivityAddDataBinding
import com.roomdb.example.databinding.ActivityFaunaAddDataBinding
import com.roomdb.example.db.FaunaAdding
import com.roomdb.example.db.Movie
import com.roomdb.example.db.MoviesDatabase
import com.roomdb.example.model.AddDataModel
import com.roomdb.example.model.Sites
import com.roomdb.example.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_startup.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.adawoud.bottomsheettimepicker.BottomSheetTimeRangePicker
import me.adawoud.bottomsheettimepicker.OnTimeRangeSelectedListener
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class FaunaAddingData : AppCompatActivity() {
    var slNumbers: ArrayList<String> = ArrayList()

    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }

    private lateinit var bindinggView: ActivityFaunaAddDataBinding
    private var slNoValue: String? = null
    private var dateValue: String? = null
    private var gpsValue: String? = null
    private var siteNameValue: String? = null
    private var comptNoValue: String? = null
    private var beatNameValue: String? = null
    private var sectionValue: String? = null
    private var rangeValue: String? = null
    private var divisionValue: String? = null;
    private var districtValue: String? = null
    private var transectIdValue: String? = null
    private var scientificValue: String? = null
    private var commonNameValue: String? = null
    private var familyValue: String? = null
    private var orderValue: String? = null
    private var numberValue: String? = null

    var dataModel: AddDataModel? = null

    private var siteInfo: Sites? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindinggView = ActivityFaunaAddDataBinding.inflate(layoutInflater)
        setContentView(bindinggView.root)
        val emailId = findViewById<TextView>(R.id.userEmailTv);
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val faunaTypeValue = intent.getStringExtra("textValue")
        Log.d("textValue", "${faunaTypeValue}")

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val currentDate = current.format(formatter)
        Log.d("date",currentDate)
        dateValue=currentDate
        if (faunaTypeValue == "Birds") {
            val treeNames =
                arrayOf<String>(*resources.getStringArray(R.array.birds_family_names))

            val botanicalTreeAdapter = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_item,
                treeNames
            )
            botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bindinggView.txtFamily.setAdapter(botanicalTreeAdapter)

        }
        if (gpsValue != null) {
            bindinggView.txtGps.setText(gpsValue)
        }

        if (siteNameValue != null) {
            bindinggView.txtSiteName.setText(siteNameValue)
        }
        if (comptNoValue != null) {
            bindinggView.txtCompartment.setText(comptNoValue)
        }
        if (beatNameValue != null) {
            bindinggView.txtBeatName.setText(beatNameValue)
        }
        if (sectionValue != null) {
            bindinggView.txtSection.setText(sectionValue)
        }

        if (rangeValue != null) {
            bindinggView.txtRange.setText(rangeValue)
        }

        if (divisionValue != null) {
            bindinggView.txtDivision.setText(divisionValue)
        }

        if (districtValue != null) {
            bindinggView.txtDist.setText(districtValue)
        }
        if (transectIdValue != null) {
            bindinggView.txtTransId.setText(transectIdValue)
        }


        bindinggView.btnSave.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {

                saveMovie(



                    slNoValue.toString(),currentDate.toString(),bindinggView.txtGps.text.toString(),
                    bindinggView.txtSiteName.text.toString(), bindinggView.txtCompartment.text.toString(), bindinggView.txtBeatName.text.toString(),
                    bindinggView.txtSection.text.toString(),bindinggView.txtRange.text.toString(),bindinggView.txtDivision.text.toString(),
                    bindinggView.txtDist.text.toString(), "",
                    "","","","", "","","")
                siteInfo?.let { it1 ->
                    preferenceHelper.setProfileData(it1)
                }

            }

        }

        val mDrawerToggle = ActionBarDrawerToggle(
            this,
            bindinggView.contentRL,
            toolbar,
            R.string.drawer_open,
            R.string.close_drawer
        )

        val gson = Gson()
        dataModel = gson.fromJson(loadJSONFromAsset(), AddDataModel::class.java)
        for (i in 0 until dataModel?.sites?.size!!) {
            val userDetail = dataModel?.sites?.get(i)
            slNumbers.add(userDetail?.slNo.toString())

            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, slNumbers)
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bindinggView.spinnerSlno.setAdapter(aa)
            //           }

        }

        bindinggView.spinnerSlno.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    slNoValue = parent!!.getItemAtPosition(position).toString()

                  //  distSpinnerValue = parent!!.getItemAtPosition(position).toString()
                    slNumbers.filter { it == slNoValue }
                    dataModel?.sites?.get(position)?.let { setDataBasedOnSelection(it) }
                    siteInfo = dataModel?.sites?.get(position)
                }

            }


        bindinggView.contentRL.setDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        navigationItemSelect()

        AppPreferences.isLogin = true

//        val file =  File("/data/data/com.roombd.example/shared_prefs/SharedPreferenceDemo.xml");
//        if (file.exists()){
//
//           // dataModel = preferenceHelper.getProfileData()
//
//        }else {










        /* if (botnicalValue != null) {
             botanicalvalue.setText(botnicalValue)
             botanicalvalue.setSelection(botnicalValue!!.length)
         }*/



        val botanicalTypes = listOf("Trees", "Shrubs", "Herbs")
        val botanicalAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, botanicalTypes)
        botanicalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      //  bindinggView.botanicalType.setAdapter(botanicalAdapter)







    }







    private fun navigationItemSelect() {
        nav.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.notes -> {

                }
                R.id.addNewNote -> {
                    //  openAddNotes()
                }
                R.id.syncNotes -> {

                    // syncNotesWithFirebase()
                }
                R.id.rating -> {
                    Toast.makeText(this, "Soon this will add", Toast.LENGTH_SHORT).show()
                }
                R.id.shareApp -> {

                }

                R.id.logout -> {

                    AppPreferences.isLogin = false
                    AppPreferences.username = ""
                    AppPreferences.password = ""
                    setupAlertDialogButton()

                }

            }
            bindinggView.contentRL.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setupAlertDialogButton() {
            MaterialAlertDialogBuilder(this)
                .setTitle("Logout")
                .setMessage("Are you sure want to logout.")
                .setPositiveButton("Ok") { dialog, which ->
                   // Toast.makeText(this, "Clicked discard", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    //Toast.makeText(this, "Clicked cancel", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()
    }

    fun setDataBasedOnSelection(siteInfo: Sites) {
        bindinggView.txtDist.text = siteInfo.district
        bindinggView.txtDivision.text = siteInfo.division
        bindinggView.txtCompartment.text = siteInfo.compartment
        bindinggView.txtBeatName.text = siteInfo.beatName
        bindinggView.txtRange.text = siteInfo.range








    }
    fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("faundata.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            val charset: Charset = Charsets.UTF_8
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, charset)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }
    private suspend fun saveMovie(

        cDate:String,
        distSpinner: String,
        division: String,
        rfblock: String,
        range: String,
        beat: String,
        area: String,
        compt: String,
        startTimer: String,
        endTimer: String,
        movieTitle: String,
        latlongvalue: String,
        observerval: String,
        botanical: String,
        treeName: String,
        gbh: String,
        height: String,
        siteSpinner: String







    ) {
        /* if (TextUtils.isEmpty(movieTitle) || TextUtils.isEmpty(movieDirectorFullName)) {
             return
         }*/
        val directorDao = MoviesDatabase.getDatabase(this).directorDao()
        val movieDao = MoviesDatabase.getDatabase(this).movieDao()
        var directorId: Long = -1L

        if (slNoValue != null) {
            // clicked on item row -> update
            val movieToUpdate = movieDao.findMovieByTitle(slNoValue)
            if (movieToUpdate != null) {
                if (movieToUpdate.title != cDate) {
                    movieToUpdate.title = cDate
                    if (directorId != -1L) {
                        movieToUpdate.directorId = directorId
                    }
                    movieDao.update(movieToUpdate)
                }
            } else if (dateValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(dateValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.latVal != distSpinner) {
                        movieToUpdate.latVal = distSpinner
                        if (directorId != -1L) {
                            movieToUpdate.latVal = distSpinner
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (gpsValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(gpsValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.obsvrval != division) {
                        movieToUpdate.obsvrval = division
                        if (directorId != -1L) {
                            movieToUpdate.obsvrval = division
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (siteNameValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(siteNameValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.distSpinner != rfblock) {
                        movieToUpdate.distSpinner = rfblock
                        if (directorId != -1L) {
                            movieToUpdate.distSpinner = rfblock
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (comptNoValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(comptNoValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.siteSpinner != range) {
                        movieToUpdate.siteSpinner = range
                        if (directorId != -1L) {
                            movieToUpdate.siteSpinner = range
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (beatNameValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(beatNameValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.startTimer != beat) {
                        movieToUpdate.startTimer = beat
                        if (directorId != -1L) {
                            movieToUpdate.startTimer = beat
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (sectionValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(sectionValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.endTimer != area) {
                        movieToUpdate.endTimer = area
                        if (directorId != -1L) {
                            movieToUpdate.endTimer = area
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (rangeValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(rangeValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.botVal != compt) {
                        movieToUpdate.botVal = compt
                        if (directorId != -1L) {
                            movieToUpdate.botVal = compt
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (divisionValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(divisionValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.treeName != startTimer) {
                        movieToUpdate.treeName = startTimer
                        if (directorId != -1L) {
                            movieToUpdate.treeName = startTimer
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (districtValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(districtValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.gbhVal != endTimer) {
                        movieToUpdate.gbhVal = endTimer
                        if (directorId != -1L) {
                            movieToUpdate.gbhVal = endTimer
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (transectIdValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(transectIdValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.heightVal != movieTitle) {
                        movieToUpdate.heightVal = movieTitle
                        if (directorId != -1L) {
                            movieToUpdate.heightVal = movieTitle
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            }

            else if (scientificValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(scientificValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.heightVal != latlongvalue) {
                        movieToUpdate.heightVal = latlongvalue
                        if (directorId != -1L) {
                            movieToUpdate.heightVal = latlongvalue
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            }

            else if (commonNameValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(commonNameValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.heightVal != observerval) {
                        movieToUpdate.heightVal = observerval
                        if (directorId != -1L) {
                            movieToUpdate.heightVal = observerval
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            }

            else if (familyValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(familyValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.heightVal != botanical) {
                        movieToUpdate.heightVal = botanical
                        if (directorId != -1L) {
                            movieToUpdate.heightVal = botanical
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            }

            else if (orderValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(orderValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.heightVal != treeName) {
                        movieToUpdate.heightVal = treeName
                        if (directorId != -1L) {
                            movieToUpdate.heightVal = treeName
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            }

            else if (numberValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(numberValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.heightVal != gbh) {
                        movieToUpdate.heightVal = gbh
                        if (directorId != -1L) {
                            movieToUpdate.heightVal = gbh
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            }

            else if (numberValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(numberValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.heightVal != height) {
                        movieToUpdate.heightVal = height
                        if (directorId != -1L) {
                            movieToUpdate.heightVal = height
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            }

            else if (numberValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(numberValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.heightVal != siteSpinner) {
                        movieToUpdate.heightVal = siteSpinner
                        if (directorId != -1L) {
                            movieToUpdate.heightVal = siteSpinner
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            }
        } else {
            // we can have many movies with same title but different director
            // movieDao.insert(Movie(title = movieTitle, directorId = directorId, latVal="",distSpinner=""))
            movieDao.insert(
                Movie(
                    title = movieTitle,
                    cDate=cDate,
                    directorId = directorId,
                    latVal = latlongvalue,
                    distSpinner = distSpinner,
                    siteSpinner = siteSpinner,
                    startTimer = startTimer,
                    endTimer = endTimer,
                    obsvrval = observerval,
                    botVal = botanical,
                    treeName = treeName,
                    gbhVal = gbh,
                    heightVal = height,
                    divVal = division,
                    rfBlock = rfblock,
                    range = range,

                    beatName = beat,
                    areaName = area,
                    comptName = compt

                )
            )
            Toast.makeText(this,"Added Data",Toast.LENGTH_LONG).show()
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }


    }
}