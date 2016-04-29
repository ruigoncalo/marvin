package com.ruigoncalo.marvin.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Font: https://github.com/nickbutcher/plaid/blob/master/app/src/main/java/io/plaidapp/ui/recyclerview/InfiniteScrollListener.java
 *
 * Created by ruigoncalo on 30/04/16.
 */
public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {

    // The minimum number of items remaining before we should loading more.
    private static final int VISIBLE_THRESHOLD = 5;

    private final LinearLayoutManager layoutManager;
    private final DataLoadingSubject dataLoading;

    public InfiniteScrollListener(LinearLayoutManager layoutManager, DataLoadingSubject dataLoading) {
        this.layoutManager = layoutManager;
        this.dataLoading = dataLoading;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // bail out if scrolling upward or already loading data
        if (dy < 0 || dataLoading.isDataLoading()) return;

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = layoutManager.getItemCount();
        final int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
