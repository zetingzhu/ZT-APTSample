package com.common.lib.tint;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.common.lib.R;
import com.common.lib.tint.utils.TintManager;

public class TintImageView extends AppCompatImageView implements Tintable, AppCompatBackgroundHelper.BackgroundExtensible,
        AppCompatImageHelper.ImageExtensible {

    /**
     * 是否允许切换主题
     */
    private boolean openThemeSwitch = false;

    private AppCompatBackgroundHelper mBackgroundHelper;
    private AppCompatImageHelper mImageHelper;

    public TintImageView(Context context) {
        this(context, null);
    }

    public TintImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TintImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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

        mImageHelper = new AppCompatImageHelper(this, tintManager);
        mImageHelper.loadFromAttribute(attrs, defStyleAttr);
        mImageHelper.setOpenThemeSwitch(openThemeSwitch);
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

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        if (mImageHelper != null) {
            mImageHelper.setImageDrawable();
        }
    }

    @Override
    public void setImageResource(int resId) {
        if (mImageHelper != null) {
            mImageHelper.setImageResId(resId);
        } else {
            super.setImageResource(resId);
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
    public void setImageTintList(int resId) {
        if (mImageHelper != null) {
            mImageHelper.setImageTintList(resId, null);
        }
    }

    @Override
    public void setImageTintList(int resId, PorterDuff.Mode mode) {
        if (mImageHelper != null) {
            mImageHelper.setImageTintList(resId, mode);
        }
    }

    @Override
    public void tint() {
        if (mBackgroundHelper != null) {
            mBackgroundHelper.tint();
        }
        if (mImageHelper != null) {
            mImageHelper.tint();
        }
    }
}
