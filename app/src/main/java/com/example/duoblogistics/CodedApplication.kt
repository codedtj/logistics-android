package com.example.duoblogistics

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.duoblogistics.data.db.DuobLogisticsDatabase
import com.example.duoblogistics.data.db.LocalDataSource
import com.example.duoblogistics.data.db.LocalDataSourceImpl
import com.example.duoblogistics.data.network.LogisticApiService
import com.example.duoblogistics.data.network.RemoteDataSource
import com.example.duoblogistics.data.network.RemoteDataSourceImpl
import com.example.duoblogistics.data.repositories.*
import com.example.duoblogistics.internal.utils.SharedSettings
import com.example.duoblogistics.ui.auth.LoginViewModelFactory
import com.example.duoblogistics.ui.main.AppViewModelFactory
import com.example.duoblogistics.ui.trips.TripsViewModelFactory
import com.example.duoblogistics.ui.trips.actions.ActionsViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class CodedApplication : Application(), KodeinAware, ViewModelStoreOwner {
    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

    override val kodein = Kodein.lazy {

        import(androidXModule(this@CodedApplication))

        //Database
        bind() from singleton { DuobLogisticsDatabase(instance()) }
        bind() from singleton { instance<DuobLogisticsDatabase>().tripDao() }
        bind() from singleton { instance<DuobLogisticsDatabase>().storedItemDao() }
        bind() from singleton { instance<DuobLogisticsDatabase>().storedItemInfoDao() }
        bind() from singleton { instance<DuobLogisticsDatabase>().actionDao() }

        //settings
        bind() from singleton { SharedSettings(instance()) }

        //view models
        bind() from provider { LoginViewModelFactory(instance()) }
        bind() from provider { AppViewModelFactory() }
        bind() from provider { TripsViewModelFactory(instance(), instance()) }
        bind() from provider { ActionsViewModelFactory(instance()) }

        //services
        bind() from singleton { LogisticApiService(instance()) }

        //data source
        bind<RemoteDataSource>() with singleton { RemoteDataSourceImpl(instance()) }
        bind<LocalDataSource>() with singleton { LocalDataSourceImpl(instance(), instance(), instance(), instance()) }

        //repositories
        bind<AuthRepository>() with singleton { AuthRepositoryImpl(instance()) }
        bind<TripsRepository>() with singleton { TripsRepositoryImpl(instance(), instance()) }
        bind<StoredItemRepository>() with singleton { StoredItemRepositoryImpl(instance()) }
        bind<ActionRepository>() with singleton { ActionRepositoryImpl(instance()) }

    }
}