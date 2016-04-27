package com.ruigoncalo.marvin.ui.collections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.ui.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ruigoncalo on 27/04/16.
 */
public class CollectionsActivity extends BaseActivity {

    @Bind(R.id.image_close) ImageView buttonClose;
    @Bind(R.id.pager) ViewPager viewPager;
    @Bind(R.id.text_collection_name) TextView textName;
    @Bind(R.id.text_page_number) TextView textPage;

    @Override
    protected void setupComponent(AppComponent appComponent) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collections);
        ButterKnife.bind(this);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ItemFragment.newInstance("");
        }

        @Override
        public int getCount() {
            return 5;
        }
    }
}
