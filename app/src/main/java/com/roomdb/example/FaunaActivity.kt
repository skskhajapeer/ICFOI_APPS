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
import com.roomdb.example.db.Movie
import com.roomdb.example.db.MoviesDatabase
import com.roomdb.example.dialog.ColorPickerDialog
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

class FaunaActivity :AppCompatActivity(), OnTimeRangeSelectedListener,
    AdapterView.OnItemSelectedListener {
    private val tagBottomSheetTimeRangePicker = "tagBottomSheetTimeRangePicker"
    private lateinit var distSpinner: MaterialSpinner
    private lateinit var spinner_site: MaterialSpinner
    private lateinit var botanical_type: MaterialSpinner
    private lateinit var botanical_name: SearchableSpinner
    val list_of_items =
        arrayOf("Select Country", "Hyderabad", "Secunderabad", "RangaReddy", "Yadadri")
    val list_of_sites = arrayOf("Select Site", "Siteone", "Sitetwo", "Sitethree")
    private var movieTitleExtra: String? = null
    private var movieDirectorFullNameExtra: String? = null
    private var latValueExtra: String? = null
    private var observerValue: String? = null;

    private var latValue: String? = null
    private var distSpinnerValue: String? = null

    private var siteSpinnerValue: String? = null

    private var startTimerValue: String? = null
    private var endTimerValue: String? = null
    private var botnicalValue: String? = null

    private var gbhValue: String? = null
    private var heightValue: String? = null
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }

    private var botanicalTypeValue: String? = null

    private var treeValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fauna)
        val selectTime = findViewById<Button>(R.id.btn_select)
        distSpinner = findViewById(R.id.dist_spinner);
        spinner_site = findViewById(R.id.spinner_site);
        botanical_type = findViewById(R.id.botanical_type);
        botanical_name = findViewById(R.id.botanical_name);


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val mDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_open,
            R.string.close_drawer
        )

        drawerLayout.setDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        navigationItemSelect()

        val botanicalTypes = listOf("Birds", "Mammals", "Herpetofauna","Insects","Threats")
        val botanicalAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, botanicalTypes)
        botanicalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        botanical_type.setAdapter(botanicalAdapter)


        botanical_type.getSpinner().onItemSelectedListener =
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
                        botanical_name.setAdapter(botanicalTreeAdapter)
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
                        botanical_name.setAdapter(botanicalTreeAdapter)
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
                        botanical_name.setAdapter(botanicalTreeAdapter)
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
        /*  try {
              val obj = JSONObject(loadJSONFromAsset())
             val userArray = obj.getJSONObject("Flora")


                 for (i in 0 until userArray.length()) {

                     val mylist: JSONArray? = userArray.getJSONArray(i)
                     val countriesArrayList = ArrayList<String>()
                     countriesArrayList.add(mylist.toString())
                     for (j in countriesArrayList)
                     botanicalTypes.add(countriesArrayList.toString());
                     // flordata=userDetail.getString("name")
                     Log.d("flora", flordata.toString())


                 }
          } catch (e: JSONException) {
              e.printStackTrace()
          }*/




        distSpinner.getSpinner().onItemSelectedListener = this
        spinner_site.getSpinner().onItemSelectedListener = this
//        botanical_type.getSpinner().onItemSelectedListener = this


        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        distSpinner.setAdapter(aa)


        distSpinner.getSpinner().onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    if (position == 0) {
                        distSpinner.setError("Please select Country")
                    } else {
                        distSpinnerValue = parent!!.getItemAtPosition(position).toString()
                        Log.d("spinnervalue", distSpinnerValue!!);
                        distSpinner.setErrorEnabled(false)
                        distSpinner.setLabel("Country")

                    }

                }

            }
        val bb = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_sites)
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_site.setAdapter(bb)


        spinner_site.getSpinner().onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    if (position == 0) {
                        spinner_site.setError("Please select Site")
                    } else {
                        siteSpinnerValue = parent!!.getItemAtPosition(position).toString()
                        Log.d("spinnervalue", siteSpinnerValue!!);
                        spinner_site.setErrorEnabled(false)
                        spinner_site.setLabel("Site")

                    }

                }

            }
        fbChooseColor.setOnClickListener {
            showColorDialog()
        }

        val latvalues = findViewById<EditText>(R.id.et_lat)
        val longitudevalues = findViewById<EditText>(R.id.et_long)
        val observervalue = findViewById<EditText>(R.id.et_observer)

        //   val botanicalvalue =findViewById<EditText>(R.id.et_botanical_name)
        val ghbvalue = findViewById<EditText>(R.id.et_gbh)
        val heightvalue = findViewById<EditText>(R.id.et_height)

        val latlongData = preferenceHelper.getUserId()
        val longitudeData = preferenceHelper.getStartTimer()
        Log.d("latval", latlongData);
        Log.d("longval", longitudeData);

        latvalues.setText(preferenceHelper.getUserId())
        longitudevalues.setText(preferenceHelper.getStartTimer())
        if (movieTitleExtra != null) {
            latvalues.setText(movieTitleExtra)
            latvalues.setSelection(movieTitleExtra!!.length)
        }
        if (movieDirectorFullNameExtra != null) {
            longitudevalues.setText(movieDirectorFullNameExtra)
            longitudevalues.setSelection(movieDirectorFullNameExtra!!.length)
        }
        if (observerValue != null) {
            observervalue.setText(observerValue)
            observervalue.setSelection(observerValue!!.length)
        }
        /* if (botnicalValue != null) {
             botanicalvalue.setText(botnicalValue)
             botanicalvalue.setSelection(botnicalValue!!.length)
         }*/
        if (gbhValue != null) {
            ghbvalue.setText(gbhValue)
            ghbvalue.setSelection(gbhValue!!.length)
        }

        if (heightValue != null) {
            heightvalue.setText(heightValue)
            heightvalue.setSelection(heightValue!!.length)
        }

        /* if (endTimerValue != null) {
             txt_endtime.setText(endTimerValue)
         }
         if (startTimerValue != null) {
             txt_stime.text(startTimerValue)
         }
 */
        /*  if (siteSpinnerValue != null) {
              latvalues.setText(latValue)
              latvalues.setSelection(latValue!!.length)
          }*/


        selectTime.setOnClickListener {
            BottomSheetTimeRangePicker
                .tabLabels(startTabLabel = "Start Time", endTabLabel = "End Time")
                .doneButtonLabel("Ok")
                .startTimeInitialHour(2)
                .startTimeInitialMinute(11)
                .endTimeInitialHour(10)
                .endTimeInitialMinute(22)
                .newInstance(this, DateFormat.is24HourFormat(this))
                .show(supportFragmentManager, tagBottomSheetTimeRangePicker)
        }


        btn_save.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {

                saveMovie(
                    latvalues.text.toString(),
                    longitudevalues.text.toString(),
                    distSpinnerValue.toString(),
                    siteSpinnerValue.toString(),
                    "","",
//                    txt_stime.text.toString(),
//                    txt_endtime.text.toString(),
                    observervalue.text.toString(),
                    botanicalTypeValue.toString(),
                    treeValue.toString(),
                    ghbvalue.text.toString(),
                    heightvalue.text.toString()
                )

            }

        }
    }

    private suspend fun saveMovie(
        movieTitle: String,
        latlongvalue: String,
        distSpinner: String,
        siteSpinner: String,
        startTimer: String,
        endTimer: String,
        observerval: String,
        botanical: String,
        treeName:String,
        gbh: String,
        height: String
    ) {
        /* if (TextUtils.isEmpty(movieTitle) || TextUtils.isEmpty(movieDirectorFullName)) {
             return
         }*/
        val directorDao = MoviesDatabase.getDatabase(this).directorDao()
        val movieDao = MoviesDatabase.getDatabase(this).movieDao()
        var directorId: Long = -1L
        /*if (movieDirectorFullNameExtra != null) {
             // clicked on item row -> update
             val directorToUpdate = directorDao.findDirectorByName(movieDirectorFullNameExtra)
             if (directorToUpdate != null) {
                 directorId = directorToUpdate.id
                 if (directorToUpdate.fullName != movieDirectorFullName) {
                     directorToUpdate.fullName = movieDirectorFullName
                     directorDao.update(directorToUpdate)


                 }
             }
         } else {
             // we need director id for movie object; in case director is already in DB,
             // insert() would return -1, so we manually check if it exists and get
             // the id of already saved director
             val newDirector = directorDao.findDirectorByName(movieDirectorFullName)
             directorId = newDirector?.id ?: directorDao.insert(Director(fullName = movieDirectorFullName))
         }*/

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
            }

            else if (treeValue != null) {
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
            }



            else if (gbhValue != null) {
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
                    treeName=treeName,
                    gbhVal = gbh,
                    heightVal = height
                )
            )
            val intent = Intent(this, ResultActivity::class.java)
            startActivity(intent)
        }

        /*if (latValue != null) {
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

            else  if (distSpinnerValue != null) {
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
            }
        } else {

            movieDao.insert(Movie(title = movieTitle, directorId = directorId, latVal=latlongvalue,distSpinner=distSpinner))
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }*/

        /*   if (distSpinnerValue != null) {
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
           } else {
               // we can have many movies with same title but different director
               movieDao.insert(Movie(title = movieTitle, directorId = directorId, latVal=latlongvalue,distSpinner=distSpinner))
               val intent = Intent(this,MainActivity::class.java)
               startActivity(intent)
           }*/


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

        /* selected_text.text = getString(
                 R.string.chosen_time_range,
                 startHourString,
                 startMinuteString,
                 endHourString,
                 endMinuteString
         )*/
//        txt_stime.text = startHourString + " " + startMinuteString
//        txt_endtime.text = endHourString + " " + endMinuteString
    }

    private fun String.prependZero(): String {
        return "0".plus(this)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        /*  if(position == 0)
          {
              distSpinner.setError("Please select Country")
              spinner_site.setError("Please Select Site")
          }
          else
          {
              distSpinnerValue = parent!!.getItemAtPosition(position).toString()
              siteSpinnerValue= parent!!.getItemAtPosition(position).toString()
              Log.d("spinnervalue", distSpinnerValue!!);
              Log.d("spinnervalue", siteSpinnerValue!!);


              distSpinner.setErrorEnabled(false)
              distSpinner.setLabel("Country")
              spinner_site.setErrorEnabled(false)
              spinner_site.setLabel("Site")
          }*/
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
    private fun showColorDialog() {
        val ft = supportFragmentManager.beginTransaction()
        val dialogFragment =
            ColorPickerDialog(this)
        dialogFragment.setOnClickListener(object : ColorPickerDialog.OnClickListener {
            override fun onClick(color: Int) {
                contentRL.setBackgroundColor(getColor(color))
            }

        })
        dialogFragment.show(ft, "dialog")
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

                    //logout()

                }

            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

}