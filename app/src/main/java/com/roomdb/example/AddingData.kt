package com.roomdb.example

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
import com.roomdb.example.databinding.ActivityAddDataBinding
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


class AddingData : AppCompatActivity(), OnTimeRangeSelectedListener {
    var slNumbers: ArrayList<String> = ArrayList()

    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }

    private val tagBottomSheetTimeRangePicker = "tagBottomSheetTimeRangePicker"
    private lateinit var bindinggView: ActivityAddDataBinding
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
        bindinggView = ActivityAddDataBinding.inflate(layoutInflater)
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



        bindinggView.btnNext.setOnClickListener {

            bindinggView.mainrel.setVisible(false)
            bindinggView.layoutBotnical.setVisible(true)
        }



        if (movieTitleExtra != null) {
            bindinggView.etLat.setText(movieTitleExtra)
            bindinggView.etLat.setSelection(movieTitleExtra!!.length)
        }
        if (movieDirectorFullNameExtra != null) {
            bindinggView.etLong.setText(movieDirectorFullNameExtra)
            bindinggView.etLong.setSelection(movieDirectorFullNameExtra!!.length)
        }
        if (observerValue != null) {
            bindinggView.etObserver.setText(observerValue)
            bindinggView.etObserver.setSelection(observerValue!!.length)
        }
        /* if (botnicalValue != null) {
             botanicalvalue.setText(botnicalValue)
             botanicalvalue.setSelection(botnicalValue!!.length)
         }*/
        if (gbhValue != null) {
            bindinggView.etGbh.setText(gbhValue)
            bindinggView.etGbh.setSelection(gbhValue!!.length)
        }

        if (heightValue != null) {
            bindinggView.etHeight.setText(heightValue)
            bindinggView.etHeight.setSelection(heightValue!!.length)
        }


        val botanicalTypes = listOf("Trees", "Shrubs", "Herbs")
        val botanicalAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, botanicalTypes)
        botanicalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bindinggView.botanicalType.setAdapter(botanicalAdapter)


        bindinggView.botanicalType.onItemSelectedListener =
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

                    if (botanicalTypeValue == "Trees") {
                        val treeNames =
                            arrayOf<String>(*resources.getStringArray(R.array.tree_names))

                        val botanicalTreeAdapter = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            treeNames
                        )
                        botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        bindinggView.botanicalName.setAdapter(botanicalTreeAdapter)
                        treeValue =
                            bindinggView.botanicalName.getItemAtPosition(position).toString()
                    }
                    if (botanicalTypeValue == "Shrubs") {
                        val shrubNames = listOf(
                            "Ziziphus oenoplia", "Lantana camara", "Tylophora indica",
                            "Canthium coromandelicum"
                        )
                        val botanicalTreeAdapter = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            shrubNames
                        )
                        botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        bindinggView.botanicalName.setAdapter(botanicalTreeAdapter)
                        treeValue =
                            bindinggView.botanicalName.getItemAtPosition(position).toString()

                    }

                    if (botanicalTypeValue == "Herbs") {
                        val herbNames =
                            arrayOf<String>(*resources.getStringArray(R.array.herbs_names))

                        val botanicalTreeAdapter = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            herbNames
                        )
                        botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        bindinggView.botanicalName.setAdapter(botanicalTreeAdapter)
                        treeValue =
                            bindinggView.botanicalName.getItemAtPosition(position).toString()


                    }

                }

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
                    distSpinnerValue = parent!!.getItemAtPosition(position).toString()
                    slNumbers.filter { it == distSpinnerValue }
                    dataModel?.sites?.get(position)?.let { setDataBasedOnSelection(it) }
                    siteInfo = dataModel?.sites?.get(position)
                }

            }


        bindinggView.btnSave.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {

                saveMovie(

                   /* currentDate.toString(),
                    bindinggView.txtDist.text.toString(),
                    bindinggView.txtDivision.text.toString(),
                    bindinggView.txtRfBlock.text.toString(),
                    bindinggView.txtRange.text.toString(),
                    bindinggView.txtBeatName.text.toString(),
                    bindinggView.txtArea.text.toString(),
                    "",
                    bindinggView.txtStime.text.toString(),
                    bindinggView.txtEndTime.text.toString(),
                    bindinggView.etLat.text.toString(),
                    bindinggView.etLong.text.toString(),
                    bindinggView.etObserver.text.toString(),
                    treeValue.toString(),
                    bindinggView.etGbh.text.toString(),
                    bindinggView.etHeight.text.toString(),
                    "",""*/

                    currentDate.toString(),currentDate.toString(),bindinggView.txtDist.text.toString(),
                    bindinggView.txtRfBlock.text.toString(), bindinggView.txtRange.text.toString(), bindinggView.txtBeatName.text.toString(),
                    bindinggView.txtArea.text.toString(),"",bindinggView.txtStime.text.toString(),
                    bindinggView.txtEndTime.text.toString(), bindinggView.etLat.text.toString(),
                    bindinggView.etLong.text.toString(),bindinggView.etObserver.text.toString(),botanicalTypeValue.toString(),treeValue.toString(),
                    bindinggView.etGbh.text.toString(),
                    bindinggView.etHeight.text.toString(),""




                )
                siteInfo?.let { it1 ->
                    preferenceHelper.setProfileData(it1)
                }

            }

        }

        setUIData()
    }

    fun setDataBasedOnSelection(siteInfo: Sites) {
        bindinggView.txtDist.text = siteInfo.district
        bindinggView.txtDivision.text = siteInfo.division
        bindinggView.txtRfBlock.text = siteInfo.rfBlock
        bindinggView.txtCompartment.text = siteInfo.compartment
        bindinggView.txtArea.text = "" + siteInfo.areaHa
        bindinggView.txtBeatName.text = siteInfo.beatName
        bindinggView.txtRange.text = siteInfo.range



        if (siteInfo.rfBlock.contains(",")) {
            bindinggView.txtRfBlock.visibility = View.GONE
            bindinggView.rfblockSpinner.visibility = View.VISIBLE

            val compartments = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                siteInfo.rfBlock.split(",")
            )
            compartments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bindinggView.rfblockSpinner.setAdapter(compartments)

        } else {
            bindinggView.txtRfBlock.text = siteInfo.rfBlock
            bindinggView.rfblockSpinner.visibility = View.GONE
        }



        if (!siteInfo.compartment.isNullOrEmpty()) {
            if (siteInfo.compartment.contains(",")) {
                bindinggView.txtCompartment.visibility = View.GONE
                bindinggView.compartmentSpinner.visibility = View.VISIBLE

                val compartments = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item,
                    siteInfo.compartment.split(",")
                )
                compartments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                bindinggView.compartmentSpinner.setAdapter(compartments)

            } else {
                bindinggView.txtCompartment.text = siteInfo.compartment
                bindinggView.compartmentSpinner.visibility = View.GONE
            }
        }

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
                .newInstance(this@AddingData, DateFormat.is24HourFormat(this))
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