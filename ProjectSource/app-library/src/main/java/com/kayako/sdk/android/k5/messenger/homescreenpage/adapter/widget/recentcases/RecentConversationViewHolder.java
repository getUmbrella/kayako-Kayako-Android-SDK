package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kayako.sdk.android.k5.R;

public class RecentConversationViewHolder extends RecyclerView.ViewHolder {

    public ImageView avatar;
    public TextView name;
    public TextView time;
    public TextView subject;
    public TextView unreadCounter;

    public ImageView typingLoader;
    public View subjectLine;

    public View itemView;
    public View separator;

    public RecentConversationViewHolder(View v) {
        super(v);
        itemView = v;
        avatar = (ImageView) v.findViewById(R.id.ko__avatar);
        name = (TextView) v.findViewById(R.id.ko__name);
        time = (TextView) v.findViewById(R.id.ko__time);
        subject = (TextView) v.findViewById(R.id.ko__subject);
        unreadCounter = (TextView) v.findViewById(R.id.ko__unread_counter);
        typingLoader = (ImageView) v.findViewById(R.id.ko__typing_progress_loader);
        subjectLine = v.findViewById(R.id.ko__subject_line);
        separator = v.findViewById(R.id.ko__recent_conversation_list_separator);
    }
}
