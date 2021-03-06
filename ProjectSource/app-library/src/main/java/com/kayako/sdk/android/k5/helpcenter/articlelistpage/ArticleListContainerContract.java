package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import com.kayako.sdk.android.k5.common.mvp.BasePresenter;
import com.kayako.sdk.android.k5.common.mvp.BaseView;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public interface ArticleListContainerContract {

    interface View extends BaseView {
        void openSearchPage();
    }

    interface Presenter extends BasePresenter<ArticleListContainerContract.View> {
        void clickSearchAction();
    }

}
