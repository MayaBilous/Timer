//package com.maya.myapplication.recyclerView
//
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.DialogFragment
//import androidx.fragment.app.viewModels
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.room.Room
//import com.maya.myapplication.R
//import com.maya.myapplication.databinding.DialogFragmentBinding
//import com.maya.myapplication.entiti.AppDatabase
//
//class CustomDialog : DialogFragment(R.layout.dialog_fragment) {
//
//    lateinit var binding: DialogFragmentBinding
//    private val adapter = CustomAdapter()
//    private val viewModel: CustomViewModel by viewModels()
//    private var appDatabase: AppDatabase? = null
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding = DialogFragmentBinding.bind(view)
//        binding.backButton.setOnClickListener { parentFragmentManager.popBackStack() }
//        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        binding.recyclerView.adapter = adapter
//
//        appDatabase = Room.databaseBuilder(
//            requireContext(),
//            AppDatabase::class.java, "database-name"
//        ).build()
//
//        viewModel.timeItemListData.observe(this) { timeItemList ->
//            adapter.addData(timeItemList)
//        }
//
//        appDatabase?.let {
//            viewModel.getItemList(it)
//        }
//    }
//}