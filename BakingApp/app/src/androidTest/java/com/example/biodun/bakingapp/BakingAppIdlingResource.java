package com.example.biodun.bakingapp;

import android.support.test.espresso.IdlingResource;

/**
 * Created by Biodun on 7/12/2017.
 */

public class BakingAppIdlingResource implements IdlingResource {
    private MainActivity activity;
    private ResourceCallback callback;

    public BakingAppIdlingResource(MainActivity activity){
        this.activity=activity;
    }
    @Override

    public String getName() {
        return "MainActivityIdleName";
    }

    @Override
    public boolean isIdleNow() {
        Boolean idle=isIdle();
        if(idle) callback.onTransitionToIdle();
        return idle;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback=callback;

    }
    public boolean isIdle(){
        return activity!=null && callback!=null && activity.IsSynced();
    }
}
