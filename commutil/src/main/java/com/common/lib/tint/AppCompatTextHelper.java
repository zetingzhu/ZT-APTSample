package com.common.lib.tint;

import android.content.res.ColorStateList;
import android.content.res.TypedArray;

import androidx.annotation.ColorRes;

import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.common.lib.R;
import com.common.lib.tint.utils.TintInfo;
import com.common.lib.tint.utils.TintManager;

class AppCompatTextHelper extends AppCompatBaseHelper<TextView> {

    //If writing like this:
    //int[] ATTRS = { R.attr.tintText, android.R.attr.textColor, android.R.attr.textColorLink, ...};
    //we can't get textColor value when api is below 20;

    private int mTextColorId;
    private int mTextLinkColorId;

    private TintInfo mTextColorTintInfo;
    private TintInfo mTextLinkColorTintInfo;

    AppCompatTextHelper(TextView view, TintManager tintManager) {
        super(view, tintManager);
    }

    @SuppressWarnings("ResourceType")
    @Override
    void loadFromAttribute(AttributeSet attrs, int defStyleAttr) {
        TypedArray array = mView.getContext().obtainStyledAttributes(attrs, R.styleable.TintTextHelper, defStyleAttr, 0);

        String color = array.getString(R.styleable.TintTextHelper_android_textColor);
        int textColorId = array.getResourceId(R.styleable.TintTextHelper_android_textColor, 0);
        if (textColorId == 0 && TextUtils.isEmpty(color)) {
            setTextAppearanceForTextColor(array.getResourceId(R.styleable.TintTextHelper_android_textAppearance, 0), false);
        } else if (textColorId != 0) {
            setTextColor(textColorId);
        }

        if (array.hasValue(R.styleable.TintTextHelper_android_textColorLink)) {
            setLinkTextColor(array.getResourceId(R.styleable.TintTextHelper_android_textColorLink, 0));
        }
        array.recycle();
    }


    /**
     * External use
     */
    public void setTextColor() {
        if (skipNextApply()) return;

        resetTextColorTintResource(0);
        setSkipNextApply(false);
    }

    /**
     * useless for setLinkTextColor is final
     */
    @Deprecated
    public void setTextLinkColor() {
        if (skipNextApply()) return;

        resetTextLinkColorTintResource(0);
        setSkipNextApply(false);
    }

    public void setTextAppearanceForTextColor(int resId) {
        resetTextColorTintResource(0);
        setTextAppearanceForTextColor(resId, true);
    }

    public void setTextAppearanceForTextColor(int resId, boolean isForced) {
        boolean isTextColorForced = isForced || mTextColorId == 0;
        TypedArray appearance = mView.getContext().obtainStyledAttributes(resId, R.styleable.TextAppearance);
        if (appearance.hasValue(R.styleable.TextAppearance_android_textColor) && isTextColorForced) {
            setTextColor(appearance.getResourceId(R.styleable.TextAppearance_android_textColor, 0));
        }
        appearance.recycle();
    }

    public void setTextColorById(@ColorRes int colorId) {
        setTextColor(colorId);
    }

    /**
     * Internal use
     */
    private void setTextColor(ColorStateList tint) {
        if (skipNextApply()) return;

        mView.setTextColor(tint);
    }

    private void setTextColor(@ColorRes int resId) {
        if (mTextColorId != resId) {
            resetTextColorTintResource(resId);

            if (resId != 0) {
                setSupportTextColorTint(resId);
            }
        }
    }

    private void setLinkTextColor(@ColorRes int resId) {
        if (mTextLinkColorId != resId) {
            resetTextLinkColorTintResource(resId);

            if (resId != 0) {
                setSupportTextLinkColorTint(resId);
            }
        }
    }

    private void setSupportTextColorTint(int resId) {
        if (resId != 0) {
            if (mTextColorTintInfo == null) {
                mTextColorTintInfo = new TintInfo();
            }
            mTextColorTintInfo.mHasTintList = true;
            mTextColorTintInfo.mTintList = mTintManager.getColorStateList(resId);
        }
        applySupportTextColorTint();
    }

    private void setSupportTextLinkColorTint(int resId) {
        if (resId != 0) {
            if (mTextLinkColorTintInfo == null) {
                mTextLinkColorTintInfo = new TintInfo();
            }
            mTextLinkColorTintInfo.mHasTintList = true;
            mTextLinkColorTintInfo.mTintList = mTintManager.getColorStateList(resId);
        }
        applySupportTextLinkColorTint();
    }

    private void applySupportTextColorTint() {
        if (mTextColorTintInfo != null && mTextColorTintInfo.mHasTintList) {
            setTextColor(mTextColorTintInfo.mTintList);
        }
    }

    private void applySupportTextLinkColorTint() {
        if (mTextLinkColorTintInfo != null && mTextLinkColorTintInfo.mHasTintList) {
            mView.setLinkTextColor(mTextLinkColorTintInfo.mTintList);
        }
    }

    private void resetTextColorTintResource(@ColorRes int resId/*text resource id*/) {
        mTextColorId = resId;
        if (mTextColorTintInfo != null) {
            mTextColorTintInfo.mHasTintList = false;
            mTextColorTintInfo.mTintList = null;
        }
    }

    private void resetTextLinkColorTintResource(@ColorRes int resId/*text resource id*/) {
        mTextLinkColorId = resId;
        if (mTextLinkColorTintInfo != null) {
            mTextLinkColorTintInfo.mHasTintList = false;
            mTextLinkColorTintInfo.mTintList = null;
        }
    }

    @Override
    public void tint() {
        if (openThemeSwitch) {
            if (mTextColorId != 0) {
                setSupportTextColorTint(mTextColorId);
            }
            if (mTextLinkColorId != 0) {
                setSupportTextLinkColorTint(mTextLinkColorId);
            }
        }
    }

    public interface TextExtensible {
        void setTextColorById(@ColorRes int colorId);
    }
}
