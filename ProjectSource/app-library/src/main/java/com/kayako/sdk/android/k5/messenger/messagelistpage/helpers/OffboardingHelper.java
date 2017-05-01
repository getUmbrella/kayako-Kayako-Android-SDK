package com.kayako.sdk.android.k5.messenger.messagelistpage.helpers;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.BotMessageListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputFeedbackListItem;
import com.kayako.sdk.android.k5.core.Kayako;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * All logic involving offboarding items that ask for feedback
 */
public class OffboardingHelper {

    public OffboardingHelper() {
    }

    private InputFeedbackListItem.RATING currentRating;

    // TODO: Retrieve via API if a rating has been assigned beforehand? Set the rating accordingly

    public List<BaseListItem> getOffboardingListItems(String lastAgentReplierName, final InputFeedbackListItem.OnSelectRatingListener onSelectRatingListener) {
        if (lastAgentReplierName == null) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        if (currentRating == null) {
            return getOffboardingItemsToSelectRating(lastAgentReplierName, onSelectRatingListener);
        } else {
            return getOffboardingItemsOnRatingSubmission(lastAgentReplierName, currentRating);
        }
    }

    private List<BaseListItem> getOffboardingItemsToSelectRating(String lastAgentReplierName, final InputFeedbackListItem.OnSelectRatingListener onSelectRatingListener) {
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackListItem(
                String.format(
                        Kayako.getApplicationContext().getString(R.string.ko__messenger_input_feedback_rating_instruction_message),
                        lastAgentReplierName),
                new InputFeedbackListItem.OnSelectRatingListener() {
                    @Override
                    public void onSelectRating(InputFeedbackListItem.RATING rating) {
                        currentRating = rating;
                        onSelectRatingListener.onSelectRating(rating);
                    }
                })
        );
        return listItems;
    }

    private List<BaseListItem> getOffboardingItemsOnRatingSubmission(String lastAgentReplierName, InputFeedbackListItem.RATING rating) {
        List<BaseListItem> listItems = new ArrayList<>();
        listItems.add(new InputFeedbackListItem(
                String.format(
                        Kayako.getApplicationContext().getString(R.string.ko__messenger_input_feedback_rating_instruction_message),
                        lastAgentReplierName),
                rating.name())
        );
        return listItems;
    }

}