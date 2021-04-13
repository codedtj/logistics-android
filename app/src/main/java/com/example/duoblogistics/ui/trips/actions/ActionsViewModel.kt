package com.example.duoblogistics.ui.trips.actions

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.duoblogistics.data.db.entities.Action
import com.example.duoblogistics.data.db.entities.StoredItem
import com.example.duoblogistics.data.db.entities.Trip
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

    fun resetSaved(){
        mSaved.postValue(false)
    }
}