package com.kayako.sdk.android.k5.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.articlelistpage.ArticleListContainerFragment;
import com.kayako.sdk.android.k5.common.core.HelpCenterPref;
import com.kayako.sdk.android.k5.common.fragments.ActivityNavigationCallback;
import com.kayako.sdk.android.k5.sectionbycategorypage.SectionByCategoryContainerFragment;

public class KayakoHelpCenterActivity extends AppCompatActivity implements ActivityNavigationCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ko__activity_help_center);

        // TODO: Write steps for users to include this in their Application class
        // TODO: Temporarily using in Activity for testing.
        HelpCenterPref.createInstance(this);

        Fragment newFragment = SectionByCategoryContainerFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_listing, newFragment);
        transaction.commit();
    }

    @Override
    public void openNextPage(long id) {
        Fragment newFragment = ArticleListContainerFragment.newInstance(id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_listing, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    // TODO: Presenter-View #1: Search Section - hide and show menu options
}
