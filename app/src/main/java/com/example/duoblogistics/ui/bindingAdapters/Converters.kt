package com.example.duoblogistics.ui.bindingAdapters

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.duoblogistics.R
import com.example.duoblogistics.data.db.entities.StoredItem

@BindingAdapter("bind:tripStatusText")
fun setTripStatusText(view: TextView, status: String) {
    when (status) {
        "active" -> view.setText(R.string.trip_status_active)
        "scheduled" -> view.setText(R.string.trip_status_scheduled)
    }
}

@BindingAdapter("bind:scannedStoredItemBackground")
fun setScannedStoredItemBackground(view: View, storedItem: StoredItem) {

    if (storedItem.scanned){
        Log.d("converter", "Stored item ${storedItem.scanned}")
        view.setBackgroundColor(Color.parseColor("#4CAF50"))
    }else
        view.setBackgroundColor(Color.parseColor("#00000000"))
}