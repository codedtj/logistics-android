package com.example.duoblogistics

import android.app.Application
import com.example.duoblogistics.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class CodedApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        bind() from provider { MainViewModelFactory() }
    }
}