package com.example.duoblogistics.ui.splashScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import com.example.duoblogistics.CodedApplication
import com.example.duoblogistics.R
import com.example.duoblogistics.internal.utils.SharedSettings
import com.example.duoblogistics.ui.auth.LoginActivity
import com.example.duoblogistics.ui.main.MainActivity
import com.example.duoblogistics.ui.main.MainViewModel
import com.example.duoblogistics.ui.main.MainViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class SplashScreenActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val mainViewModelFactory: MainViewModelFactory by instance()

    private lateinit var vm: MainViewModel

    private val settings: SharedSettings by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        vm = ViewModelProvider(this.application as CodedApplication, mainViewModelFactory)
            .get(MainViewModel::class.java)

        toLoginActivity();

        if (settings.getToken().isNullOrBlank())
            toLoginActivity()
        else
            toMainActivity()

//        setObservers()
    }

//    private fun setObservers() {
//        vm.userResponse.observe(this, Observer { response ->
//            if (response.status === Result.Status.SUCCESS)
//                toMainActivity()
//            else
//                toLoginActivity()
//        })
//    }

    private fun toLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        navigate(intent)
    }

    private fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        navigate(intent)
    }

    private fun navigate(activityIntent: Intent) {
        startActivity(activityIntent)
        finish()
    }
}
