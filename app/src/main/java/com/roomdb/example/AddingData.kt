package com.roomdb.example

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.roomdb.example.databinding.ActivityAddDataBinding
import com.roomdb.example.model.AddDataModel
import com.roomdb.example.model.Sites
import java.io.IOException
import java.nio.charset.Charset


class AddingData : AppCompatActivity() {
    var slNumbers: ArrayList<String> = ArrayList()
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

}