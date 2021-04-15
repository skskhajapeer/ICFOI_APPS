package com.roomdb.example

import android.content.Intent
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.dev.materialspinner.MaterialSpinner
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.roomdb.example.databinding.ActivityAddDataBinding
import com.roomdb.example.databinding.ActivityFaunaBinding
import com.roomdb.example.db.Movie
import com.roomdb.example.db.MoviesDatabase
import com.roomdb.example.dialog.ColorPickerDialog
import com.roomdb.example.model.AddDataModel
import com.roomdb.example.model.Sites
import com.roomdb.example.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_startup.*
import kotlinx.android.synthetic.main.startup.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.adawoud.bottomsheettimepicker.BottomSheetTimeRangePicker
import me.adawoud.bottomsheettimepicker.OnTimeRangeSelectedListener
import pl.utkala.searchablespinner.SearchableSpinner
import java.io.IOException
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FaunaActivity :AppCompatActivity(),  OnTimeRangeSelectedListener {
    var slNumbers: ArrayList<String> = ArrayList()

    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }

    private val tagBottomSheetTimeRangePicker = "tagBottomSheetTimeRangePicker"
    private lateinit var bindinggView: ActivityFaunaBinding
    private var distSpinnerValue: String? = null
    private var botnicalValue: String? = null
    private var siteInfo: Sites? = null
    private var slNoValue: String? = null
    var dataModel: AddDataModel? = null
    private var botanicalTypeValue: String? = null
    private var treeValue: String? = null
    private var movieTitleExtra: String? = null
    private var movieDirectorFullNameExtra: String? = null
    private var observerValue: String? = null;
    private var gbhValue: String? = null
    private var heightValue: String? = null
    private var latValue: String? = null
    private var siteSpinnerValue: String? = null
    private var startTimerValue: String? = null
    private var endTimerValue: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindinggView = ActivityFaunaBinding.inflate(layoutInflater)
        setContentView(bindinggView.root)
        val emailId = findViewById<TextView>(R.id.userEmailTv);
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val currentDate = current.format(formatter)
        Log.d("date",currentDate)

        /*  if (!preferenceHelper.getUserName().isEmpty()) {

              emailId.text = preferenceHelper.getUserName()
          }*/

        val mDrawerToggle = ActionBarDrawerToggle(
            this,
            bindinggView.contentRL,
            toolbar,
            R.string.drawer_open,
            R.string.close_drawer
        )

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

        val botanicalTypes = listOf("Birds", "Mammals", "Herpetofauna")
        val botanicalAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, botanicalTypes)
        botanicalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bindinggView.spinnerFauna.setAdapter(botanicalAdapter)


        bindinggView.botanicalName.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    botanicalTypeValue = parent!!.getItemAtPosition(position).toString()

                    if (botanicalTypeValue == "Birds") {
                        val birdNames = listOf(
                            "Sturnia pagodarum",
                            "Pycnonotus cafer",
                            "Leptocoma zeylonica",
                            "Lonchura punctulata",
                            "Ploceus philippinus",
                            "Spilopelia chinensis",
                            "Eudynamys scolopaceus",
                            "Prinia socialis",
                            "Prinia hodgsonii",
                            "Pericrocotus cinnamomeus",
                            "Acridotheres tristis",
                            "Francolinus pictus",
                            "Centropus sinensis",
                            "Pycnonotus luteolus",
                            "Pavo cristatus",
                            "Merops orientalis",
                            "Phaenicophaeus viridirostris",
                            "Prinia inornata",
                            "Caprimulgus asiaticus",
                            "Turdoides affinis",
                            "Dendrocitta vagabunda",
                            "Amaurornis phoenicurus",
                            "Corvus splendens",
                            "Bubo bengalensis",
                            "Streptopelia decaocto"


                        )

                        val botanicalTreeAdapter = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            birdNames
                        )
                        botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        bindinggView.botanicalName.setAdapter(botanicalTreeAdapter)
                        treeValue = botanical_name.getItemAtPosition(position).toString()
                    }
                    if (botanicalTypeValue == "Mammals") {
                        val mammalNames = listOf(
                            "Sus scrofa", "Lepus nigricollis", "Funambulus palmarum"
                        )
                        val botanicalTreeAdapter = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            mammalNames
                        )
                        botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        bindinggView.botanicalName.setAdapter(botanicalTreeAdapter)
                        treeValue = botanical_name.getItemAtPosition(position).toString()

                    }

                    if (botanicalTypeValue == "Herpetofauna") {
                        val herbNames = listOf(
                            "Peninsular rock agama",
                            "Eutropis carinata",
                            "Giant Leaf-toed Gecko",
                            "Treutler's gecko",
                            "Asian common toad",
                            "Indian tree frog",
                            "Indian bullfrog",
                            "Indian skipper frog",
                            "ornate narrow-mouthed frog"
                        )

                        val botanicalTreeAdapter = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            herbNames
                        )
                        botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        bindinggView.botanicalName.setAdapter(botanicalTreeAdapter)
                        treeValue = botanical_name.getItemAtPosition(position).toString()


                    }


                    if (botanicalTypeValue == "Insects") {
                        val insectNames = listOf(
                            "Peninsular rock agama",
                            "Eutropis carinata",
                            "Giant Leaf-toed Gecko",
                            "Treutler's gecko",
                            "Asian common toad",
                            "Indian tree frog",
                            "Indian bullfrog",
                            "Indian skipper frog",
                            "ornate narrow-mouthed frog"
                        )

                        val botanicalTreeAdapter = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            insectNames
                        )
                        botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        botanical_name.setAdapter(botanicalTreeAdapter)
                        treeValue = botanical_name.getItemAtPosition(position).toString()


                    }

                }

            }


        setUIData()

    }



    fun setUIData() {
        bindinggView.btnSelectTime.setOnClickListener(View.OnClickListener {
            BottomSheetTimeRangePicker
                .tabLabels(startTabLabel = "Start Time", endTabLabel = "End Time")
                .doneButtonLabel("Ok")
                .startTimeInitialHour(2)
                .startTimeInitialMinute(11)
                .endTimeInitialHour(10)
                .endTimeInitialMinute(22)
                .newInstance(this, DateFormat.is24HourFormat(this))
                .show(supportFragmentManager, tagBottomSheetTimeRangePicker)
        })

        bindinggView.etLat.setText(preferenceHelper.getUserId())
        bindinggView.etLong.setText(preferenceHelper.getStartTimer())

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

    override fun onTimeRangeSelected(
        startHour: Int,
        startMinute: Int,
        endHour: Int,
        endMinute: Int
    ) {
        var startHourString = startHour.toString()
        var startMinuteString = startMinute.toString()
        var endHourString = endHour.toString()
        var endMinuteString = endMinute.toString()
        when {
            startHour < 9 -> startHourString = startHour.toString().prependZero()
            startMinute < 9 -> startMinuteString = startMinute.toString().prependZero()
            endHour < 9 -> endHourString = endHour.toString().prependZero()
            endMinute < 9 -> endMinuteString = endMinute.toString().prependZero()
        }

        bindinggView.txtStime.text = startHourString + " " + startMinuteString
        bindinggView.txtEndTime.text = endHourString + " " + endMinuteString
    }

    private fun String.prependZero(): String {
        return "0".plus(this)
    }

    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }

    fun View.setVisible(visible: Boolean) {
        visibility = if (visible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    private suspend fun saveMovie(
        movieTitle: String,
        cDate:String,
        latlongvalue: String,
        distSpinner: String,
        siteSpinner: String,
        startTimer: String,
        endTimer: String,
        observerval: String,
        botanical: String,
        treeName: String,
        gbh: String,
        height: String,
        division: String,
        rfblock: String,
        range: String,
        beat: String,
        area: String,
        compt: String
    ) {
        /* if (TextUtils.isEmpty(movieTitle) || TextUtils.isEmpty(movieDirectorFullName)) {
             return
         }*/
        val directorDao = MoviesDatabase.getDatabase(this).directorDao()
        val movieDao = MoviesDatabase.getDatabase(this).movieDao()
        var directorId: Long = -1L

        if (movieTitleExtra != null) {
            // clicked on item row -> update
            val movieToUpdate = movieDao.findMovieByTitle(movieTitleExtra)
            if (movieToUpdate != null) {
                if (movieToUpdate.title != movieTitle) {
                    movieToUpdate.title = movieTitle
                    if (directorId != -1L) {
                        movieToUpdate.directorId = directorId
                    }
                    movieDao.update(movieToUpdate)
                }
            } else if (latValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(latValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.latVal != latlongvalue) {
                        movieToUpdate.latVal = latlongvalue
                        if (directorId != -1L) {
                            movieToUpdate.latVal = latlongvalue
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (observerValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(observerValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.obsvrval != observerval) {
                        movieToUpdate.obsvrval = observerval
                        if (directorId != -1L) {
                            movieToUpdate.obsvrval = observerval
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (distSpinnerValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(distSpinnerValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.distSpinner != distSpinner) {
                        movieToUpdate.distSpinner = distSpinner
                        if (directorId != -1L) {
                            movieToUpdate.distSpinner = distSpinner
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (siteSpinnerValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(siteSpinnerValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.siteSpinner != siteSpinner) {
                        movieToUpdate.siteSpinner = siteSpinner
                        if (directorId != -1L) {
                            movieToUpdate.siteSpinner = siteSpinner
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (startTimerValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(startTimerValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.startTimer != startTimer) {
                        movieToUpdate.startTimer = startTimer
                        if (directorId != -1L) {
                            movieToUpdate.startTimer = startTimer
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (endTimerValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(endTimerValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.endTimer != endTimer) {
                        movieToUpdate.endTimer = endTimer
                        if (directorId != -1L) {
                            movieToUpdate.endTimer = endTimer
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (botnicalValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(botnicalValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.botVal != botanical) {
                        movieToUpdate.botVal = botanical
                        if (directorId != -1L) {
                            movieToUpdate.botVal = botanical
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (treeValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(treeValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.treeName != treeName) {
                        movieToUpdate.treeName = treeName
                        if (directorId != -1L) {
                            movieToUpdate.treeName = treeName
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (gbhValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(gbhValue)
                if (movieToUpdate != null) {
                    if (movieToUpdate.gbhVal != gbh) {
                        movieToUpdate.gbhVal = gbh
                        if (directorId != -1L) {
                            movieToUpdate.gbhVal = gbh
                        }
                        movieDao.update(movieToUpdate)
                    }
                }
            } else if (heightValue != null) {
                // clicked on item row -> update
                val movieToUpdate = movieDao.findMovieByTitle(heightValue)
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
        } else {
            // we can have many movies with same title but different director
            // movieDao.insert(Movie(title = movieTitle, directorId = directorId, latVal="",distSpinner=""))
            movieDao.insert(
                Movie(
                    title = movieTitle,
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
                    comptName = compt,
                    cDate=cDate
                )
            )
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }


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


}