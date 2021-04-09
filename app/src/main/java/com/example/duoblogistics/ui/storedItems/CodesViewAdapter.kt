package com.example.duoblogistics.ui.storedItems

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.duoblogistics.ui.main.MainViewModel
import com.example.duoblogistics.R
import com.example.duoblogistics.data.db.entities.Code

class CodesViewAdapter(
private val viewModel: MainViewModel
) : RecyclerView.Adapter<CodesViewAdapter.ViewHolder>() {

    private var codes: List<Code> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_code, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val code = codes[position]
        holder.idView.text = (position + 1).toString()
        holder.contentView.text = code.code
        holder.deleteBtn.setOnClickListener {
            viewModel.removeCode(code)
        }
    }

    override fun getItemCount(): Int = codes.size

    fun setData(newCodes: List<Code>) {
        codes = newCodes
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.item_number)
        val contentView: TextView = view.findViewById(R.id.content)
        val deleteBtn: Button = view.findViewById(R.id.delete)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }
}