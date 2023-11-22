package com.common.lib.tint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.common.lib.R;
import com.common.lib.tint.utils.TintManager;

public class TintLinearLayout extends LinearLayout implements Tintable {

    private AppCompatBackgroundHelper mBackgroundHelper;
    /**
     * 是否允许切换主题
     */
    private boolean openThemeSwitch = false;

    public TintLinearLayout(Context context) {
        this(context, null);
    }

    public TintLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
        ta.recycle();
        TintManager tintManager = TintManager.get(context);

        mBackgroundHelper = new AppCompatBackgroundHelper(this, tintManager);
        mBackgroundHelper.loadFromAttribute(attrs, defStyleAttr);
        mBackgroundHelper.setOpenThemeSwitch(openThemeSwitch);
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (getBackground() != null) {
            invalidateDrawable(getBackground());
        }
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

    public void setBackgroundTintList(int resId) {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setBackgroundTintList(resId, null);
        }
    }

    @Override
    public void tint() {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
    }
}
