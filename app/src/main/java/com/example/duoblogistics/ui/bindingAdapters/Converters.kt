package com.example.duoblogistics.ui.bindingAdapters

import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.duoblogistics.R
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.internal.data.ACTION_BRANCH_TO_CAR
import com.example.duoblogistics.internal.data.ACTION_CAR_TO_BRANCH
import com.example.duoblogistics.internal.data.ACTION_CAR_TO_CAR

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
        ACTION_CAR_TO_CAR ->  view.text = "C рейса на рейс"
        ACTION_BRANCH_TO_CAR ->  view.text = "Со склада в машину"
        ACTION_CAR_TO_BRANCH ->  view.text = "Из машины в склад"
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