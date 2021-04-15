package com.firebaseChat.ui.viewmodels

import android.app.Application
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.firebaseChat.extension.showShortToast
import com.firebaseChat.ui.repositories.LoginRepository
import com.firebaseChat.utils.ConnectionDetector
import com.firebaseChat.utils.Constants

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var editTextValue = MutableLiveData<String>().apply {
        value = ""
    }

    var pageTransitionLiveData = MutableLiveData<Bundle>()

    fun storeUserProfileInFirebaseDatabase() {
        editTextValue.value?.let {
            if (ConnectionDetector.isConnectingToInternet()) {
                if (it.isEmpty() || it.isBlank()) {
                    showShortToast("Please enter valid user name")
                } else {
                    LoginRepository.setUserProfile(HashMap<String, String>().apply {
                        put(Constants.USER_NAME, it)
                    }, pageTransitionLiveData)
                }
            } else {
                showShortToast("No Internet Connection Available")
            }
        } ?: showShortToast("Please enter valid user name")
    }
}