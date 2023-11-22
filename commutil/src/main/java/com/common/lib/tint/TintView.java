package com.common.lib.tint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.common.lib.R;
import com.common.lib.tint.utils.TintManager;
import com.common.lib.util.Log;

public class TintView extends View implements Tintable, AppCompatBackgroundHelper.BackgroundExtensible {
    private AppCompatBackgroundHelper mBackgroundHelper;
    /**
     * 是否允许切换主题
     */
    private boolean openThemeSwitch = false;

    public TintView(Context context) {
        this(context, null);
    }

    public TintView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (isInEditMode()) {
            return;
        }
        TypedArray ta = context.obtainStyledAttributes(
                attrs,
                R.styleable.TintThemeSwitch,
                defStyleAttr,
                0);
        openThemeSwitch = ta.getBoolean(R.styleable.TintThemeSwitch_openThemeSwitch, openThemeSwitch);
        Log.e("openThemeSwitch", "openThemeSwitch===" + openThemeSwitch);
        ta.recycle();
        TintManager tintManager = TintManager.get(context);

        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);
        mBackgroundHelper.setOpenThemeSwitch(openThemeSwitch);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        super.setBackgroundDrawable(background);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundDrawableExternal(background);
        }
    }

    @Override
    public void setBackgroundResource(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundResId(resId);
        } else {
            super.setBackgroundResource(resId);
        }
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundColor(color);
        }
    }

    @Override
    public void setBackgroundTintList(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, null);
        }
    }

    @Override
    public void setBackgroundTintList(int resId, PorterDuff.Mode mode) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, mode);
        }
    }

    @Override
    public void tint() {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
    }
}
