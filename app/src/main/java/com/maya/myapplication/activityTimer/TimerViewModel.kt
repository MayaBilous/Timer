package com.maya.myapplication.activityTimer

import android.annotation.SuppressLint
import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maya.myapplication.domain.TimeForTimer
import java.time.Year
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)

class TimerViewModel : ViewModel() {
    val zoneDateTime = ZonedDateTime.now(ZoneId.of("Europe/Kiev"))

    private val _yearTimer = MutableLiveData<String>()
    val yearTimer: LiveData<String>
        get() = _yearTimer

    private val _monthTimer = MutableLiveData<String>()
    val monthTimer: LiveData<String>
        get() = _monthTimer

    private val _dayTimer = MutableLiveData<String>()
    val dayTimer: LiveData<String>
        get() = _dayTimer

    private val _hourTimer = MutableLiveData<String>()
    val hourTimer: LiveData<String>
        get() = _hourTimer

    private val _minuteTimer = MutableLiveData<String>()
    val minuteTimer: LiveData<String>
        get() = _minuteTimer

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
    fun startCountDownTimeYear(timeForTimer: TimeForTimer) {
        val dayOfYear: Long
        if (Year.isLeap(zoneDateTime.year.toLong())) {
            dayOfYear = 31622400000
        } else {
            dayOfYear = 31536000000
        }
        val zoneDY: Int = zoneDateTime.year
        var year: Int = (timeForTimer.year.toInt() - zoneDateTime.year)+1
        val timerYear = object : CountDownTimer(year * dayOfYear, dayOfYear) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (zoneDY == zoneDateTime.year) {
                    _yearTimer.value = "y-" + (millisUntilFinished / dayOfYear)
                } else {
                    startCountDownTimeYear(timeForTimer)
                }
            }

            override fun onFinish() {
                onFinish()
            }

        }.start()
    }

//--------------------------------------------------------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.O)
    fun startCountDownTimeMonth(timeForTimer: TimeForTimer) {
        val dayOfYear: Long
        if (zoneDateTime.dayOfYear == 365) {
            dayOfYear = 31536000000
        } else {
            dayOfYear = 31622400000
        }
        val timerMonth = object : CountDownTimer(
            dayOfYear,
            getMonthLastDay(
                timeForTimer.year.toInt() - (timeForTimer.year.toInt() - zoneDateTime.year),
                timeForTimer.month.toInt() - (timeForTimer.month.toInt() - zoneDateTime.monthValue)
            ) * 86400000
        ) {


            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (timeForTimer.year == zoneDateTime.year.toString()) {
                    if ((timeForTimer.month.toInt() - zoneDateTime.monthValue) == 0
                    ) {
                        _monthTimer.value =
                            "mon-" + 0
                    } else {
                        _monthTimer.value =
                            "mon-" + (((timeForTimer.month.toInt() - zoneDateTime.month.toString()
                                .toInt()).toLong() - 1) * 86400000 / getMonthLastDay(
                                timeForTimer.year.toInt() - (timeForTimer.year.toInt() - zoneDateTime.year),
                                timeForTimer.month.toInt() - (timeForTimer.month.toInt() - zoneDateTime.monthValue)
                            ) * 86400000)
                    }
                } else {
                    _monthTimer.value = "mon-" + (millisUntilFinished / getMonthLastDay(
                        timeForTimer.year.toInt() - (timeForTimer.year.toInt() - zoneDateTime.year),
                        timeForTimer.month.toInt() - (timeForTimer.month.toInt() - zoneDateTime.monthValue)
                    ) * 86400000)
                }
            }

            override fun onFinish() {
                if (yearTimer.value != "0" &&
                    yearTimer.value != "1"
                ) {
                    onTick(dayOfYear)
                } else if (yearTimer.value == "1"
                ) {
                    onTick(
                        ((timeForTimer.month.toInt() - zoneDateTime.month.toString()
                            .toInt()).toLong() - 1) * 86400000 * 30
                    )
                } else {
                    onFinish()
                }
            }
        }.start()
    }

//--------------------------------------------------------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.O)
    fun startCountDownTimeDay(timeForTimer: TimeForTimer) {
        val timerDay = object : CountDownTimer(
            getMonthLastDay(
                timeForTimer.year.toInt() - (timeForTimer.year.toInt() - zoneDateTime.year),
                timeForTimer.month.toInt() - (timeForTimer.month.toInt() - zoneDateTime.monthValue)
            ) * 86400000, 86400000
        ) {


            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (timeForTimer.year == zoneDateTime.year.toString() &&
                    timeForTimer.month == zoneDateTime.monthValue.toString()
                ) {
                    if ((timeForTimer.day.toLong() - zoneDateTime.dayOfMonth).toString() == "0") {
                        _dayTimer.value =
                            "d-" + 0
                    } else {
                        _dayTimer.value =
                            "d-" + ((timeForTimer.day.toLong() - zoneDateTime.dayOfMonth - 1) * 86400000 / 86400000)
                    }
                } else {
                    _dayTimer.value = "d-" + (millisUntilFinished / 86400000)
                }
            }

            override fun onFinish() {
                if (yearTimer.value != "0" ||
                    monthTimer.value != "0" &&
                    monthTimer.value != "1"
                ) {
                    onTick(
                        getMonthLastDay(
                            timeForTimer.year.toInt() - (timeForTimer.year.toInt() - zoneDateTime.year),
                            timeForTimer.month.toInt() - (timeForTimer.month.toInt() - zoneDateTime.monthValue)
                        ) * 86400000
                    )
                } else if (yearTimer.value == "0" &&
                    monthTimer.value == "1"
                ) {
                    if ((timeForTimer.day.toLong() - zoneDateTime.dayOfMonth).toString() == "0") {
                        onTick(0)
                    } else {
                        onTick((timeForTimer.day.toLong() - zoneDateTime.dayOfMonth - 1) * 86400000)
                    }
                } else {
                    onFinish()
                }
            }
        }.start()
    }

//--------------------------------------------------------------------------------------------------

    @RequiresApi(Build.VERSION_CODES.O)
    fun startCountDownTimeHour(timeForTimer: TimeForTimer) {
        val timerHour = object : CountDownTimer(86400000, 3600000) {


            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (timeForTimer.year == zoneDateTime.year.toString() &&
                    timeForTimer.month == zoneDateTime.monthValue.toString() &&
                    timeForTimer.day == zoneDateTime.dayOfMonth.toString()
                ) {
                    if ((timeForTimer.hour.toLong() - zoneDateTime.hour).toString() == "0") {
                        _hourTimer.value =
                            "h-" + 0
                    } else {
                        _hourTimer.value =
                            "h-" + ((timeForTimer.hour.toLong() - zoneDateTime.hour) * 3600000 / 3600000)
                    }
                } else {
                    _hourTimer.value = "h-" + (millisUntilFinished / 3600000)
                }
            }

            override fun onFinish() {
                if (yearTimer.value != "0" ||
                    monthTimer.value != "0" ||
                    dayTimer.value != "0" &&
                    dayTimer.value != "1"
                ) {
                    onTick(86400000)
                } else if (yearTimer.value == "0" &&
                    monthTimer.value == "0" &&
                    dayTimer.value == "1"
                ) {
                    if ((timeForTimer.hour.toLong() - zoneDateTime.hour).toString() == "0") {
                        onTick(0)
                    } else {
                        onTick((timeForTimer.hour.toLong() - zoneDateTime.hour - 1) * 3600000)
                    }
                } else {
                    onFinish()
                }
            }
        }.start()
    }

//--------------------------------------------------------------------------------------------------

@RequiresApi(Build.VERSION_CODES.O)
fun startCountDownTimeMinuter(timeForTimer: TimeForTimer) {
    val timerMinute = object : CountDownTimer(3600000, 60000) {
        @SuppressLint("SetTextI18n")
        override fun onTick(millisUntilFinished: Long) {
            if (timeForTimer.year == zoneDateTime.year.toString() &&
                timeForTimer.month == zoneDateTime.monthValue.toString() &&
                timeForTimer.day == zoneDateTime.dayOfMonth.toString() &&
                timeForTimer.hour == zoneDateTime.hour.toString()
            ) {
                _minuteTimer.value =
                    "min-" + ((timeForTimer.minute.toLong() - zoneDateTime.minute) * 60000 / 60000)
            } else {
                _minuteTimer.value = "min-" + (millisUntilFinished / 60000)
            }
        }

        override fun onFinish() {
            if (yearTimer.value != "0" ||
                monthTimer.value != "0" ||
                dayTimer.value != "0" ||
                hourTimer.value != "0" &&
                hourTimer.value != "1"
            ) {
                onTick(3600000)
            } else if (TimerViewModel().yearTimer.value == "0" &&
                monthTimer.value == "0" &&
                dayTimer.value == "0" &&
                hourTimer.value == "1"
            ) {
                onTick((timeForTimer.minute.toLong() - zoneDateTime.minute) * 60000)
            } else {
                onFinish()
            }
        }
    }.start()
}
}