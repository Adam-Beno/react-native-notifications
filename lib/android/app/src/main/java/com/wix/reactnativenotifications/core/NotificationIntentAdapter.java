package com.wix.reactnativenotifications.core;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wix.reactnativenotifications.core.notification.PushNotificationProps;

public class NotificationIntentAdapter {

    private static final String PUSH_NOTIFICATION_EXTRA_NAME = "pushNotification";

    public static PendingIntent createActivity(Context appContext, Intent intent, PushNotificationProps notification) {
        intent.putExtra(PUSH_NOTIFICATION_EXTRA_NAME, notification.asBundle());
        return PendingIntent.getActivity(appContext, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);
    }

    public static boolean canHandleIntent(@Nullable Intent intent) {
        if (intent == null) {
            return false;
        }

        Bundle extras = intent.getExtras();
        if (extras == null) {
            return false;
        }

        return isOurNotification(extras) || isFCMNotification(extras);
    }

    private static boolean isFCMNotification(@NonNull Bundle bundle) {
        return bundle.getString("google.message_id", null) != null;
    }

    private static boolean isOurNotification(@NonNull Bundle intent) {
        return intent.containsKey(PUSH_NOTIFICATION_EXTRA_NAME);
    }

}
