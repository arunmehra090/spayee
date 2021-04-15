package com.firebaseChat.ui.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.firebaseChat.R
import com.firebaseChat.databinding.LoginFragmentBinding
import com.firebaseChat.extension.closeKeyboard
import com.firebaseChat.extension.showShortToast
import com.firebaseChat.ui.viewmodels.LoginViewModel
import com.firebaseChat.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = LoginFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.loginViewModel = viewModel

        viewModel.pageTransitionLiveData.observe(viewLifecycleOwner, {
            if (it.isEmpty) {
                activity?.showShortToast("Something went wrong")
            } else {
                binding.etUserId.closeKeyboard()
                findNavController().navigate(R.id.from_login_to_group_chat, it)
            }
        })
    }
}