package com.sergey.taxiservice.ui.fragments.share.chat.view;

import com.sergey.taxiservice.models.chat.MessageDbModel;
import com.sergey.taxiservice.ui.base.BaseView;

import java.util.List;

public interface ChatView extends BaseView {

    void loadHistory(List<MessageDbModel> messageDbModels);

    void addMessage(MessageDbModel messageDbModel);
}
