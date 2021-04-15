package com.example.duoblogistics.ui.trips.actions

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
import com.example.duoblogistics.databinding.FragmentSelectActionBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tripsViewModel = activity?.run {
            ViewModelProvider(this, tripsViewModelFactory)
                .get(TripsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        tripsViewModel.selectedTrip?.let {
            tripsViewModel.getTripStoredItems(it.id)
        }

        actionsViewModel = activity?.run {
            ViewModelProvider(this, actionsViewModelFactory)
                .get(ActionsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
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


        this.context?.let { context ->
            tripsViewModel.trips.value?.let {
                binding.tripsSpinner.adapter = ArrayAdapter(
                    context,
                    R.layout.viewholder_textview,
                    R.id.genericViewHolderTv,
                    it.filter{
                        it.id != tripsViewModel.selectedTrip?.id
                    }.map { it.code }.toMutableList()
                )

                binding.tripsSpinner.onItemSelectedListener =
                    TripSpinnerOnClickListener(selectActionViewModel, it)
            }
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
        binding.transferFromCarToCar.setOnClickListener {
            tripsViewModel.selectedTrip?.apply {
                tripsViewModel.storedItems.value?.filter { storedItem ->
                    storedItem.scanned
                }?.let {
                    actionsViewModel.saveActionWithStoredItems(
                        Action(0, ACTION_CAR_TO_CAR, id),
                        it
                    )
                }
            }
        }
    }
}