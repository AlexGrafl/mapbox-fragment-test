package com.example.mapboxtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import com.mapbox.mapboxsdk.maps.SupportMapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(this, BuildConfig.MAPBOX_ACCESS_TOKEN)
        setContentView(R.layout.activity_main)
        mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        //Adding the fragment manually does not alter the behavior:

//        mapFragment = SupportMapFragment.newInstance()
//        supportFragmentManager.beginTransaction()
//            .apply {
//                replace(R.id.container, mapFragment, "com.mapbox.map")
//                addToBackStack("map")
//                commit()
//            }

        mapFragment.getMapAsync { init(it) }

        button.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .apply {
                    replace(R.id.container, TestFragment())
                    addToBackStack("map2")
                }.commit()
        }
    }

    private fun init(mapboxMap: MapboxMap) {
        mapboxMap.setStyle(Style.SATELLITE) {
            Log.i("MAPBOX", "padding: [${mapboxMap.padding.fold("") { str, i -> "$str$i " }}]")
            mapboxMap.setPadding(300, 300, 300, 300)
            Log.i("MAPBOX", "padding: [${mapboxMap.padding.fold("") { str, i -> "$str$i " }}]")
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
