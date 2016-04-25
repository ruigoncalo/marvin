package com.ruigoncalo.marvin.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ruigoncalo.marvin.App;
import com.ruigoncalo.marvin.AppComponent;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupComponent(App.get(this).component());
    }

    protected abstract void setupComponent(AppComponent appComponent);

}
