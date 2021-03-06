package com.example.duoblogistics.ui.trips.actions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.duoblogistics.data.db.entities.*
import com.example.duoblogistics.data.repositories.ActionRepository
import com.example.duoblogistics.internal.base.BaseViewModel
import com.example.duoblogistics.internal.extensions.plusAssign
import io.reactivex.schedulers.Schedulers

class ActionsViewModel(
    private val actionRepository: ActionRepository
) : BaseViewModel() {
    private val mSaved = MutableLiveData<Boolean>()
    val saved: LiveData<Boolean>
        get() = mSaved

    private val mActions = MutableLiveData<List<Action>>()
    val actions: LiveData<List<Action>>
        get() = mActions

    private val mActionWithEverything = MutableLiveData<ActionWithEverything>()
    val actionWithEverything: LiveData<ActionWithEverything>
        get() = mActionWithEverything

    var selectedAction: Action? = null

    fun saveActionWithStoredItems(action: Action, storedItems: List<StoredItem>) {
        compositeDisposable += actionRepository.saveActionWithStoredItems(action, storedItems)
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    Log.d("actions-vm", "Action saved $it")
                    mSaved.postValue(true)
                },
                {
                    Log.e("actions-vm", "Failed to stored action with stored items $it")
                })
    }

    fun getActions() {
        compositeDisposable += actionRepository.getActions()
            .subscribeOn(Schedulers.computation())
            .subscribe(
                {
                    mActions.postValue(it)
                },
                {
                    Log.e("actions-vm", "Failed to load actions $it")
                })
    }



    fun getActionWithEverything(id: Long){
//        compositeDisposable += actionRepository.getActionWithEverything(id)
//            .subscribeOn(Schedulers.computation())
//            .subscribe(
//                {
//                    Log.d("actions-vm", "Action loaded $it")
//                    mActionWithEverything.postValue(it)
//                },
//                {
//                    Log.e("actions-vm", "Failed to load action with everything $it")
//                }
//            )
    }

    fun resetSaved() {
        mSaved.postValue(false)
    }
}