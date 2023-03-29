package com.dessoft.dogs.auth

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dessoft.dogs.R
import com.dessoft.dogs.databinding.FragmentLoginBinding
import com.dessoft.dogs.utils.isValidEmail

class LoginFragment : Fragment() {

    interface LoginFragmentActions {
        fun onRegisterButtonClick()
        fun onLoginFieldsValidated(email: String, password: String)
    }

    private lateinit var loginFragmentActions: LoginFragmentActions
    private lateinit var binding: FragmentLoginBinding

    /*
    * cuando el fragment se une al activity le pasa un context y ese contexto lo ocupamos como esta en el interface */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }

        binding.loginButton.setOnClickListener {
            validateFields()
        }

        return binding.root
    }


    private fun validateFields() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""

        val email = binding.emailEdit.text.toString().trim()
        if (!isValidEmail(email)) {
            binding.emailInput.error = getString(R.string.email_is_no_valid)
            return
        }

        val password = binding.passwordEdit.text.toString().trim()
        if (password.isEmpty()) {
            binding.passwordInput.error = getString(R.string.password_is_not_valid)
            return
        }

        loginFragmentActions.onLoginFieldsValidated(email, password)


    }
}