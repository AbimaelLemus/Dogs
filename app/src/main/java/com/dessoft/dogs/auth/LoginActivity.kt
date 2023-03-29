package com.dessoft.dogs.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.dessoft.dogs.MainActivity
import com.dessoft.dogs.R
import com.dessoft.dogs.api.ApiResponseStatus
import com.dessoft.dogs.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentActions,
    SingUpFragment.SignUpFragmentActions {

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.status.observe(this) { status ->
            when (status) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    showErroDialog(status.messageId)
                }
                is ApiResponseStatus.Loading -> {
                    binding.loadingWheel.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Success -> {
                    binding.loadingWheel.visibility = View.GONE
                }
            }
        }

        viewModel.user.observe(this) { user ->
            if (user != null) {
                startMainActivity()
            }
        }
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun showErroDialog(messageId: Int) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.there_was_an_error))
            .setMessage(messageId)
            .setPositiveButton(android.R.string.ok) { _, _ -> /**Dismiss dialog*/ }
            .create()
            .show()
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSingUpFragment())
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        viewModel.singUp(email, password, passwordConfirmation)
    }
}