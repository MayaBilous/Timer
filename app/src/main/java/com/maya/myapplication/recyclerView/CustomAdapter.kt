package com.maya.myapplication.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maya.myapplication.R
import com.maya.myapplication.entiti.TimeItem

class CustomAdapter(
    val onViewClickAction: (TimeItem) -> Unit,
    val onDeleteClickAction: (TimeItem) -> Unit,
    val onSwitchClickAction: (TimeItem) -> Unit,
) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private val timeItem = mutableListOf<TimeItem>()

    fun addData(item: List<TimeItem>){
        timeItem.clear()
        timeItem.addAll(item)
        notifyDataSetChanged()
    }

    fun deleteData(item:TimeItem){
        timeItem.remove(item)
        notifyDataSetChanged()
    }

    override fun getItemCount() = timeItem.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.recyclerview_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(timeItem[position])
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val timeFinish :TextView = view.findViewById(R.id.timeFinish)
        private val name :TextView = view.findViewById(R.id.name)
        private val switch :Button = view.findViewById(R.id.switchButton)
        private val deleteButton :ImageView = view.findViewById(R.id.deleteButton)

        fun bind(timeItem: TimeItem) {
            timeFinish.text = timeItem.timeFinish
            name.text = timeItem.name
            switch.tag = timeItem.switch
            view.setOnClickListener { onViewClickAction(timeItem) }
            switch.setOnClickListener { onSwitchClickAction(timeItem) }
            deleteButton.setOnClickListener { onDeleteClickAction(timeItem) }

        }
    }
}

