package com.example.duoblogistics.ui.trips.actions

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.duoblogistics.R
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.Trip
import com.example.duoblogistics.databinding.FragmentSelectActionBinding
import com.example.duoblogistics.internal.data.ACTION_BRANCH_TO_CAR
import com.example.duoblogistics.internal.data.ACTION_CAR_TO_BRANCH
import com.example.duoblogistics.internal.data.ACTION_CAR_TO_CAR
import com.example.duoblogistics.ui.trips.TripsViewModel
import com.example.duoblogistics.ui.trips.TripsViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SelectActionFragment : Fragment(), KodeinAware {
    override val kodein by closestKodein()

    private lateinit var binding: FragmentSelectActionBinding

    private lateinit var tripsViewModel: TripsViewModel

    private val tripsViewModelFactory: TripsViewModelFactory by instance()

    private lateinit var actionsViewModel: ActionsViewModel

    private val actionsViewModelFactory: ActionsViewModelFactory by instance()

    private val selectActionViewModel: SelectActionViewModel = SelectActionViewModel()

    private lateinit var trip: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tripsViewModel = activity?.run {
            ViewModelProvider(this, tripsViewModelFactory)
                .get(TripsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        actionsViewModel = activity?.run {
            ViewModelProvider(this, actionsViewModelFactory)
                .get(ActionsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        tripsViewModel.selectedTrip?.let {
            trip = it
        }

        tripsViewModel.getTripStoredItems(trip.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectActionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionsViewModel.saved.observe(viewLifecycleOwner, {
            if (it) {
                actionsViewModel.resetSaved()
                findNavController().navigate(
                    SelectActionFragmentDirections.navigateToActionsList()
                )
            }
        })

        selectActionViewModel.selectedAction.observe(viewLifecycleOwner, {
            when (it) {
                ACTION_BRANCH_TO_CAR -> {
                    binding.tripsSpinnerLayout.visibility = View.GONE
                    binding.branchesSpinnerLayout.visibility = View.GONE
                }
                ACTION_CAR_TO_BRANCH -> {
                    binding.tripsSpinnerLayout.visibility = View.GONE
                    binding.branchesSpinnerLayout.visibility = View.VISIBLE
                }
                ACTION_CAR_TO_CAR -> {
                    binding.tripsSpinnerLayout.visibility = View.VISIBLE
                    binding.branchesSpinnerLayout.visibility = View.GONE
                }
            }
        })

        this.context?.let { context ->
            initActionsSpinner(context)
            initTripsSpinner(context)
        }


//        binding.loadToCarBtn.setOnClickListener {
//            tripsViewModel.selectedTrip?.apply {
//                tripsViewModel.storedItems.value?.filter { storedItem ->
//                    storedItem.scanned
//                }?.let {
//                    actionsViewModel.saveActionWithStoredItems(
//                        Action(0, ACTION_BRANCH_TO_CAR, id),
//                        it
//                    )
//                }
//            }
//        }
//        binding.unloadCarBtn.setOnClickListener {
//            tripsViewModel.selectedTrip?.apply {
//                tripsViewModel.storedItems.value?.filter { storedItem ->
//                    storedItem.scanned
//                }?.let {
//                    actionsViewModel.saveActionWithStoredItems(
//                        Action(0, ACTION_CAR_TO_BRANCH, id),
//                        it
//                    )
//                }
//            }
//        }
        binding.saveActionBtn.setOnClickListener {
            selectActionViewModel.selectedAction.value?.let { action ->
                tripsViewModel.storedItems.value?.filter { storedItem ->
                    storedItem.scanned
                }?.let {
                    if (validate(action, it.size))
                        actionsViewModel.saveActionWithStoredItems(
                            Action(
                                0,
                                action,
                                trip.id,
                                selectActionViewModel.selectedTrip.value?.id,
                                selectActionViewModel.selectedBranch.value?.id
                            ),
                            it
                        )
                }
            }


            selectActionViewModel.errorMessage.observe(viewLifecycleOwner, {
                binding.selectActionErrorMsg.text = it
            })
//            tripsViewModel.selectedTrip?.apply {
//                tripsViewModel.storedItems.value?.filter { storedItem ->
//                    storedItem.scanned
//                }?.let {
//                    actionsViewModel.saveActionWithStoredItems(
//                        Action(0, ACTION_CAR_TO_CAR, id),
//                        it
//                    )
//                }
//            }
        }
    }

    private fun validate(action: String, storedItemsCount: Int): Boolean {
        var valid = true

        when (action) {
            ACTION_CAR_TO_BRANCH -> {
                if (selectActionViewModel.selectedBranch.value == null) {
                    valid = false
                    selectActionViewModel.setErrorMessage("Необходимо выбрать филиал")
                }
            }

            ACTION_CAR_TO_CAR -> {
                if (selectActionViewModel.selectedTrip.value == null) {
                    valid = false
                    selectActionViewModel.setErrorMessage("Необходимо выбрать рейс")
                }
            }
        }

        if(storedItemsCount < 1){
            selectActionViewModel.setErrorMessage("Необходимо отсканировать хотя бы один товар")
            valid = false
        }


        return valid
    }

    private fun initActionsSpinner(context: Context) {

        val actionsList =
            listOf("Загрузить в машину", "Выгрузить из машины", "Из машины в машину")
        Log.d("elele", actionsList.toString())

        binding.actionsSpinner.adapter = ArrayAdapter(
            context,
            R.layout.viewholder_textview,
            R.id.genericViewHolderTv,
            actionsList
        )

        binding.actionsSpinner.onItemSelectedListener =
            ActionSpinnerOnSelectedListener(
                selectActionViewModel, listOf(
                    ACTION_BRANCH_TO_CAR, ACTION_CAR_TO_BRANCH, ACTION_CAR_TO_CAR
                )
            )
    }

    private fun initTripsSpinner(context: Context) {
        tripsViewModel.trips.value?.let {
            binding.tripsSpinner.adapter = ArrayAdapter(
                context,
                R.layout.viewholder_textview,
                R.id.genericViewHolderTv,
                it.filter {
                    it.id != trip.id
                }.map { it.code }.toMutableList()
            )

            binding.tripsSpinner.onItemSelectedListener =
                TripSpinnerOnClickListener(selectActionViewModel, it)
        }
    }
}