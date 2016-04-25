package com.ruigoncalo.marvin.ui;

/**
 * Created by ruigoncalo on 24/04/16.
 */
public abstract class Presenter<P> {

    private P presentable;

    public void onStart(P presentable){
        this.presentable = presentable;
    }

    public void onStop(){
        this.presentable = null;
    }

    public P getPresentable() {
        return presentable;
    }
}
