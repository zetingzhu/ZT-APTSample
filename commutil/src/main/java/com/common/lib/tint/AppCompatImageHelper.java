package com.common.lib.tint;

import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.common.lib.R;
import com.common.lib.tint.utils.DrawableUtils;
import com.common.lib.tint.utils.TintInfo;
import com.common.lib.tint.utils.TintManager;

class AppCompatImageHelper extends AppCompatBaseHelper<ImageView> {

    private TintInfo mImageTintInfo;
    private int mImageResId;
    private int mImageTintResId;

    AppCompatImageHelper(ImageView view, TintManager tintManager) {
        super(view, tintManager);
    }

    @SuppressWarnings("ResourceType")
    @Override
    void loadFromAttribute(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mView.getContext().obtainStyledAttributes(attrs, R.styleable.TintImageHelper, defStyleAttr, 0);
        // first resolve srcCompat due to not extending by AppCompatImageView
        if (mView.getDrawable() == null) {
            Drawable image = mTintManager.getDrawable(mImageResId = array.getResourceId(R.styleable.TintImageHelper_srcCompat, 0));
            if (image != null) {
                setImageDrawable(image);
            }
        }
        if (array.hasValue(R.styleable.TintImageHelper_tint)) {
            mImageTintResId = array.getResourceId(R.styleable.TintImageHelper_tint, 0);
            if (array.hasValue(R.styleable.TintImageHelper_imageTintMode)) {
                setSupportImageTintMode(DrawableUtils.parseTintMode(array.getInt(R.styleable.TintImageHelper_imageTintMode, 0), null));
            }
            setSupportImageTint(mImageTintResId);
        } else if (mImageResId == 0) {
            Drawable image = mTintManager.getDrawable(mImageResId = array.getResourceId(R.styleable.TintImageHelper_android_src, 0));
            if (image != null) {
                setImageDrawable(image);
            }
        }
        array.recycle();
    }

    /**
     * External use
     */
    public void setImageDrawable() {
        if (skipNextApply()) return;

        resetTintResource(0);
        setSkipNextApply(false);
    }

    public void setImageResId(int resId) {
        if (mImageResId != resId) {
            resetTintResource(resId);

            if (resId != 0) {
                Drawable image = mTintManager.getDrawable(resId);
                setImageDrawable(image != null ? image : ContextCompat.getDrawable(mView.getContext(), resId));
            }
        }
    }

    public void setImageTintList(int resId, PorterDuff.Mode mode) {
        if (mImageTintResId != resId) {
            mImageTintResId = resId;
            if (mImageTintInfo != null) {
                mImageTintInfo.mHasTintList = false;
                mImageTintInfo.mTintList = null;
            }
            setSupportImageTintMode(mode);
            setSupportImageTint(resId);
        }
    }

    /**
     * Internal use
     */
    private void setImageDrawable(Drawable drawable) {
        if (skipNextApply()) return;

        mView.setImageDrawable(drawable);
    }

    private boolean setSupportImageTint(int resId) {
        if (resId != 0) {
            if (mImageTintInfo == null) {
                mImageTintInfo = new TintInfo();
            }
            mImageTintInfo.mHasTintList = true;
            mImageTintInfo.mTintList = mTintManager.getColorStateList(resId);
        }
        return applySupportImageTint();
    }

    private void setSupportImageTintMode(PorterDuff.Mode mode) {
        if (mImageTintResId != 0 && mode != null) {
            if (mImageTintInfo == null) {
                mImageTintInfo = new TintInfo();
            }
            mImageTintInfo.mHasTintMode = true;
            mImageTintInfo.mTintMode = mode;
        }
    }

    private boolean applySupportImageTint() {
        Drawable image = mView.getDrawable();
        if (image != null && mImageTintInfo != null && mImageTintInfo.mHasTintList) {
            Drawable tintDrawable = image.mutate();
            tintDrawable = DrawableCompat.wrap(tintDrawable);
            if (mImageTintInfo.mHasTintList) {
                DrawableCompat.setTintList(tintDrawable, mImageTintInfo.mTintList);
            }
            if (mImageTintInfo.mHasTintMode) {
                DrawableCompat.setTintMode(tintDrawable, mImageTintInfo.mTintMode);
            }
            if (tintDrawable.isStateful()) {
                tintDrawable.setState(mView.getDrawableState());
            }
            setImageDrawable(tintDrawable);
            if (image == tintDrawable) {
                tintDrawable.invalidateSelf();
            }
            return true;
        }
        return false;
    }

    private void resetTintResource(int resId/*background resource id*/) {
        mImageResId = resId;
        mImageTintResId = 0;
        if (mImageTintInfo != null) {
            mImageTintInfo.mHasTintList = false;
            mImageTintInfo.mTintList = null;
            mImageTintInfo.mHasTintMode = false;
            mImageTintInfo.mTintMode = null;
        }
    }

    @Override
    public void tint() {
        if (mImageTintResId == 0 || !setSupportImageTint(mImageTintResId)) {
            if (openThemeSwitch) {
                Drawable drawable = mTintManager.getDrawable(mImageResId);
                if (drawable == null) {
                    drawable = mImageResId == 0 ? null : ContextCompat.getDrawable(mView.getContext(), mImageResId);
                }
                setImageDrawable(drawable);
            }
        }
    }

    public interface ImageExtensible {
        void setImageTintList(int resId);

        void setImageTintList(int resId, PorterDuff.Mode mode);
    }
}
