package com.firebaseChat.ui.repositories

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.firebaseChat.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object LoginRepository {

    fun setUserProfile(
        userProfile: HashMap<String, String>,
        userRegisterLiveData: MutableLiveData<Bundle>
    ) {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                it.result?.user?.apply {
                FirebaseFirestore.getInstance().apply {
                    userProfile[Constants.USER_UID_ID] = uid
                    collection(Constants.GROUPS_USERS_DETAILS).add(userProfile)

                    userRegisterLiveData.value = Bundle().apply {
                            putString(Constants.USER_NAME, userProfile[Constants.USER_NAME])
                            putString(Constants.USER_UID_ID, uid)
                        }
                    }
                }
            } else {
                userRegisterLiveData.value = Bundle()
            }
        }
    }
}