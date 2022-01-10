package com.maya.myapplication.activityTimer

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maya.myapplication.databinding.FragmentTimerBinding
import com.maya.myapplication.domain.TimeForTimer

class TimerFragment : Fragment() {

    companion object {
        const val TIME_FOR_TIMER = "time_for_timer"
    }

    private val viewModel: TimerViewModel by viewModels()
    private lateinit var binding: FragmentTimerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.yearTimer.observe(this) { number ->
            binding.yearTimer.text = number
        }
        viewModel.monthTimer.observe(this) { number ->
            binding.monthTimer.text = number
        }
        viewModel.dayTimer.observe(this) { number ->
            binding.dayTimer.text = number
        }
        viewModel.hourTimer.observe(this) { number ->
            binding.hourTimer.text = number
        }
        viewModel.minuteTimer.observe(this) { number ->
            binding.minuteTimer.text = number
        }
        arguments?.let {
            it.getParcelable<TimeForTimer>(TIME_FOR_TIMER)?.let { timeForTimer ->
                viewModel.startCountDownTimeMinuter(timeForTimer)
                viewModel.startCountDownTimeHour(timeForTimer)
                viewModel.startCountDownTimeDay(timeForTimer)
                viewModel.startCountDownTimeMonth(timeForTimer)
                viewModel.startCountDownTimeYear(timeForTimer)
            }
        }


        binding.backButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}