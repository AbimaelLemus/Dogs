package com.dessoft.dogs.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dessoft.dogs.R
import com.dessoft.dogs.databinding.FragmentSingUpBinding
import com.dessoft.dogs.utils.isValidEmail

class SingUpFragment : Fragment() {

    interface SignUpFragmentActions {
        fun onSignUpFieldsValidated(email: String, password: String, passwordConfirmation: String)
    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions
    private lateinit var binding: FragmentSingUpBinding

    /*
    * cuando el fragment se une al activity le pasa un context y ese contexto lo ocupamos como esta en el interface */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        } catch (e: java.lang.ClassCastException) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingUpBinding.inflate(inflater)
        setupSingUpButton()
        return binding.root
    }

    private fun setupSingUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.emailInput.error = ""
        binding.passwordInput.error = ""
        binding.confirmPasswordInput.error = ""

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

        val passwordConfirmation = binding.confirmPasswordEdit.text.toString().trim()
        if (passwordConfirmation.isEmpty()) {
            binding.confirmPasswordInput.error = getString(R.string.password_is_not_valid)
            return
        }

        if (password != passwordConfirmation) {
            binding.passwordInput.error = getString(R.string.passwords_do_not_match)
            return
        }
        signUpFragmentActions.onSignUpFieldsValidated(email, password, passwordConfirmation)


    }


}