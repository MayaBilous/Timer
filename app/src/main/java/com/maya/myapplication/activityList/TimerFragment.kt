package com.maya.myapplication.activityList

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.room.Room
import com.maya.myapplication.R
import com.maya.myapplication.databinding.FragmentTimerBinding
import com.maya.myapplication.entiti.AppDatabase
import com.maya.myapplication.entiti.TimeItem
import com.maya.myapplication.showSingleChoiceAlertDialog

class TimerFragment : Fragment(R.layout.fragment_timer) {

    private var appDatabase: AppDatabase? = null
    private val viewModel: TimerViewModel by viewModels()
    private lateinit var binding: FragmentTimerBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appDatabase = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).build()

        binding = FragmentTimerBinding.bind(view)

        viewModel.yearNumbers.observe(this) { number ->
            binding.yearNumbers.text = number.toString()
        }
        viewModel.monthNumbers.observe(this) { number ->
            binding.monthNumbers.text = number.toString()
        }
        viewModel.dayNumbers.observe(this) { number ->
            binding.dayNumbers.text = number.toString()
        }
        viewModel.hourNumbers.observe(this) { number ->
            binding.hourNumbers.text = number.toString()
        }
        viewModel.minuteNumbers.observe(this) { number ->
            binding.minuteNumbers.text = number.toString()
        }


        binding.yearTime.setOnClickListener {
            showSingleChoiceAlertDialog(
                title = "year",
                itemList = viewModel.yearList(),
                context = requireContext(),
                action = { index -> viewModel.chooseYear(index) }
            )
        }
        binding.monthTime.setOnClickListener {
            showSingleChoiceAlertDialog(
                title = "month",
                itemList = viewModel.monthList(),
                context = requireContext(),
                action = { index ->
                    viewModel.chooseMonth(index)
                }
            )
        }
        binding.dayTime.setOnClickListener {
            showSingleChoiceAlertDialog(
                title = "day",
                itemList = viewModel.dayList(),
                context = requireContext(),
                action = { index ->
                    viewModel.chooseDay(index)
                }
            )
        }
        binding.hourTime.setOnClickListener {
            showSingleChoiceAlertDialog(
                title = "hour",
                itemList = viewModel.hourList(),
                context = requireContext(),
                action = { index ->
                    viewModel.chooseHour(index)
                }
            )
        }
        binding.minuteTime.setOnClickListener {
            showSingleChoiceAlertDialog(
                title = "minute",
                itemList = viewModel.minuteList(),
                context = requireContext(),
                action = { index ->
                    viewModel.chooseMinute(index)
                }
            )
        }
        binding.add.setOnClickListener {
            appDatabase?.let {
                viewModel.addItem(it, binding.nameTime.text.toString())
            }
        }

        binding.back.setOnClickListener { parentFragmentManager.popBackStack() }

    }

}
