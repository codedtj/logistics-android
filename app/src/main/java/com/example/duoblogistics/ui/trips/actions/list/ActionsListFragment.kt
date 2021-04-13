package com.example.duoblogistics.ui.trips.actions.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.duoblogistics.R
import com.example.duoblogistics.databinding.FragmentActionsListBinding
import com.example.duoblogistics.ui.main.AppViewModel
import com.example.duoblogistics.ui.trips.actions.ActionsViewModel
import com.example.duoblogistics.ui.trips.actions.ActionsViewModelFactory
import com.example.duoblogistics.ui.trips.details.StoredItemsAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ActionsListFragment : Fragment(), KodeinAware {

    override val kodein by closestKodein()

    private lateinit var actionsViewModel: ActionsViewModel

    private val actionsViewModelFactory: ActionsViewModelFactory by instance()

    private lateinit var binding: FragmentActionsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentActionsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ActionsAdapter()
        binding.actionsRecyclerViewer.layoutManager =  LinearLayoutManager(this.context)
        binding.actionsRecyclerViewer.adapter = adapter
        val divider = DividerItemDecoration(
            binding.actionsRecyclerViewer.context,
            LinearLayoutManager(this.context).orientation
        )

        binding.actionsRecyclerViewer.addItemDecoration(divider)

        actionsViewModel = activity?.run {
            ViewModelProvider(this, actionsViewModelFactory)
                .get(ActionsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        actionsViewModel.actions.observe(viewLifecycleOwner, { actions ->
            if (actions != null) {
                adapter.submitList(actions)
            }
        })

        actionsViewModel.getActions()
    }

}