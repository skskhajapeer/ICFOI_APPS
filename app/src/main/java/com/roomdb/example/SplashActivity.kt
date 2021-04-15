package com.roomdb.example

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.roomdb.example.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_latlong.*

class SplashActivity :AppCompatActivity() {

    val RequestPermissionCode = 1
    var mLocation: Location? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val preferenceHelper: IPreferenceHelper by lazy { PreferenceManager(applicationContext) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
       // 3000 is the delayed time in milliseconds.

    }

    fun getLastLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermission()
        } else {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    mLocation = location
                    if (location != null) {
                       // latitude.text = location.latitude.toString()
                        //longitude.text = location.longitude.toString()
                        preferenceHelper.setUserId(location.latitude.toString())
                        preferenceHelper.setStartTimer(location.longitude.toString())
//                        time.text = android.text.format.DateFormat.getTimeFormat(applicationContext).format(location.time)
//                        date.text = android.text.format.DateFormat.getDateFormat(getApplicationContext()).format(location.time)
                    }
                }
        }

        Handler().postDelayed({
            if (AppPreferences.isLogin) {
                val intent = Intent(this, SelectionActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
               /* val intent = Intent(this, SelectionActivity::class.java)
                startActivity(intent)
                finish()*/

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 7000)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            RequestPermissionCode
        )
    }
}