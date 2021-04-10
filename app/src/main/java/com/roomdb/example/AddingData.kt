package com.roomdb.example

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.roomdb.example.databinding.ActivityAddDataBinding
import com.roomdb.example.model.AddDataModel
import com.roomdb.example.model.Sites
import me.adawoud.bottomsheettimepicker.BottomSheetTimeRangePicker
import me.adawoud.bottomsheettimepicker.OnTimeRangeSelectedListener
import java.io.IOException
import java.nio.charset.Charset


class AddingData : AppCompatActivity(), OnTimeRangeSelectedListener {
    var slNumbers: ArrayList<String> = ArrayList()

    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }

    private val tagBottomSheetTimeRangePicker = "tagBottomSheetTimeRangePicker"
    private lateinit var bindinggView : ActivityAddDataBinding
    private var distSpinnerValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindinggView = ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(bindinggView.root)

        val gson = Gson()
        val dataModel = gson.fromJson(loadJSONFromAsset(), AddDataModel::class.java)


            for (i in 0 until dataModel.sites.size) {
                val userDetail = dataModel.sites.get(i)
                slNumbers.add(userDetail.slNo.toString())

                val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, slNumbers)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                bindinggView.spinnerSlno.setAdapter(aa)
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
                    slNumbers.filter { it==distSpinnerValue }
                    setDataBasedOnSelection( dataModel.sites.get(position))
                }

            }
        setUIData()
    }

    fun setDataBasedOnSelection(siteInfo: Sites) {
        bindinggView.txtDist.text = siteInfo.district
        bindinggView.txtDivision.text = siteInfo.division
        bindinggView.txtRfBlock.text = siteInfo.rfBlock
        bindinggView.txtArea.text = ""+siteInfo.areaHa
        bindinggView.txtBeatName.text = siteInfo.beatName
        bindinggView.txtRange.text = siteInfo.range

        if(siteInfo.compartment.contains(",")){
            bindinggView.txtCompartment.visibility= View.GONE
            bindinggView.compartmentSpinner.visibility= View.VISIBLE

            val compartments = ArrayAdapter(this, android.R.layout.simple_spinner_item, siteInfo.compartment.split(","))
            compartments.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bindinggView.compartmentSpinner.setAdapter(compartments)

        }else{
            bindinggView.txtCompartment.text = siteInfo.compartment
            bindinggView.compartmentSpinner.visibility = View.GONE
        }

    }

    fun setUIData(){
        bindinggView.btnSelectTime.setOnClickListener( View.OnClickListener {
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

}