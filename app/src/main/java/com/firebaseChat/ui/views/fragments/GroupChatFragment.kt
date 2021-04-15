package com.firebaseChat.ui.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebaseChat.R
import com.firebaseChat.databinding.GroupChatFragmentBinding
import com.firebaseChat.extension.clearTextField
import com.firebaseChat.extension.showShortToast
import com.firebaseChat.model.MessageModel
import com.firebaseChat.ui.views.adapters.GroupChatAdapter
import com.firebaseChat.utils.ConnectionDetector
import com.firebaseChat.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class GroupChatFragment : Fragment() {
    private lateinit var groupChatAdapter: GroupChatAdapter
    private lateinit var viewModel: GroupChatViewModel
    private lateinit var binding: GroupChatFragmentBinding
    private val firebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GroupChatFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GroupChatViewModel::class.java)
        arguments?.apply {
            binding.toolbar.title = "Group Chat"
                binding.toolbar.setNavigationIcon(R.drawable.ic_arrow)
                binding.toolbar.setNavigationOnClickListener {
                    findNavController().navigate(R.id.user_login)
                }
            setRecyclerView(this)
            setUpMsgReplyBox(this)
        }
    }

    private fun setUpMsgReplyBox(bundle: Bundle) {
        binding.apply {
            ivSend.setOnClickListener {
                if (ConnectionDetector.isConnectingToInternet()) {
                    val reply = etReplyBox.text.toString()
                    if (reply.isEmpty()) {
                        activity?.showShortToast("Message should not be empty!!")
                    } else {
                        firebaseFirestore.collection(Constants.GROUPS_MESSAGES_DETAILS).add(
                            MessageModel().apply {
                                userName = bundle.getString(Constants.USER_NAME, "")
                                msg = reply
                                msgId = bundle.getString(Constants.USER_UID_ID, "")
                            }
                        ).addOnSuccessListener {
                            etReplyBox.clearTextField()
                            rvGroupChat.adapter?.let {
                                rvGroupChat.smoothScrollToPosition(it.itemCount - 1)
                            }
                        }.addOnFailureListener {
                            activity?.showShortToast("Something went wrong")
                        }
                    }
                } else {
                    activity?.showShortToast("No internet connection is available")
                }
            }
        }
    }

    private fun setRecyclerView(bundle: Bundle) {
        val userName = bundle.getString(Constants.USER_NAME, "")
        val query = firebaseFirestore.collection(Constants.GROUPS_MESSAGES_DETAILS).orderBy(
            "msgTime",
            Query.Direction.ASCENDING
        )
        val options = FirestoreRecyclerOptions.Builder<MessageModel>()
            .setQuery(query, MessageModel::class.java)
            .build()
        groupChatAdapter = GroupChatAdapter(userName, options)
        binding.rvGroupChat.adapter = groupChatAdapter
    }

    override fun onStart() {
        super.onStart()
        if (::groupChatAdapter.isInitialized) {
            groupChatAdapter.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (::groupChatAdapter.isInitialized) {
            groupChatAdapter.stopListening()
        }
    }



}