package com.example.duoblogistics

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.example.duoblogistics.data.network.IRemoteDataSource
import com.example.duoblogistics.data.network.LogisticApiService
import com.example.duoblogistics.data.network.RemoteDataSource
import com.example.duoblogistics.data.network.interceptors.RequestTokenInterceptor
import com.example.duoblogistics.data.repositories.AuthRepository
import com.example.duoblogistics.data.repositories.AuthRepositoryImpl
import com.example.duoblogistics.internal.utils.SharedSettings
import com.example.duoblogistics.ui.auth.LoginViewModelFactory
import com.example.duoblogistics.ui.main.MainViewModelFactory
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

        bind() from singleton { SharedSettings(instance()) }

        bind() from provider { MainViewModelFactory() }

        bind() from provider { LoginViewModelFactory(instance()) }

        bind() from singleton { LogisticApiService(instance()) }

        bind<IRemoteDataSource>() with singleton { RemoteDataSource(instance()) }

        bind<AuthRepository>() with singleton { AuthRepositoryImpl(instance()) }

    }
}