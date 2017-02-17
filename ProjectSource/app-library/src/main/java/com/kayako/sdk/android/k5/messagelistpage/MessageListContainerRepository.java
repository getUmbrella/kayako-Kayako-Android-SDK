package com.kayako.sdk.android.k5.messagelistpage;

import android.os.Handler;

import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.base.callback.ListCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.error.ResponseMessages;
import com.kayako.sdk.error.response.Notification;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.message.Message;
import com.kayako.sdk.messenger.message.MessageSourceType;
import com.kayako.sdk.messenger.message.PostMessageBodyParams;

import java.util.List;

public class MessageListContainerRepository implements MessageListContainerContract.Data {

    private Messenger mMessenger;

    public MessageListContainerRepository(String helpCenterUrl, FingerprintAuth fingerpritnAuth) {
        mMessenger = new Messenger(helpCenterUrl, fingerpritnAuth);
    }

    @Override
    public void postNewMessage(long conversationId, String contents, final MessageListContainerContract.PostNewMessageCallback callback) {
        final Handler handler = new Handler();
        mMessenger.postMessage(conversationId, new PostMessageBodyParams(contents, MessageSourceType.MESSENGER), new ItemCallback<Message>() {
            @Override
            public void onSuccess(final Message item) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(item);
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: Parse and see if there's a notification in KayakoException
                        callback.onFailure(exception.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void getMessages(final MessageListContainerContract.OnLoadMessagesListener listener, long conversationId, int offset, int limit) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread
        mMessenger.getMessages(conversationId, offset, limit, new ListCallback<Message>() {
            @Override
            public void onSuccess(final List<Message> items) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onSuccess(items);
                        }
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener == null) {
                            return;
                        }

                        ResponseMessages responseMessages = exception.getResponseMessages();
                        if (responseMessages != null) {
                            List<Notification> notifications = responseMessages.getNotifications();
                            if (notifications != null && notifications.size() > 0) {
                                listener.onFailure(notifications.get(0).message);
                                return;
                            }
                        }

                        listener.onFailure(exception.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public void startNewConversation(PostConversationBodyParams bodyParams, final MessageListContainerContract.OnLoadConversationListener onLoadConversationListener) {
        final Handler handler = new Handler(); // Needed to ensure that the callbacks run on the UI Thread
        mMessenger.postConversation(bodyParams, new ItemCallback<Conversation>() {
            @Override
            public void onSuccess(final Conversation item) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onLoadConversationListener == null) {
                            return;
                        }

                        onLoadConversationListener.onSuccess(item);
                    }
                });
            }

            @Override
            public void onFailure(final KayakoException exception) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (onLoadConversationListener == null) {
                            return;
                        }

                        onLoadConversationListener.onFailure(exception.getMessage()); // TODO: Add a method to extract the latest NOTIFICATION
                    }
                });
            }
        });
    }
}