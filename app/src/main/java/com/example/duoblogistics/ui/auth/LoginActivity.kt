package com.example.duoblogistics.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.duoblogistics.data.network.models.Credentials
import com.example.duoblogistics.databinding.ActivityLoginBinding
import com.example.duoblogistics.internal.data.STATUS_BUSY
import com.example.duoblogistics.internal.data.STATUS_NEEDLE
import com.example.duoblogistics.internal.utils.SharedSettings
import com.example.duoblogistics.ui.main.MainActivity
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by closestKodein()

    private val loginViewModelFactory: LoginViewModelFactory by instance()

    private lateinit var viewModel: LoginViewModel

    private val settings: SharedSettings by instance()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProviders.of(this, loginViewModelFactory)
            .get(LoginViewModel::class.java)

        setListeners()

        activeState()
    }

    private fun setListeners() {
        binding.loginBtn.setOnClickListener { authorize() }

        viewModel.authorizeResponse.observe(this, { response ->
                settings.saveToken(response.token)
                launchMainActivity()
                finish()
        })

        viewModel.state.observe(this, {
            when(it){
                 STATUS_NEEDLE-> {
                    this.activeState()
                }
                STATUS_BUSY -> {
                    this.busyState()
                }
            }
        })
    }

    private fun launchMainActivity() {
        val activityIntent = Intent(this, MainActivity::class.java)
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(activityIntent)
        finish()
    }

    private fun authorize() {
//        val credentials = Credentials(binding.emailEt.text.toString(), binding.passwordEt.text.toString())
        val credentials = Credentials("1010011", "Dhtvtyysq92");
        if (credentials.validate()) {
            viewModel.authorize(credentials)
        }
    }

    private fun busyState() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.loginBtn.visibility = View.GONE
    }

    private fun activeState() {
        binding.progressCircular.visibility = View.GONE
        binding.loginBtn.visibility = View.VISIBLE
    }
}
