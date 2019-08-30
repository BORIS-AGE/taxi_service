package com.sergey.taxiservice.ui.fragments.share.chat.adapter;

import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

public class MessageAdapter extends MessagesListAdapter<Message> {

    public MessageAdapter(String senderId) {
        super(senderId, (imageView, url) -> Picasso.get().load(url).into(imageView));
    }
}
