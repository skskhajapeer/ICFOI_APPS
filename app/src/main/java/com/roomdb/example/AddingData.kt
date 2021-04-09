package com.roomdb.example

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.dev.materialspinner.MaterialSpinner
import kotlinx.android.synthetic.main.activity_add_data.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


class AddingData : AppCompatActivity() {
    var slNumbers: ArrayList<String> = ArrayList()
    private lateinit var slNoSpinner: MaterialSpinner
    private var  passwordValue:String?=null
    private var distSpinnerValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)
        slNoSpinner = findViewById(R.id.spinner_slno);




        try {
            val obj = JSONObject(loadJSONFromAsset())
            val userArray = obj.getJSONArray("Sites")




            for (i in 0 until userArray.length()) {
                val userDetail = userArray.getJSONObject(i)

                val contact = userDetail.getString("Sl.No")
                slNumbers.add(contact.toString())

                val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, slNumbers)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                slNoSpinner.setAdapter(aa)



                // slNumbers.add(userArray.getString(i).toString())

               /* val contact = userDetail.getString("Sl.No")
                slNumbers.add(contact.toString())
               // mobileNumbers.add(contact.getString("mobile"))
                Log.d("slNo", slNumbers.toString());

                val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, slNumbers)
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                slNoSpinner.setAdapter(aa)

                passwordValue=slNumbers.get(0);
                Log.d("district", passwordValue.toString());*/



            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        slNoSpinner.getSpinner().onItemSelectedListener =
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
                    Log.d("spinpos",distSpinnerValue.toString())
                    slNumbers.filter { it==distSpinnerValue }



                }

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