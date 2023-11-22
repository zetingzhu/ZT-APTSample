package com.common.lib.tint;

import android.util.AttributeSet;
import android.view.View;

import com.common.lib.tint.utils.TintManager;

abstract class AppCompatBaseHelper<T extends View> {
    protected T mView;
    protected TintManager mTintManager;

    private boolean mSkipNextApply;
    /**
     * 是否允许切换主题
     */
    public boolean openThemeSwitch = false;

    AppCompatBaseHelper(T view, TintManager tintManager) {
        mView = view;
        mTintManager = tintManager;
    }

    protected boolean skipNextApply() {
        if (mSkipNextApply) {
            mSkipNextApply = false;
            return true;
        }
        mSkipNextApply = true;
        return false;
    }

    protected void setSkipNextApply(boolean flag) {
        mSkipNextApply = flag;
    }

    abstract void loadFromAttribute(AttributeSet attrs, int defStyleAttr);

    public abstract void tint();

    public void setOpenThemeSwitch(boolean openThemeSwitch) {
        this.openThemeSwitch = openThemeSwitch;
    }
}
