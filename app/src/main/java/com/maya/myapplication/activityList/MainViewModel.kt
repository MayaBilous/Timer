package com.maya.myapplication.activityList

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.temporal.TemporalAdjusters


@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel : ViewModel() {
    private val zoneDateTime = ZonedDateTime.now(ZoneId.of("Europe/Kiev"))
    private val _yearNumbers =
        MutableLiveData(ZonedDateTime.now(ZoneId.of("Europe/Kiev")).year.toString())
    val yearNumbers: LiveData<String>
        get() = _yearNumbers

    private val _monthNumbers =
        MutableLiveData(ZonedDateTime.now(ZoneId.of("Europe/Kiev")).monthValue.toString())
    val monthNumbers: LiveData<String>
        get() = _monthNumbers

    private val _dayNumbers =
        MutableLiveData(ZonedDateTime.now(ZoneId.of("Europe/Kiev")).dayOfMonth.toString())
    val dayNumbers: LiveData<String>
        get() = _dayNumbers

    private val _hourNumbers =
        MutableLiveData(ZonedDateTime.now(ZoneId.of("Europe/Kiev")).hour.toString())
    val hourNumbers: LiveData<String>
        get() = _hourNumbers

    private val _minuteNumbers =
        MutableLiveData((ZonedDateTime.now(ZoneId.of("Europe/Kiev")).minute + 1).toString())
    val minuteNumbers: LiveData<String>
        get() = _minuteNumbers

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseYear(yearIdx: Int) {
        _yearNumbers.value = yearList()[yearIdx].toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseMonth(monthIdx: Int) {
        _monthNumbers.value = monthList()[monthIdx].toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseDay(dayIdx: Int) {
        _dayNumbers.value = dayList()[dayIdx].toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseHour(hourIdx: Int) {
        _hourNumbers.value = hourList()[hourIdx].toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun chooseMinute(minuteIdx: Int) {
        _minuteNumbers.value = minuteList()[minuteIdx].toString()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun yearList(): List<Int> {
        val values: IntRange
        val date: Int = zoneDateTime.year
        values = (date..date + 100)
        return values.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthList(): List<Int> {
        val date: Int
        val values: IntRange
        if (yearNumbers.value != zoneDateTime.year.toString()) {
            values = (1..12)
        } else {
            date = zoneDateTime.monthValue
            values = (date..12)
        }
        Log.d("MyLogs", yearNumbers.value.toString())
        return values.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dayList(): List<Int> {
        val values: IntRange
        val date: Int = zoneDateTime.dayOfMonth
        val lastDateOfCurrentMonth = TemporalAdjusters.lastDayOfMonth()
        val lastDay: CharArray = lastDateOfCurrentMonth.adjustInto(LocalDate.now()).toString().toCharArray()
        if (monthNumbers.value != zoneDateTime.monthValue.toString() ||
            yearNumbers.value !=zoneDateTime.year.toString()
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
        if (dayNumbers.value != zoneDateTime.dayOfMonth.toString() ||
            monthNumbers.value != zoneDateTime.monthValue.toString() ||
            yearNumbers.value != zoneDateTime.year.toString()
        ) {
            values = (1..24)
        } else {
            date = zoneDateTime.hour
            values = (date..24)
        }
        return values.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun minuteList(): List<Int> {
        val date: Int
        val values: IntRange
        if (hourNumbers.value != zoneDateTime.hour.toString() ||
            dayNumbers.value != zoneDateTime.dayOfMonth.toString() ||
            monthNumbers.value != zoneDateTime.monthValue.toString() ||
            yearNumbers.value != zoneDateTime.year.toString()
        ) {
            values = (1..59)
        } else {
            date = zoneDateTime.minute
            values = (date + 1..59)
        }
        return values.toList()
    }

    fun startButton(): Boolean {
        return hourNumbers.value!! > zoneDateTime.hour.toString() ||
                dayNumbers.value!! > zoneDateTime.dayOfMonth.toString() ||
                monthNumbers.value!! > zoneDateTime.monthValue.toString() ||
                yearNumbers.value!! > zoneDateTime.year.toString() ||
                minuteNumbers.value!! > zoneDateTime.minute.toString()

    }
}