package com.dessoft.dogs.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dessoft.dogs.databinding.FragmentSingUpBinding

class SingUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSingUpBinding.inflate(inflater)
        return binding.root
    }

}