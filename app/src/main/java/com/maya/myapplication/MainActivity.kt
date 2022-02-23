package com.maya.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maya.myapplication.activityTimer.MainFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_layout, MainFragment(), null)
                .commit()
        }
    }
}

