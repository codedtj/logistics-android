package com.example.duoblogistics.ui.trips.actions.details

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duoblogistics.R
import com.example.duoblogistics.databinding.FragmentActionDetailsBinding
import com.example.duoblogistics.ui.trips.TripsViewModel
import com.example.duoblogistics.ui.trips.TripsViewModelFactory
import com.example.duoblogistics.ui.trips.actions.ActionsViewModel
import com.example.duoblogistics.ui.trips.actions.ActionsViewModelFactory
import com.example.duoblogistics.ui.trips.actions.list.ActionsAdapter
import com.example.duoblogistics.ui.trips.details.StoredItemsAdapter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ActionDetailsFragment : Fragment(), KodeinAware {


    override val kodein by closestKodein()

    private lateinit var actionsViewModel: ActionsViewModel

    private val actionsViewModelFactory: ActionsViewModelFactory by instance()


    private lateinit var tripsViewModel: TripsViewModel

    private val tripsViewModelFactory: TripsViewModelFactory by instance()

    private lateinit var binding: FragmentActionDetailsBinding;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentActionDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionsViewModel = activity?.run {
            ViewModelProvider(this, actionsViewModelFactory)
                .get(ActionsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")


        tripsViewModel = activity?.run {
            ViewModelProvider(this, tripsViewModelFactory)
                .get(TripsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val adapter = StoredItemsAdapter(this, tripsViewModel)
        binding.actionStoredItemsRv.layoutManager = LinearLayoutManager(this.context)
        binding.actionStoredItemsRv.adapter = adapter
        val divider = DividerItemDecoration(
            binding.actionStoredItemsRv.context,
            LinearLayoutManager(this.context).orientation
        )

        binding.actionStoredItemsRv.addItemDecoration(divider)

        actionsViewModel.actionWithStoredItems.observe(viewLifecycleOwner, {
            when (it.action.status) {
                "pending" -> binding.actionStatusTv.text = "Ожидает отправки"
                "completed" -> binding.actionStatusTv.text = "Отправлено"
            }
            Log.d("action-df", "$it")
            adapter.submitList(it.storedItems.toMutableList())
        })

        actionsViewModel.selectedAction?.apply {
            actionsViewModel.getActionStoredItems(id)
        }
    }
}