package com.maya.myapplication.activityTimer

import android.annotation.SuppressLint
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maya.myapplication.entiti.AppDatabase
import com.maya.myapplication.entiti.TimeItem
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)

class MainViewModel : ViewModel() {

    private val _timeLasted = MutableLiveData<Long>()
    val timeLasted: LiveData<Long>
        get() = _timeLasted

    private val _timeItemListData = MutableLiveData<List<TimeItem>>()
    val timeItemListData: LiveData<List<TimeItem>>
        get() = _timeItemListData


    fun getMonthLastDay(year: Int, month: Int): Long {
        Math.abs(month)
        val a = Calendar.getInstance()
        a[Calendar.YEAR] = year
        a[Calendar.MONTH] = month - 1
        a[Calendar.DATE] = 1
        a.roll(
            Calendar.DATE,
            -1
        )
        return a[Calendar.DATE].toLong()
    }

//--------------------------------------------------------------------------------------------------


    @RequiresApi(Build.VERSION_CODES.O)
    fun startCountDownTimer(timeForTimer: Long) {
        var someTime = currentTimeMillis() + 60000
        val timerMinute = object : CountDownTimer(3600000, 60000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                someTime += 60000
                val timeZone = TimeZone.getTimeZone("Europe/Kiev")
                val offsetDateTime = timeZone.getOffset(currentTimeMillis())
                _timeLasted.value = timeForTimer-currentTimeMillis() - offsetDateTime
            }

            override fun onFinish() {
                onTick(3600000)
            }
        }.start()
    }
//    fun deleteItem(appDatabase: AppDatabase, item: TimeItem) {
//        Thread {
//            appDatabase.TimeListDao().delete(item)
//        }.start()
//    }

//    fun getItemList(appDatabase: AppDatabase) {
//        Thread {
//             val result = appDatabase.TimeListDao().getAll() as ArrayList<TimeItem>
//            _timeItemListData.postValue(result)
//        }.start()
//    }

    fun timeInMillis(numberMillis:String) :Long {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
           val result =simpleDateFormat.parse(numberMillis)?.time
        return result!!
    }

    fun deletingObsoleteDates(appDatabase: AppDatabase){
        Thread {
             val result = appDatabase.TimeListDao().getAll() as ArrayList<TimeItem>
            _timeItemListData.postValue(result)
        }.start()
    }
}


