package com.maya.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.maya.myapplication.activityList.MainFragment
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.main_layout, MainFragment(), null)
                .commit()
        }



        val someTime = System.currentTimeMillis()
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        Log.d("MyLogs", "time = ${simpleDateFormat.format(someTime)}")
    }
}

