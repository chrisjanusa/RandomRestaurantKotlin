package com.chrisjanusa.navigation

import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.chrisjanusa.randomizer.RandomizerFragment
import kotlinx.android.synthetic.main.activity_main.*
import mumayank.com.airlocationlibrary.AirLocation
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var airLocation: AirLocation? = null
    private val newFragment = TempFragment()
    private val randomizerFrag = RandomizerFragment()

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setRandomFrag()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                setCityFrag()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchToText("Notifications")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        setRandomFrag()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        airLocation?.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        airLocation?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun switchToText(text: String){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
        newFragment.setText(text)
    }

    fun setRandomFrag() {
        airLocation = AirLocation(this, true, true, object: AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, randomizerFrag)
                transaction.addToBackStack(null)
                transaction.commit()
                location.longitude += Random.nextDouble()
                randomizerFrag.setMap(location)
            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                //TODO: Handle different types of errors
                switchToText(locationFailedEnum.name)
            }

        })
    }
    fun setCityFrag() {
        airLocation = AirLocation(this, true, true, object: AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                switchToText(
                    Geocoder(applicationContext)
                        .getFromLocation(13.72931,99.30367,1)[0]
                    .locality)
            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
                //TODO: Handle different types of errors
                switchToText(locationFailedEnum.name)
            }

        })
    }
}
