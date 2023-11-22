package com.common.lib.tint;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.appcompat.widget.AppCompatTextView;

import android.util.AttributeSet;

import com.common.lib.R;
import com.common.lib.tint.utils.TintManager;
import com.common.lib.util.Log;

public class TintTextView extends AppCompatTextView implements Tintable, AppCompatTextHelper.TextExtensible, AppCompatBackgroundHelper.BackgroundExtensible {

    private AppCompatTextHelper mTextHelper;
    private AppCompatBackgroundHelper mBackgroundHelper;
    /**
     * 是否允许切换主题
     */
    private boolean openThemeSwitch = false;

    public TintTextView(Context context) {
        this(context, null);
    }

    public TintTextView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public TintTextView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mTextHelper = new AppCompatTextHelper(this, tintManager);
        mTextHelper.loadFromAttribute(attrs, defStyleAttr);
        mTextHelper.setOpenThemeSwitch(openThemeSwitch);

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
    public void setTextColor(int color) {
        super.setTextColor(color);
        if (mTextHelper != null) {
            mTextHelper.setTextColor();
        }
    }

    @Override
    public void setTextColor(ColorStateList colors) {
        super.setTextColor(colors);
        if (mTextHelper != null) {
            mTextHelper.setTextColor();
        }
    }

    @Override
    public void setTextColorById(int colorId) {
        if (mTextHelper != null) {
            mTextHelper.setTextColorById(colorId);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void setTextAppearance(int resId) {
        super.setTextAppearance(resId);
        if (mTextHelper != null) {
            mTextHelper.setTextAppearanceForTextColor(resId);
        }
    }

    @Override
    public void setTextAppearance(Context context, int resId) {
        super.setTextAppearance(context, resId);
        if (mTextHelper != null) {
            mTextHelper.setTextAppearanceForTextColor(resId);
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
        if (mTextHelper != null) {
            mTextHelper.tint();
        }
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
    }

    public void setOpenThemeSwitch(boolean openThemeSwitch) {
        this.openThemeSwitch = openThemeSwitch;
        if (mTextHelper != null) {
            mTextHelper.setOpenThemeSwitch(openThemeSwitch);
        }
        if (mBackgroundHelper != null) {
            mBackgroundHelper.setOpenThemeSwitch(openThemeSwitch);
        }
    }

    public boolean isOpenThemeSwitch() {
        return openThemeSwitch;
    }
}
