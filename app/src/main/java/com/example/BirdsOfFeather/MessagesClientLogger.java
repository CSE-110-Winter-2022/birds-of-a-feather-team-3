package com.example.BirdsOfFeather;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.common.api.internal.ApiKey;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;
import com.google.android.gms.nearby.messages.MessagesClient;
import com.google.android.gms.nearby.messages.MessagesOptions;
import com.google.android.gms.nearby.messages.PublishOptions;
import com.google.android.gms.nearby.messages.StatusCallback;
import com.google.android.gms.nearby.messages.SubscribeOptions;
import com.google.android.gms.tasks.Task;

public class MessagesClientLogger implements MessagesClient {
    private static final String TAG = MessagesClientLogger.class.getSimpleName();

    MessagesClient messagesClient;
    MessagesClientLogger(MessagesClient messagesClient) {
        this.messagesClient = messagesClient;
    }
    @NonNull
    @Override
    public Task<Void> publish(@NonNull Message message) {
        Log.i(TAG, "Publishing");
        return messagesClient.publish(message);
    }

    @NonNull
    @Override
    public Task<Void> publish(@NonNull Message message, @NonNull PublishOptions publishOptions) {
        Log.i(TAG, "Publishing with options");
        return messagesClient.publish(message, publishOptions);
    }

    @NonNull
    @Override
    public Task<Void> unpublish(@NonNull Message message) {
        Log.i(TAG, "Unpublishing");
        return messagesClient.unpublish(message);
    }

    @NonNull
    @Override
    public Task<Void> subscribe(@NonNull MessageListener messageListener) {
        Log.i(TAG, "Subscribing");
        return messagesClient.subscribe(messageListener);
    }

    @NonNull
    @Override
    public Task<Void> subscribe(@NonNull MessageListener messageListener, @NonNull SubscribeOptions subscribeOptions) {
        Log.i(TAG, "Subscribing with options");
        return messagesClient.subscribe(messageListener, subscribeOptions);
    }

    @NonNull
    @Override
    public Task<Void> subscribe(@NonNull PendingIntent pendingIntent, @NonNull SubscribeOptions subscribeOptions) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> subscribe(@NonNull PendingIntent pendingIntent) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> unsubscribe(@NonNull MessageListener messageListener) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> unsubscribe(@NonNull PendingIntent pendingIntent) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> registerStatusCallback(@NonNull StatusCallback statusCallback) {
        return null;
    }

    @NonNull
    @Override
    public Task<Void> unregisterStatusCallback(@NonNull StatusCallback statusCallback) {
        return null;
    }

    @Override
    public void handleIntent(@NonNull Intent intent, @NonNull MessageListener messageListener) {

    }

    @NonNull
    @Override
    public ApiKey<MessagesOptions> getApiKey() {
        return messagesClient.getApiKey();
    }
}
