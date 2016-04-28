package com.ruigoncalo.marvin.ui.collections;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ruigoncalo.marvin.R;
import com.ruigoncalo.marvin.ui.ImageLoaderManager;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ruigoncalo on 28/04/16.
 */
public class ItemFragment extends Fragment {

    @Bind(R.id.image_collection_page) ImageView image;

    private String imageUrl;

    public static ItemFragment newInstance(String image){
        ItemFragment fragment = new ItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("image", image);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            imageUrl = args.getString("image");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.layout_collection_page, container, false);
        ButterKnife.bind(this, view);
        ImageLoaderManager.load(getContext(), imageUrl, image);
        return view;
    }


}
