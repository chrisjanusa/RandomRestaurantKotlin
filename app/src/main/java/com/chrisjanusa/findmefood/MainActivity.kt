package com.chrisjanusa.findmefood

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.chrisjanusa.randomizer.filter_base.OverlayFragmentManager
import com.chrisjanusa.randomizer.filter_base.ShadeFragment


class MainActivity : OverlayFragmentManager, AppCompatActivity() {
    private val overlayTag = "OVERLAY"

    override fun onFilterSelected(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slidein_bottom, R.anim.slideout_bottom,
                R.anim.slidein_bottom, R.anim.slideout_bottom
            )
            .replace(R.id.overlay_fragment, fragment, overlayTag)
            .setCustomAnimations(
                R.anim.fadein, R.anim.fadeout,
                R.anim.fadein, R.anim.fadeout
            )
            .replace(R.id.shade_fragment, ShadeFragment())
            .addToBackStack(overlayTag)
            .commit()
    }

    override fun onFilterClosed() {
        supportFragmentManager.popBackStackImmediate(
            overlayTag,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.error_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.show()
//        val navController = Navigation.findNavController(this, R.id.my_nav_host_fragment)
//        // TODO: Synthetics
////        nav_view.setupWithNavController(navController)
////        nav_view.setOnNavigationItemReselectedListener {
//            // Do nothing to ignore the reselection
////        }
    }
}
