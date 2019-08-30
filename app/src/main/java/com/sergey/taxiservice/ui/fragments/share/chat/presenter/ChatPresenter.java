package com.sergey.taxiservice.ui.fragments.share.chat.presenter;

import com.google.gson.Gson;
import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.manager.preferences.PreferencesManager;
import com.sergey.taxiservice.models.chat.MessageApiModel;
import com.sergey.taxiservice.models.chat.MessageDbModel;
import com.sergey.taxiservice.ui.base.BasePresenter;
import com.sergey.taxiservice.ui.fragments.share.chat.adapter.User;
import com.sergey.taxiservice.ui.fragments.share.chat.view.ChatView;
import com.sergey.taxiservice.util.RxTransformers;
import com.sergey.taxiservice.util.TimeUtils;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jivesoftware.smackx.muc.DiscussionHistory;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.packet.DataForm;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;

import rx.Observable;

public class ChatPresenter extends BasePresenter<ChatView> implements MessageListener {

    private PreferencesManager preferencesManager;
    private MultiUserChat multiUserChat;
    private XMPPTCPConnection mConnection;

    @Inject
    ChatPresenter(PreferencesManager preferencesManager) {
        this.preferencesManager = preferencesManager;
    }

    public User loadSelfUser() {
        User user = new User();
        user.setId(String.valueOf(preferencesManager.getUser().getId()));
        user.setName(preferencesManager.getUser().getFirstName() + " " + preferencesManager.getUser().getLastName());
        user.setAvatar("");

        return user;
    }

    public void init(String username, String password, int chatId) {
        Observable.fromCallable(this::connectToServer)
                .map(bool -> createAccount(username, password))
                .map(bool -> createMultiChat(username, password, chatId))
                .compose(RxTransformers.applyApiRequestSchedulers())
                .subscribe();
    }

    public void sendMessage(int userId, String avatar, String name, int chatId, String message) {
        MessageApiModel messageApiModel = new MessageApiModel();
        messageApiModel.setId(UUID.randomUUID().toString());
        messageApiModel.setAvatar(avatar);
        messageApiModel.setUserName(name);
        messageApiModel.setUserId(userId);
        messageApiModel.setChatId(chatId);
        messageApiModel.setMessage(message);
        messageApiModel.setDate(TimeUtils.convertToGeneralFormat(new Date()));

        try {
            multiUserChat.sendMessage(new Gson().toJson(messageApiModel));
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if(mConnection != null)
            mConnection.disconnect();
    }

//    public void loadHistory(int chatId) {
//        List<MessageDbModel> dbModels = TaxiApplication.getDbInstance()
//                .where(MessageDbModel.class)
//                .equalTo("chatId", chatId)
//                .findAll();
//
//        getView().loadHistory(dbModels);
//    }

    @Override
    public void processMessage(Message message) {
        MessageApiModel apiModel = new Gson().fromJson(message.getBody(), MessageApiModel.class);
        MessageDbModel dbModel = apiModel.toDbModel();
        TaxiApplication.getDbInstance().executeTransaction(realm -> realm.insertOrUpdate(dbModel));
        getView().addMessage(dbModel);
    }

    private boolean createMultiChat(String username, String password, int chatId) {
        try {
            mConnection.login(username, password);
            multiUserChat = MultiUserChatManager.getInstanceFor(mConnection).getMultiUserChat("group_" + chatId + "@conference.taxi-prod");

            if(multiUserChat.createOrJoin(username, password, new DiscussionHistory(), 10000))
                multiUserChat.sendConfigurationForm(new Form(DataForm.Type.submit));

            multiUserChat.addMessageListener(this);
            return true;

        } catch (XMPPException | SmackException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean connectToServer() {
        try {
            XMPPTCPConnectionConfiguration.Builder config = XMPPTCPConnectionConfiguration.builder()
                    .setServiceName("94.130.185.247")
                    .setHost("94.130.185.247")
                    .setPort(5222)
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setDebuggerEnabled(true);

            mConnection = new XMPPTCPConnection(config.build());
            mConnection.setPacketReplyTimeout(10000);
            mConnection.connect();
            return true;

        } catch (SmackException | IOException | XMPPException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean createAccount(String username, String password) {
        try {
            AccountManager accountManager = AccountManager.getInstance(mConnection);
            accountManager.sensitiveOperationOverInsecureConnection(true);
            accountManager.createAccount(username, password);
            return true;

        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
