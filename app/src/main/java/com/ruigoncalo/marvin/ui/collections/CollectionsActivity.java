package com.ruigoncalo.marvin.ui.collections;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.ruigoncalo.marvin.AppComponent;
import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.ui.BaseActivity;
import com.ruigoncalo.marvin.utils.ZoomOutPageTransformer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ruigoncalo on 27/04/16.
 */
public class CollectionsActivity extends BaseActivity {

    public static final String EXTRA_LIST_TITLES = "extra-titles";
    public static final String EXTRA_LIST_IMAGES = "extra-images";
    public static final String EXTRA_POSITION = "extra-position";

    @Bind(R.id.pager) ViewPager viewPager;
    @Bind(R.id.text_collection_name) TextView textName;
    @Bind(R.id.text_page_number) TextView textPage;

    List<CharSequence> titles;
    List<CharSequence> images;
    int position;
    int totalPages;

    @Override
    protected void setupComponent(AppComponent appComponent) {
        // empty
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_collections);
        ButterKnife.bind(this);
        resolveIntent();
        setupAdapter();
        setTexts(position);
    }

    private void setupAdapter(){
        PagerAdapter pagerAdapter = new ItemsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTexts(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resolveIntent(){
        Intent intent = getIntent(); // @NonNull
        titles = intent.getCharSequenceArrayListExtra(EXTRA_LIST_TITLES);
        images = intent.getCharSequenceArrayListExtra(EXTRA_LIST_IMAGES);
        position = intent.getIntExtra(EXTRA_POSITION, -1);
        totalPages = titles.size();
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ItemsPagerAdapter extends FragmentStatePagerAdapter {
        public ItemsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ItemFragment.newInstance((String) images.get(position));
        }

        @Override
        public int getCount() {
            return totalPages;
        }
    }

    private void setTexts(int position){
        textName.setText(titles.get(position));
        String currentPage = (position + 1) + "/" + totalPages;
        textPage.setText(currentPage);
    }

    @OnClick(R.id.button_close)
    public void onButtonCloseClick(){
        finish();
    }
}
