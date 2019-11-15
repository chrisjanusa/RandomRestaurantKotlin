package com.chrisjanusa.findmefood

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.chrisjanusa.randomizer.RandomizerFragment
import com.chrisjanusa.tempfrag.TempFragment
import kotlinx.android.synthetic.main.activity_main.*
import mumayank.com.airlocationlibrary.AirLocation


class MainActivity : AppCompatActivity() {

    private var airLocation: AirLocation? = null
    private val randomizerFrag = RandomizerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        nav_view.setupWithNavController(Navigation.findNavController(this, R.id.my_nav_host_fragment))
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

//    fun switchToText(text: String){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.fragment_container, newFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//        newFragment.setText(text)
//    }
//
    fun setRandomFrag() {
        airLocation = AirLocation(this, true, true, object: AirLocation.Callbacks {
            override fun onSuccess(location: Location) {
                randomizerFrag.setMap(location)
            }

            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {

            }

        })
    }
//    fun setCityFrag() {
//        airLocation = AirLocation(this, true, true, object: AirLocation.Callbacks {
//            override fun onSuccess(location: Location) {
//                switchToText(
//                    Geocoder(applicationContext)
//                        .getFromLocation(13.72931,99.30367,1)[0]
//                    .locality)
//            }
//
//            override fun onFailed(locationFailedEnum: AirLocation.LocationFailedEnum) {
//                //TODO: Handle different types of errors
//                switchToText(locationFailedEnum.name)
//            }
//
//        })
//    }
}
