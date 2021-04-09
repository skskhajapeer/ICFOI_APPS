package com.roomdb.example

import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.dev.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.startup.*
import me.adawoud.bottomsheettimepicker.BottomSheetTimeRangePicker
import me.adawoud.bottomsheettimepicker.OnTimeRangeSelectedListener


class SaveData : AppCompatActivity(), OnTimeRangeSelectedListener {


    private lateinit var distSpinner: MaterialSpinner
    private lateinit var spinner_site: MaterialSpinner
    val list_of_items = arrayOf("Select Country", "USA", "Japan", "India")
    val list_of_sites = arrayOf("Select Site", "Siteone", "Sitetwo", "Sitethree")
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }
    private var distSpinnerValue: String? = null

    private var siteSpinnerValue: String? = null
    private val tagBottomSheetTimeRangePicker = "tagBottomSheetTimeRangePicker"

    var startTimer: String? = null
    var endTimer: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.savedata)

        val latvalues = findViewById<EditText>(R.id.et_lat)
        val longitudevalues = findViewById<EditText>(R.id.et_long)
        val observervalue = findViewById<EditText>(R.id.et_observer)

        val selectTime = findViewById<Button>(R.id.btn_select)


        distSpinner = findViewById(R.id.dist_spinner);
        spinner_site = findViewById(R.id.spinner_site);

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        distSpinner.setAdapter(aa)

        Log.d("sharedprefval", preferenceHelper.getApiKey());
        Log.d("sharedprefval", preferenceHelper.getUserId());

//        distSpinner.getSpinner().setEnabled(false)
//        distSpinner.setClickable(false);

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


        val districtSpinValue = preferenceHelper.getUserId();

        val siteSpinValue = preferenceHelper.getApiKey();

        /*  val startTimeValue=preferenceHelper.getStartTimer();
          val endTimeValue=preferenceHelper.getEndTimer();

          if(startTimeValue===null){
              txt_stime.text=startTimeValue
          }
          else{
              txt_stime.text=""

          }
          if(endTimeValue===null){
              txt_endtime.text=endTimeValue
          }
          else{
              txt_endtime.text=""

          }*/
        startTimer = preferenceHelper.getStartTimer();
        endTimer = preferenceHelper.getEndTimer();

        if (isNullOrEmpty(startTimer))
            txt_stime.text = ""
        else

            txt_stime.text = startTimer
        if (isNullOrEmpty(endTimer))
            txt_endtime.text = ""
        else

            txt_endtime.text = endTimer

        if (distSpinnerValue !== null) {


            distSpinner.getSpinner().setEnabled(false)
            distSpinner.setClickable(false);
            distSpinner.setAdapter(aa)
            distSpinner.getSpinner().setSelection(aa.getPosition(districtSpinValue))

        } else {
            /* distSpinner.getSpinner().setEnabled(false)
             distSpinner.setClickable(false);
             distSpinner.setAdapter(aa)
             distSpinner.getSpinner().setSelection(aa.getPosition(districtSpinValue))
 */
            distSpinner.getSpinner().setEnabled(true)
            distSpinner.setClickable(true);
            //distSpinner.setAdapter(aa)

        }



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
                        val provider: String = distSpinner.getSpinner().getSelectedItem().toString()
                        preferenceHelper.setUserId(provider)
                        Log.d("spinnervalue", distSpinnerValue!!);
                        distSpinner.setErrorEnabled(false)
                        distSpinner.setLabel("Country")

                    }

                }

            }
        val bb = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_sites)
        bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_site.setAdapter(bb)


        if (siteSpinnerValue !== null) {
            spinner_site.getSpinner().setEnabled(true)
            spinner_site.setClickable(true);
            spinner_site.setAdapter(aa)
        } else {
            spinner_site.getSpinner().setEnabled(false)
            spinner_site.setClickable(false);
            spinner_site.setAdapter(aa)
            spinner_site.getSpinner().setSelection(bb.getPosition(siteSpinValue))

        }

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

        btn_save.setOnClickListener {

            preferenceHelper.setApiKey(distSpinnerValue.toString())
            preferenceHelper.setUserId(siteSpinnerValue.toString())
            preferenceHelper.setStartTimer(txt_stime.text.toString())
            preferenceHelper.setEndTimer(txt_endtime.text.toString())

        }

        Log.d("sharedprefval", preferenceHelper.getApiKey());
        Log.d("sharedprefval", preferenceHelper.getUserId());


    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceHelper.clearPrefs()
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
        txt_stime.text = startHourString + " " + startMinuteString
        txt_endtime.text = endHourString + " " + endMinuteString
    }

    private fun String.prependZero(): String {
        return "0".plus(this)
    }

    fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }
}