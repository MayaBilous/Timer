package com.maya.myapplication.activityTimer

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.maya.myapplication.R
import com.maya.myapplication.activityList.TimerFragment
import com.maya.myapplication.databinding.FragmentMainBinding
import com.maya.myapplication.entiti.AppDatabase
import com.maya.myapplication.entiti.TimeItem
import com.maya.myapplication.entiti.TimeListDao
import com.maya.myapplication.recyclerView.CustomAdapter

class MainFragment : Fragment(R.layout.fragment_main) {

    private var adapter = CustomAdapter(
        { timeItem -> onItemViewClick(timeItem) },
        { timeItem -> onDeleteItemViewClick(timeItem) },
        { timeItem -> onSwitchViewClick(timeItem) }
    )
    private var appDatabase: AppDatabase? = null
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        appDatabase = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "database-name"
        ).build()

        appDatabase?.let {
            viewModel.deletingObsoleteDates(it)
        }


        viewModel.timeItemListData.observe(this) { timeItemList ->
            adapter.addData(timeItemList)
        }

        binding.floatingActionButton.setOnClickListener {
            val bundle = Bundle()
            val timerFragment = TimerFragment()
            timerFragment.arguments = bundle

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_layout, timerFragment, null)
                .addToBackStack(null)
                .commit()
        }

        binding.end.setOnClickListener { requireActivity().finish() }

    }

    fun onItemViewClick(timeItem: TimeItem) {

    }

    fun onDeleteItemViewClick(timeItem: TimeItem) {
        adapter.deleteData(timeItem)
        Thread {appDatabase?.TimeListDao()?.delete(timeItem) }.start()
    }

    fun onSwitchViewClick(timeItem: TimeItem) {
        Thread {
            if (timeItem.switch) {
//                appDatabase?.TimeListDao()?.updateItems(TimeItem(timeItem.id, timeItem.name, timeItem.timeFinish,false ))
                timeItem.switch=false
            }else if (!timeItem.switch){
//                appDatabase?.TimeListDao()?.updateItems(timeItem.copy(switch = true))
                timeItem.switch=true
            }
            Log.d("MyLogs", timeItem.switch.toString())
        }.start()
    }
}