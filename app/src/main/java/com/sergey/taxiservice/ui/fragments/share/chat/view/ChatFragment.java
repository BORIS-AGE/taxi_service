package com.sergey.taxiservice.ui.fragments.share.chat.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sergey.taxiservice.R;
import com.sergey.taxiservice.databinding.FragmentChatBinding;
import com.sergey.taxiservice.models.chat.MessageDbModel;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.base.BaseBindingToolbarFragment;
import com.sergey.taxiservice.ui.fragments.share.chat.adapter.Message;
import com.sergey.taxiservice.ui.fragments.share.chat.adapter.MessageAdapter;
import com.sergey.taxiservice.ui.fragments.share.chat.adapter.User;
import com.sergey.taxiservice.ui.fragments.share.chat.presenter.ChatPresenter;
import com.sergey.taxiservice.util.TimeUtils;
import com.stfalcon.chatkit.messages.MessageInput;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends BaseBindingToolbarFragment<ChatPresenter, FragmentChatBinding> implements ChatView, MessageInput.InputListener {

    private final static String CHAT_ID = "chat_id";

    public static void open(Activity activity, int chatId) {
        Intent intent = new Intent(activity, ToolbarActivity.class);
        Bundle fragmentBundle = new Bundle();

        fragmentBundle.putInt(CHAT_ID, chatId);
        intent.putExtra(ToolbarActivity.EXTRA_OPEN_WITH, ToolbarActivity.OPEN_WITH_CHAT);
        intent.putExtra(ToolbarActivity.EXTRA_BUNDLE_DATA, fragmentBundle);

        activity.startActivity(intent);
    }

    private User me;
    private int chatId = 0;
    private MessageAdapter messageAdapter;

    @Override
    public String getTitle() {
        return getString(R.string.chat);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_chat;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = presenter.loadSelfUser();

        if(getArguments() != null) {
            chatId = getArguments().getInt(CHAT_ID) + 1000;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        messageAdapter = new MessageAdapter(me.getId());
        binding.messagesList.setAdapter(messageAdapter);
//        presenter.loadHistory(chatId);

        binding.input.setInputListener(this);
        binding.input.setEnabled(false);
        presenter.init("user_" + me.getId(), "1111", chatId);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.close();
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        presenter.sendMessage(Integer.parseInt(me.getId()), me.getAvatar(),
                me.getName(), chatId, input.toString());
        return true;
    }

    @Override
    public void loadHistory(List<MessageDbModel> messageDbModels) {
        List<Message> messages = new ArrayList<>();
        for (MessageDbModel messageDbModel : messageDbModels) {
            User user = new User();
            user.setId(String.valueOf(messageDbModel.getUserId()));
            user.setAvatar(messageDbModel.getAvatar());
            user.setName(messageDbModel.getUserName());

            Message message = new Message();
            message.setId(messageDbModel.getId());
            message.setDate(TimeUtils.convertFromGeneralFormat(messageDbModel.getDate()));
            message.setMessage(messageDbModel.getMessage());
            message.setUser(user);

            messages.add(message);
        }

        new Handler(Looper.getMainLooper()).post(() -> messageAdapter.addToEnd(messages, false));
    }

    @Override
    public void addMessage(MessageDbModel messageDbModel) {
        User user = new User();
        user.setId(String.valueOf(messageDbModel.getUserId()));
        user.setAvatar(messageDbModel.getAvatar());
        user.setName(messageDbModel.getUserName());

        Message message = new Message();
        message.setId(messageDbModel.getId());
        message.setDate(TimeUtils.convertFromGeneralFormat(messageDbModel.getDate()));
        message.setMessage(messageDbModel.getMessage());
        message.setUser(user);

        new Handler(Looper.getMainLooper()).post(() -> messageAdapter.addToStart(message, true));
    }
}
