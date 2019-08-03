package com.jaywhitsitt.eggsandbakey.testhelpers;

import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class RecipeFetchIdlingResource implements IdlingResource {

    private volatile ResourceCallback mCallback;
    private AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIsIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }

    public void setIdleState(boolean isIdle) {
        mIsIdleNow.set(isIdle);
        if (isIdle && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
    }
}
