package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import com.kayako.sdk.android.k5.common.adapter.loadmorelist.LoadMoreListType;

public interface MessengerListType extends LoadMoreListType {

    // Message Types
    int SIMPLE_MESSAGE_OTHER = 1; // Simple Message sent by someone else
    int SIMPLE_MESSAGE_SELF = 2; // Simple Message sent by you

    int SIMPLE_MESSAGE_CONTINUED_OTHER = 4; // Simple message (without avatar) continued from previous message by someones else
    int SIMPLE_MESSAGE_CONTINUED_SELF = 5; // Simple message (without avatar) continued from previous mesage by you

    int ATTACHMENT_MESSAGE_OTHER = 6;
    int ATTACHMENT_MESSAGE_SELF = 7;

    int ATTACHMENT_MESSAGE_CONTINUED_OTHER = 6;
    int ATTACHMENT_MESSAGE_CONTINUED_SELF = 7;

    // Header/Footer Types
    int CONVERSATION_INFO_HEADER = 8;
    int TYPING_FOOTER = 9;

    // Info Types
    int SIMPLE_INFO = 10;
}
