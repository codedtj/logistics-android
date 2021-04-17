package com.example.duoblogistics.ui.main

import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.ListenableWorker
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.entities.ActionWithStoredItems
import com.example.duoblogistics.data.db.entities.Branch
import com.example.duoblogistics.data.network.RemoteDataSource
import com.example.duoblogistics.data.network.models.ActionWithItemsList
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.data.ACTION_STATUS_COMPLETED
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class AppViewModel(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : BaseViewModel() {
    private val mCode = MutableLiveData<String>()
    val code: LiveData<String>
        get() = mCode

    private val mPendingActions = MutableLiveData<List<ActionWithStoredItems>>()
    val pendingActions: LiveData<List<ActionWithStoredItems>>
        get() = mPendingActions


    fun pushCode(code: String) {
        val current = mCode.value
        if (current != code) {
            mCode.postValue(code)
        }
    }

    fun getBranches() {
        compositeDisposable += remoteDataSource.fetchBranches()
            .switchMapSingle {
                Log.d("app-vm", "Branches are fetched $it")
                localDataSource.saveBranches(it)
            }
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    Log.d("app-vm", "Branches are saved $it")
                },
                {
                    Log.e("app-vm", "Failed to save branches $it")
                }
            )
    }

    fun getPendingActions() {
        compositeDisposable += localDataSource.getPendingAction()
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    this.mPendingActions.postValue(it)
                    Log.d("app-vm", "Pending actions are loaded$it")
                },
                {
                    Log.e("app-vm", "Failed to load pending actions $it")
                }
            )
    }


    private val syncing = MutableLiveData<Boolean>()

    fun syncPendingAction() {
        if (syncing.value == true)
            return

        val actionWithStoredItems = pendingActions.value?.firstOrNull()

        if (actionWithStoredItems != null) {
            syncing.postValue(true)
            postAction(actionWithStoredItems)
        }

    }

    private fun postAction(actionWithStoredItems: ActionWithStoredItems) {
        Log.d("app-vm", "Trying to sync action $actionWithStoredItems")
        compositeDisposable += remoteDataSource.postAction(
            ActionWithItemsList(
                actionWithStoredItems.action.name,
                actionWithStoredItems.action.tripId,
                actionWithStoredItems.action.tripToId,
                actionWithStoredItems.action.branchToId,
                actionWithStoredItems.storedItems.map {
                    it.id
                }
            )
        )
            .doFinally {
                Log.d("app-vm", "Finally")
                syncing.postValue(false)
                SystemClock.sleep(5000)
                syncPendingAction()
            }
            .andThen {
                Log.d("app-vm", "Updating Status")
                actionWithStoredItems.action.status = ACTION_STATUS_COMPLETED
                localDataSource.updateAction(
                    actionWithStoredItems.action
                ).subscribe(
                    {
                        Log.d("app-vm", "Action is synced")
                        mPendingActions.value?.let{actions->
                            mPendingActions.postValue(
                                actions.filter {
                                    it.action.id != actionWithStoredItems.action.id
                                }
                            )
                        }
                    },
                    {
                        Log.e("app-vm", "Failed to sync action $it")
                        SystemClock.sleep(10000)
                    }
                )
            }
            .subscribeOn(Schedulers.newThread())
            .subscribe(

            )
    }
}