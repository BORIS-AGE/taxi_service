package com.sergey.taxiservice.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.sergey.taxiservice.R;
import com.sergey.taxiservice.TaxiApplication;
import com.sergey.taxiservice.models.db.CurrentOrder;
import com.sergey.taxiservice.models.db.OrderState;
import com.sergey.taxiservice.models.db.OrderType;
import com.sergey.taxiservice.models.order.CreateOrder;
import com.sergey.taxiservice.models.share.ActionModel;
import com.sergey.taxiservice.ui.activities.ToolbarActivity;
import com.sergey.taxiservice.ui.fragments.share.action.view.ActionFragment;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static com.sergey.taxiservice.ui.activities.ToolbarActivity.EXTRA_OPEN_WITH;
import static com.sergey.taxiservice.ui.activities.ToolbarActivity.OPEN_WITH_SHARE_ACTION;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private Random random = new Random();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String message = remoteMessage.getData().toString();
            Log.d(this.getClass().getName(), "Data payload: " + message);

            if(!message.isEmpty()) {
                message = message.replace("{default=", "");
                message = message.substring(0, message.length() - 1);

                Log.d(getClass().getCanonicalName(), message);
                if(message.contains("wink")) {
                    message = message.replace("{\"wink\":", "");
                    message = message.substring(0, message.length() - 1);

                    ActionModel actionModel = new Gson().fromJson(message, ActionModel.class);
                    buildNotification("Вам подмигнули", actionModel.getClientId(), ActionFragment.WINKS);

                } else if (message.contains("respect")) {
                    message = message.replace("{\"respect\":", "");
                    message = message.substring(0, message.length() - 1);

                    ActionModel actionModel = new Gson().fromJson(message, ActionModel.class);
                    buildNotification("Вам выразили уважение", actionModel.getClientId(), ActionFragment.RESPECT);
                }

//                CreateOrder createOrder = new Gson().fromJson(message, CreateOrder.class);
//
//                TaxiApplication.getDbInstance().executeTransaction(realm -> {
//                    CurrentOrder currentOrder = new CurrentOrder();
//                    currentOrder.setType(OrderType.COMPANION);
//                    currentOrder.setState(OrderState.DRIVER_SEARCHING);
//                    currentOrder.setRideInfo(new Gson().toJson(createOrder.getRide()));
//
//                    realm.insertOrUpdate(currentOrder);
//                });
            }
        }
    }

    private void buildNotification(String message, int userId, int actionType) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(message)
                        .setAutoCancel(true)
                        .setChannelId(getString(R.string.default_notification_channel_id));

        Intent resultIntent = new Intent(this, ToolbarActivity.class);
        resultIntent.putExtra(EXTRA_OPEN_WITH, OPEN_WITH_SHARE_ACTION);
        resultIntent.putExtra(ActionFragment.ACTION_TYPE, actionType);
        resultIntent.putExtra(ActionFragment.USER_ID, userId);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(mNotificationManager != null) {

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(getString(R.string.default_notification_channel_id), "Share", NotificationManager.IMPORTANCE_HIGH);
                mNotificationManager.createNotificationChannel(mChannel);
            }

            mNotificationManager.notify(randomId(100000, 1), mBuilder.build());
        }
    }

    private int randomId(int max, int min) {
        return random.nextInt((max - min) + 1) + min;
    }
}
