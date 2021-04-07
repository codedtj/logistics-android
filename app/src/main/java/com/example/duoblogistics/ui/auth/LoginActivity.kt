package com.example.duoblogistics.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.duoblogistics.data.network.models.Credentials
import com.example.duoblogistics.databinding.ActivityLoginBinding
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
    }

    private fun setListeners() {
        binding.loginBtn.setOnClickListener { authorize() }

        viewModel.authorizeResponse.observe(this, Observer { response ->

//            if (response.status === Status.SUCCESS) {
//                settings.saveToken(response.data!!.token)
//                launchMainActivity()
//                finish()
//            } else
//                activeState()
        })
    }

    private fun launchMainActivity() {
        val activityIntent = Intent(this, MainActivity::class.java)
        startActivity(activityIntent)
    }

    private fun authorize() {
//        val credentials = Credentials(binding.emailEt.text.toString(), binding.passwordEt.text.toString())
        val credentials = Credentials("1010011", "Dhtvtyysq92");
        if (credentials.validate()) {
            busyState()
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
