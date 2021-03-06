package com.example.duoblogistics.ui.trips.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duoblogistics.R
import com.example.duoblogistics.databinding.FragmentTripBinding
import com.example.duoblogistics.ui.barcodereader.LiveBarcodeScanFragment
import com.example.duoblogistics.ui.main.AppViewModel
import com.example.duoblogistics.ui.main.AppViewModelFactory
import com.example.duoblogistics.ui.trips.TripsViewModel
import com.example.duoblogistics.ui.trips.TripsViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class TripFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var binding: FragmentTripBinding

    private lateinit var tripsViewModel: TripsViewModel

    private lateinit var appViewModel: AppViewModel

    private val tripsViewModelFactory: TripsViewModelFactory by instance()

    private val appViewModelFactory: AppViewModelFactory by instance()

    private var barCodeScannerIsVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("trip-fragment", "Creating fragment")

        tripsViewModel = activity?.run {
            ViewModelProvider(this, tripsViewModelFactory)
                .get(TripsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        appViewModel = activity?.run {
            ViewModelProvider(this, appViewModelFactory)
                .get(AppViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        appViewModel.code.observe(this, {
            tripsViewModel.scanStoredItem(it)
        })

        tripsViewModel.selectedTrip?.apply {
            tripsViewModel.refreshStoredItems(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("trip-fragment", "Creating view")

        binding = FragmentTripBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = StoredItemsAdapter(this, tripsViewModel)
        binding.tripStoredItemsRv.adapter = adapter
        val divider = DividerItemDecoration(
            binding.tripStoredItemsRv.context,
            LinearLayoutManager(this.context).orientation
        )

        binding.tripStoredItemsRv.addItemDecoration(divider)
        binding.tripStoredItemsRv.itemAnimator = null

        tripsViewModel.storedItems.observe(viewLifecycleOwner, { storedItems ->
            if (storedItems != null) {
                adapter.submitList(storedItems)
                binding.scannedItemsCounter.text =
                    "?????????????????????????? " + storedItems.count { it.scanned }.toString()
            }
        })

        binding.toggleScannerButton.setOnClickListener {
            barCodeScannerIsVisible = !barCodeScannerIsVisible

            activity?.apply {
                if (barCodeScannerIsVisible)
                    supportFragmentManager.commit {
                        setReorderingAllowed(true)
                        add<LiveBarcodeScanFragment>(
                            R.id.tripFragmentContainerView,
                            "Live_Scan_Barcode_Trip_Fragment"
                        )
                    }
                else {
                    val fragment =
                        supportFragmentManager.findFragmentByTag("Live_Scan_Barcode_Trip_Fragment")
                    if (fragment != null)
                        supportFragmentManager.commit {
                            setReorderingAllowed(true)
                            remove(fragment)
                        }
                }

            }
        }

        binding.nextPageFloatingButton.setOnClickListener {
            findNavController().navigate(
                TripFragmentDirections.navigateToSelectActionsFragment()
            )
        }

        tripsViewModel.selectedTrip?.apply {
            tripsViewModel.getTripStoredItems(id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        tripsViewModel.clearTripStoredItems()
        Log.d("trip-fragment", "My view is destroyed")
    }

    override fun onDestroy() {
        tripsViewModel.clearDisposables()
        super.onDestroy()
        Log.d("trip-fragment", "I am destroyed")
    }
}