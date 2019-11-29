package com.chrisjanusa.findmefood

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.chrisjanusa.randomizer.filter_fragments.OverlayFragmentManager
import com.chrisjanusa.randomizer.filter_fragments.ShadeFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : OverlayFragmentManager, AppCompatActivity() {
    private val OVERLAY_TAG = "OVERLAY"

    override fun onFilterSelected(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.slidein_bottom, R.anim.slideout_bottom,
                R.anim.slidein_bottom, R.anim.slideout_bottom)
            .replace(R.id.overlay_fragment, fragment, OVERLAY_TAG)
            .addToBackStack(OVERLAY_TAG)
            .commit()
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fadein, R.anim.fadeout,
                R.anim.fadein, R.anim.fadeout)
            .replace(R.id.shade_fragment, ShadeFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onFilterClosed() {
        supportFragmentManager.popBackStack(OVERLAY_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        nav_view.setupWithNavController(Navigation.findNavController(this, R.id.my_nav_host_fragment))
    }
}
