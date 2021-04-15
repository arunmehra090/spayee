package com.firebaseChat.ui.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.firebaseChat.databinding.ConversationLayoutBinding
import com.firebaseChat.extension.gone
import com.firebaseChat.model.MessageModel

class GroupChatAdapter(private val senderUserName: String, options: FirestoreRecyclerOptions<MessageModel>) :
        FirestoreRecyclerAdapter<MessageModel, GroupChatAdapter.MessageViewHolder>(options) {

    inner class MessageViewHolder(private val binding: ConversationLayoutBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(model: MessageModel) {
            binding.apply {
                model.apply {
                    if (senderUserName.equals(userName, false)) {
                        rlRightLayout.apply {
                            tvRightName.text = userName
                            tvRightMessage.text = msg
                            tvRightMsgTime.text = msgTime
                            rlLeftLayout.root.gone()
                        }
                    } else {
                        rlLeftLayout.apply {
                            tvLeftName.text = userName
                            tvLeftMessage.text = msg
                            tvLeftMsgTime.text = msgTime
                            rlRightLayout.root.gone()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
       return MessageViewHolder(ConversationLayoutBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: MessageModel) {
        holder.bind(model)
    }
}