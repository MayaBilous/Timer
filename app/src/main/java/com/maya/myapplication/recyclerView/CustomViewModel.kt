//package com.maya.myapplication.recyclerView
//
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import com.maya.myapplication.entiti.AppDatabase
//import com.maya.myapplication.entiti.TimeItem
//
//class CustomViewModel : ViewModel() {
//
//    private val _timeItemListData = MutableLiveData<List<TimeItem>>()
//    val timeItemListData: LiveData<List<TimeItem>>
//        get() = _timeItemListData
//
//    fun getItemList(appDatabase: AppDatabase) {
//        Thread {
//            val result = appDatabase.TimeListDao().getAll()
//            _timeItemListData.postValue(result)
//        }.start()
//    }
//}