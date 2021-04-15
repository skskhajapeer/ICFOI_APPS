package com.roomdb.example

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.roomdb.example.utils.AppPreferences
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset

class LoginActivity : AppCompatActivity() {

    var personName: ArrayList<String> = ArrayList()
    var emailId: ArrayList<String> = ArrayList()
    var mobileNumbers: ArrayList<String> = ArrayList()
    private var emailValue:String?=null
    private var  passwordValue:String?=null

    lateinit var etEmail: EditText
    lateinit var  etPassword: EditText


    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }
    private val IS_LOGIN = Pair("is_login", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        etEmail = findViewById<EditText>(R.id.et_email)
        etPassword = findViewById<EditText>(R.id.et_password)
        val btn_submit = findViewById<Button>(R.id.bt_login)

        try {
            val obj = JSONObject(loadJSONFromAsset())
            val userArray = obj.getJSONArray("users")

           for (i in 0 until userArray.length()) {
                val userDetail = userArray.getJSONObject(i)
                personName.add(userDetail.getString("name"))
              passwordValue=userDetail.getString("name")
                emailId.add(userDetail.getString("email"))
//                emailValue=userDetail.getString("email")
                Log.d("emailvalue",emailValue.toString());
                val contact = userDetail.getJSONObject("contact")
                mobileNumbers.add(contact.getString("mobile"))


            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

       /* btn_submit.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
              //  emailValue = et_user_name.text.toString()

                if (validateInput()) {
                    val email = etEmail!!.text.toString()
                    //  email==emailValue;
                    val password = etPassword!!.text.toString()

                    val username: Boolean = personName.contains(password)
                    val passwordValues: Boolean = emailId.contains(email)

                    if (username && passwordValues) {
                        val intent = Intent(applicationContext, ResultActivity::class.java)
                        startActivity(intent)
                    }
                }
                else
                    println("Not Equal")

            }
        }*/
    }

    fun loadJSONFromAsset(): String {
        val json: String?
        try {
            val inputStream = assets.open("logindata.json")
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

    // Hook Click Event
    fun performSignUp(v: View) {
        if (validateInput()) {

            val email = etEmail!!.text.toString()
            //  email==emailValue;
            val password = etPassword!!.text.toString()




            if (AppPreferences.isLogin) {
                AppPreferences.isLogin = false
                AppPreferences.username = ""
                AppPreferences.password = ""
            }
            else {

                val username: Boolean = personName.contains(password)
                val passwordValues: Boolean = emailId.contains(email)
              preferenceHelper.setUserName(email)

//                val username = etEmail.text.toString()
//                val password = etPassword.text.toString()
                if (username && passwordValues) {
                    AppPreferences.isLogin = true
                    AppPreferences.username = email
                    AppPreferences.password = password
                    val intent = Intent(applicationContext, SelectionActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
                }
            }
           /* if (username && passwordValues) {
                val intent = Intent(applicationContext, SelectionActivity::class.java)
                startActivity(intent)
            }*/
        }
        else
            println("Not Equal")


    }

    // Checking if the input in form is valid
    fun validateInput(): Boolean {
        if (etEmail.text.toString() == "") {
           etEmail.error = "Please Enter Email"
            //Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show()
            return false
        }
        if (etPassword.text.toString() == "") {
            etPassword.error = "Please Enter Password"
            //Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show()

            return false
        }


        return true
    }
}
