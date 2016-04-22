package com.ruigoncalo.marvin;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

/**
 * Created by ruigoncalo on 21/04/16.
 */
public class App extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        setupGraph();
        timber();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public AppComponent component() {
        return appComponent;
    }

    private void setupGraph(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        appComponent.inject(this);
    }

    private void timber(){
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }
}
