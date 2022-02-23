package com.maya.myapplication.activityList

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maya.myapplication.entiti.AppDatabase
import com.maya.myapplication.entiti.TimeItem
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters


@RequiresApi(Build.VERSION_CODES.O)
class TimerViewModel : ViewModel() {

    private val _timeList = MutableLiveData<List<TimeItem>>()
    val timeItem: LiveData<List<TimeItem>>
        get() = _timeList

    private val _yearNumbers =
        MutableLiveData(ZonedDateTime.now(ZoneId.of("Europe/Kiev")).year)
    val yearNumbers: LiveData<Int>
        get() = _yearNumbers

    private val _monthNumbers =
        MutableLiveData(ZonedDateTime.now(ZoneId.of("Europe/Kiev")).monthValue)
    val monthNumbers: LiveData<Int>
        get() = _monthNumbers

    private val _dayNumbers =
        MutableLiveData(ZonedDateTime.now(ZoneId.of("Europe/Kiev")).dayOfMonth)
    val dayNumbers: LiveData<Int>
        get() = _dayNumbers

    private val _hourNumbers =
        MutableLiveData(ZonedDateTime.now(ZoneId.of("Europe/Kiev")).hour)
    val hourNumbers: LiveData<Int>
        get() = _hourNumbers

    private val _minuteNumbers =
        MutableLiveData((ZonedDateTime.now(ZoneId.of("Europe/Kiev")).minute + 1))
    val minuteNumbers: LiveData<Int>
        get() = _minuteNumbers



    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseYear(yearIdx: Int) {
        _yearNumbers.value = yearList()[yearIdx]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseMonth(monthIdx: Int) {
        _monthNumbers.value = monthList()[monthIdx]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseDay(dayIdx: Int) {
        _dayNumbers.value = dayList()[dayIdx]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseHour(hourIdx: Int) {
        _hourNumbers.value = hourList()[hourIdx]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseMinute(minuteIdx: Int) {
        _minuteNumbers.value = minuteList()[minuteIdx]
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun yearList(): List<Int> {
        val values: IntRange
        val date: Int = ZonedDateTime.now(ZoneId.of("Europe/Kiev")).year
        values = (date..date + 100)
        return values.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthList(): List<Int> {
        val date: Int
        val values: IntRange
        if (yearNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).year) {
            values = (1..12)
        } else {
            date = ZonedDateTime.now(ZoneId.of("Europe/Kiev")).monthValue
            values = (date..12)
        }
        Log.d("MyLogs", yearNumbers.value.toString())
        return values.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dayList(): List<Int> {
        val values: IntRange
        val date: Int = ZonedDateTime.now(ZoneId.of("Europe/Kiev")).dayOfMonth
        val lastDateOfCurrentMonth = TemporalAdjusters.lastDayOfMonth()
        val lastDay: CharArray =
            lastDateOfCurrentMonth.adjustInto(LocalDate.now()).toString().toCharArray()
        if (monthNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).monthValue ||
            yearNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).year
        ) {
            values = (1..(lastDay.get(lastDay.lastIndex - 1)
                .toString() + lastDay.get(lastDay.lastIndex).toString()).toInt())
        } else {
            values = (date..(lastDay.get(lastDay.lastIndex - 1)
                .toString() + lastDay.get(lastDay.lastIndex).toString()).toInt())
        }
        return values.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun hourList(): List<Int> {
        val date: Int
        val values: IntRange
        if (dayNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).dayOfMonth ||
            monthNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).monthValue ||
            yearNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).year
        ) {
            values = (1..24)
        } else {
            date = ZonedDateTime.now(ZoneId.of("Europe/Kiev")).hour
            values = (date..24)
        }
        return values.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun minuteList(): List<Int> {
        val date: Int
        val values: IntRange
        if (hourNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).hour ||
            dayNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).dayOfMonth ||
            monthNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).monthValue ||
            yearNumbers.value != ZonedDateTime.now(ZoneId.of("Europe/Kiev")).year
        ) {
            values = (1..59)
        } else {
            date = ZonedDateTime.now(ZoneId.of("Europe/Kiev")).minute
            values = (date + 1..59)
        }
        return values.toList()
    }

//    fun getItemList(appDatabase: AppDatabase) {
//        Thread {
////            for (i in 0..appDatabase.TimeListDao().getAll().size){
////                if (timeInMillis(appDatabase.TimeListDao().getAll()[i].toString()) <= System.currentTimeMillis()){
////                    deleteItem(appDatabase, TimeList(appDatabase.TimeListDao().getAll()[i].toString()))
////                }
////            }
//            val result = appDatabase.TimeListDao().getAll()
//            _timeList.postValue(result)
//        }.start()
//    }
    fun addItem(appDatabase: AppDatabase, nameItem:String) {
        Thread {
            appDatabase.TimeListDao().insert(TimeItem(0, nameItem, dayNumbers.value.toString() + "." + monthNumbers.value.toString() + "." + yearNumbers.value.toString() + " " + hourNumbers.value.toString() + ":" + minuteNumbers.value.toString() + ":0", true
            ))

        }.start()
    }

    fun deleteItem(appDatabase: AppDatabase, item:TimeItem ) {
        Thread {
            appDatabase.TimeListDao().delete(item)
        }.start()
    }
}