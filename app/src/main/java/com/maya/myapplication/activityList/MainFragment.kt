package com.maya.myapplication.activityList

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.maya.myapplication.R
import com.maya.myapplication.activityTimer.TimerFragment
import com.maya.myapplication.activityTimer.TimerFragment.Companion.TIME_FOR_TIMER
import com.maya.myapplication.databinding.FragmentMainBinding
import com.maya.myapplication.domain.TimeForTimer
import com.maya.myapplication.showSingleChoiceAlertDialog

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)

        viewModel.yearNumbers.observe(this) { number ->
            binding.yearNumbers.text = number
        }
        viewModel.monthNumbers.observe(this) { number ->
            binding.monthNumbers.text = number
        }
        viewModel.dayNumbers.observe(this) { number ->
            binding.dayNumbers.text = number
        }
        viewModel.hourNumbers.observe(this) { number ->
            binding.hourNumbers.text = number
        }
        viewModel.minuteNumbers.observe(this) { number ->
            binding.minuteNumbers.text = number
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
        binding.end.setOnClickListener { requireActivity().finish() }

        binding.start.setOnClickListener {

            if (viewModel.startButton()) {

                val someObject = TimeForTimer(
                    binding.yearNumbers.text as String,
                    binding.monthNumbers.text as String,
                    binding.dayNumbers.text as String,
                    binding.hourNumbers.text as String,
                    binding.minuteNumbers.text as String
                )
                val bundle = Bundle()
                bundle.putParcelable(TIME_FOR_TIMER, someObject)
                val timerFragment = TimerFragment()
                timerFragment.arguments = bundle

                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_layout, timerFragment, null)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}