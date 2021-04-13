package com.example.duoblogistics.ui.trips.actions.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ListAdapter
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.databinding.ViewholderActionBinding
import com.example.duoblogistics.databinding.ViewholderStoredItemBinding
import com.example.duoblogistics.ui.trips.TripsViewModel
import com.example.duoblogistics.ui.trips.actions.ActionsViewModel
import com.example.duoblogistics.ui.trips.details.StoredItemClickHandler

class ActionsAdapter(
    private val fragment:Fragment,
    private val vm: ActionsViewModel
) : ListAdapter<Action, ActionViewHolder>(ActionDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder(
            ViewholderActionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        val dataBinding = holder.getDataBinding()
        dataBinding.handler = ActionClickHandler(fragment, vm, item)
    }
}


