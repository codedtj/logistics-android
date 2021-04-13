package com.example.duoblogistics.ui.bindingAdapters

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.duoblogistics.R
import com.example.duoblogistics.data.db.entities.Action
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

@BindingAdapter("bind:actionText")
fun setActionText(view:TextView, action: Action){
    when(action.name){
        "carToCar" ->  view.text = "C рейса на рейс"
        "branchToCar" ->  view.text = "Со склада в машину"
        "carToBranch" ->  view.text = "Из машины в склад"
        else -> view.text ="НЛО"
    }
}

@BindingAdapter("bind:actionLayoutBackground")
fun setActionLayoutBackground(view:View, status:String){
    when(status){
        "pending" -> view.setBackgroundColor(Color.parseColor("#00000000"))
        "completed" -> view.setBackgroundColor(Color.parseColor("#4CAF50"))
    }
}