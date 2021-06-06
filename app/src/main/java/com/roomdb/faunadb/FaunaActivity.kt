package com.roomdb.faunadb

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.room.mvvm.viewmodel.LoginViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.roomdb.example.IPreferenceHelper
import com.roomdb.example.LoginActivity
import com.roomdb.example.PreferenceManager
import com.roomdb.example.R
import com.roomdb.example.databinding.ActivityFaunaAddDataBinding
import com.roomdb.example.model.AddDataModel
import com.roomdb.example.model.Sites
import com.roomdb.example.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_add_data.*
import kotlinx.android.synthetic.main.activity_fauna_add_data.*
import kotlinx.android.synthetic.main.activity_fauna_add_data.contentRL
import kotlinx.android.synthetic.main.activity_fauna_add_data.nav
import kotlinx.android.synthetic.main.activity_fauna_add_data.spinner_slno
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_startup.*
import kotlinx.android.synthetic.main.nav_header.view.*
import java.io.IOException
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FaunaActivity : AppCompatActivity() {

    lateinit var loginViewModel: LoginViewModel

    lateinit var context: Context

    lateinit var strUsername: String
    lateinit var strPassword: String



    var slNumbers: ArrayList<String> = ArrayList()

    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }

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
        setContentView(R.layout.activity_fauna_add_data)

        context = this@FaunaActivity

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        var district = findViewById<TextView>(R.id.txt_dist)
        val division= findViewById<TextView>(R.id.txt_division)
        val beat= findViewById<TextView>(R.id.txt_beat_name)
        val range= findViewById<TextView>(R.id.txt_range)

        districtValue=district.text.toString()
        divisionValue=division.text.toString()
        beatNameValue=beat.text.toString()
        rangeValue=range.text.toString()

        val faunaTypeValue = intent.getStringExtra("textValue")
        Log.d("textValue", "${faunaTypeValue}")

        val current = LocalDateTime.now()

        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val currentDate = current.format(formatter)

        Log.d("date",currentDate)
        dateValue=currentDate

        txt_date.setText(dateValue)

        if (faunaTypeValue == "Birds") {
            val treeNames =
                arrayOf<String>(*resources.getStringArray(R.array.birds_new_names))

            val botanicalTreeAdapter = ArrayAdapter(
                applicationContext,
                android.R.layout.simple_spinner_item,
                treeNames
            )
            botanicalTreeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            txt_family.setAdapter(botanicalTreeAdapter)

        }

        val mDrawerToggle = ActionBarDrawerToggle(
            this,
            contentRL,
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
            spinner_slno.setAdapter(aa)
            //           }

        }

        spinner_slno.onItemSelectedListener =
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


        contentRL.setDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()

        navigationItemSelect()

        AppPreferences.isLogin = true


        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        btnsave.setOnClickListener {

            strUsername = txt_gps.text.toString().trim()
            strPassword = txt_siteName.text.toString().trim()

            if (strPassword.isEmpty()) {
                txt_gps.error = "Please enter the username"
            }
            else if (strPassword.isEmpty()) {
                txt_siteName.error = "Please enter the username"
            }
            else {
                loginViewModel.insertData(context, strUsername, strPassword)
               // lblInsertResponse.text = "Inserted Successfully"
            }

            Toast.makeText(context,"Data Success",Toast.LENGTH_LONG  ).show()

            val intent = Intent(this, FaunaResultActivity::class.java)
            startActivity(intent)
        }

/*
        btnReadLogin.setOnClickListener {

            strUsername = txtUsername.text.toString().trim()

            loginViewModel.getLoginDetails(context, strUsername)!!.observe(this, Observer {

                if (it == null) {
                    lblReadResponse.text = "Data Not Found"
                    lblUseraname.text = "- - -"
                    lblPassword.text = "- - -"
                }
                else {
                    lblUseraname.text = it.Username
                    lblPassword.text = it.Password

                    lblReadResponse.text = "Data Found Successfully"
                }
            })
        }
*/
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
            contentRL.closeDrawer(GravityCompat.START)
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
        districtValue = siteInfo.district
        divisionValue = siteInfo.division
        beatNameValue= siteInfo.beatName
        rangeValue = siteInfo.range







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