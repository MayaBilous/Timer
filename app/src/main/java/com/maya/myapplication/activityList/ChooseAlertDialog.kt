package com.maya.myapplication

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog

@RequiresApi(Build.VERSION_CODES.O)
fun showSingleChoiceAlertDialog(
    title: String,
    itemList: List<Int>,
    context: Context,
    action: (Int) -> Unit
) {
    val timeTextItems = itemList
        .map { it.toString() }
        .toTypedArray()
    val dialog = AlertDialog.Builder(context)
        .setTitle(title)
        .setSingleChoiceItems(timeTextItems, 0) { dialog, which ->
            action(which)
            dialog.dismiss()

        }
        .setPositiveButton(R.string.action_close, null)
        .create()
    dialog.show()
}