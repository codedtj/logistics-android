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
import com.example.duoblogistics.internal.data.ACTION_BRANCH_TO_CAR
import com.example.duoblogistics.internal.data.ACTION_CAR_TO_BRANCH
import com.example.duoblogistics.internal.data.ACTION_CAR_TO_CAR
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

    private lateinit var actionDetailViewModel: ActionDetailViewModel

    private val actionDetailViewModelFactory: ActionDetailViewModelFactory by instance()

    private lateinit var binding: FragmentActionDetailsBinding

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


        actionDetailViewModel = activity?.run {
            ViewModelProvider(this, actionDetailViewModelFactory)
                .get(ActionDetailViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        val adapter = StoredItemsAdapter(this, tripsViewModel)
        binding.actionStoredItemsRv.layoutManager = LinearLayoutManager(this.context)
        binding.actionStoredItemsRv.adapter = adapter
        val divider = DividerItemDecoration(
            binding.actionStoredItemsRv.context,
            LinearLayoutManager(this.context).orientation
        )

        binding.actionStoredItemsRv.addItemDecoration(divider)
        binding.actionStoredItemsRv.itemAnimator = null

        actionDetailViewModel.actionWithStoredItems.observe(viewLifecycleOwner, {
            setVisibility(it.action.name)
            when (it.action.status) {
                "pending" -> binding.actionStatusTv.text = "Ожидает отправки"
                "completed" -> binding.actionStatusTv.text = "Отправлено"
            }

            when(it.action.name){
                ACTION_CAR_TO_CAR ->  binding.actionNameTv.text = "C рейса на рейс"
                ACTION_BRANCH_TO_CAR ->  binding.actionNameTv.text = "Со склада в машину"
                ACTION_CAR_TO_BRANCH ->  binding.actionNameTv.text = "Из машины в склад"
                else -> binding.actionNameTv.text ="НЛО"
            }

            adapter.submitList(it.storedItems.toMutableList())
        })

        actionsViewModel.selectedAction?.apply {
            actionDetailViewModel.getActionStoredItems(id)
        }

        actionDetailViewModel.trip.observe(viewLifecycleOwner, {
            binding.actionTripTv.text = "Рейс: " + it.code
        })

        actionDetailViewModel.tripTo.observe(viewLifecycleOwner, {
            binding.actionTripToTv.text = "На рейс: " + it.code
        })

        actionDetailViewModel.branch.observe(viewLifecycleOwner, {
            binding.actionBranchTv.text = "На склад: " + it.name
        })
    }

    private fun setVisibility(action: String) {
        binding.actionBranchTv.visibility = View.GONE
        binding.actionTripToTv.visibility = View.GONE
        when (action) {
            ACTION_CAR_TO_CAR -> binding.actionTripToTv.visibility = View.VISIBLE
            ACTION_CAR_TO_BRANCH ->  binding.actionBranchTv.visibility = View.VISIBLE
        }
    }
}