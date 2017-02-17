package com.kayako.sdk.android.k5.common.adapter.conversationlist;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.loadmorelist.EndlessRecyclerViewScrollAdapter;
import com.kayako.sdk.android.k5.common.utils.DateTimeUtils;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.messenger.conversation.Conversation;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ConversationListAdapter extends EndlessRecyclerViewScrollAdapter {

    private OnClickConversationListener mListener;

    public ConversationListAdapter(List<BaseListItem> items, OnClickConversationListener listener) {
        super(items);
        mListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getItemType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case ConversationListType.CONVERSATION_LIST_ITEM:
                View viewItem = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ko__list_conversations, parent, false);
                return new ConversationItemViewHolder(viewItem);
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ConversationListType.CONVERSATION_LIST_ITEM:
                ConversationItemViewHolder conversationViewHolder = (ConversationItemViewHolder) viewHolder;
                ConversationListItem conversationListItem = (ConversationListItem) getData().get(position);

                Context context = Kayako.getApplicationContext();
                ImageUtils.setAvatarImage(context, conversationViewHolder.avatar, conversationListItem.avatarUrl);

                conversationViewHolder.name.setText(conversationListItem.name);
                conversationViewHolder.subject.setText(conversationListItem.subject);
                conversationViewHolder.time.setText(DateTimeUtils.formatShortDateTime(System.currentTimeMillis(), conversationListItem.timeInMilleseconds));

                conversationViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ConversationListItem item = (ConversationListItem) getData().get(viewHolder.getAdapterPosition());
                        mListener.onClickConversation(item.conversation);
                    }
                });

                conversationViewHolder.status.setText(conversationListItem.status);
                conversationViewHolder.status.setTextColor(getStatusColor(context, conversationListItem.statusColor));
            default:
                super.onBindViewHolder(viewHolder, position);
        }
    }

    public int getStatusColor(Context context, ConversationListItem.StatusColor statusColor) {
        switch (statusColor) {
            case BLUE:
                return ContextCompat.getColor(context, R.color.conversation_item_status_blue);
            case GRAY:
                return ContextCompat.getColor(context, R.color.conversation_item_status_gray);
            case GREEN:
                return ContextCompat.getColor(context, R.color.conversation_item_status_green);

            default:
            case YELLOW:
                return ContextCompat.getColor(context, R.color.conversation_item_status_yellow);

        }
    }

    public interface OnClickConversationListener {
        void onClickConversation(Conversation conversation);
    }

}