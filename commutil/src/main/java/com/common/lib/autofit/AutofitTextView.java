package com.common.lib.autofit;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.common.lib.tint.TintTextView;

/**
 * A {@link TextView} that re-sizes its text to be no larger than the width of the view.
 *
 * @attr ref R.styleable.AutofitTextView_sizeToFit
 * @attr ref R.styleable.AutofitTextView_minTextSize
 * @attr ref R.styleable.AutofitTextView_precision
 *
 * 1、使用了单行属性才有效果。android:maxLines="1" 超过宽度就会自动改变字体大小，适配宽度不换行
 * 2、TextView的宽度必须 在布局中已经固定才有效果，使用wrap_content会默认使用使用最小字体
 *
 */
public class AutofitTextView extends TintTextView implements AutofitHelper.OnTextSizeChangeListener {

    private AutofitHelper mHelper;

    public AutofitTextView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AutofitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public AutofitTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mHelper = AutofitHelper.create(this, attrs, defStyle)
                .addOnTextSizeChangeListener(this);
    }

    // Getters and Setters

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        if (mHelper != null) {
            mHelper.setTextSize(unit, size);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLines(int lines) {
        super.setLines(lines);
        if (mHelper != null) {
            mHelper.setMaxLines(lines);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        if (mHelper != null) {
            mHelper.setMaxLines(maxLines);
        }
    }

    /**
     * Returns the {@link AutofitHelper} for this View.
     */
    public AutofitHelper getAutofitHelper() {
        return mHelper;
    }

    /**
     * Returns whether or not the text will be automatically re-sized to fit its constraints.
     */
    public boolean isSizeToFit() {
        return mHelper.isEnabled();
    }

    /**
     * Sets the property of this field (sizeToFit), to automatically resize the text to fit its
     * constraints.
     */
    public void setSizeToFit() {
        setSizeToFit(true);
    }

    /**
     * If true, the text will automatically be re-sized to fit its constraints; if false, it will
     * act like a normal TextView.
     *
     * @param sizeToFit
     */
    public void setSizeToFit(boolean sizeToFit) {
        mHelper.setEnabled(sizeToFit);
    }

    /**
     * Returns the maximum size (in pixels) of the text in this View.
     */
    public float getMaxTextSize() {
        return mHelper.getMaxTextSize();
    }

    /**
     * Set the maximum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param size The scaled pixel size.
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    public void setMaxTextSize(float size) {
        mHelper.setMaxTextSize(size);
    }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     *
     * @attr ref android.R.styleable#TextView_textSize
     */
    public void setMaxTextSize(int unit, float size) {
        mHelper.setMaxTextSize(unit, size);
    }

    /**
     * Returns the minimum size (in pixels) of the text in this View.
     */
    public float getMinTextSize() {
        return mHelper.getMinTextSize();
    }

    /**
     * Set the minimum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param minSize The scaled pixel size.
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public void setMinTextSize(int minSize) {
        mHelper.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, minSize);
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param minSize The desired size in the given units.
     *
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public void setMinTextSize(int unit, float minSize) {
        mHelper.setMinTextSize(unit, minSize);
    }

    /**
     * Returns the amount of precision used to calculate the correct text size to fit within its
     * bounds.
     */
    public float getPrecision() {
        return mHelper.getPrecision();
    }

    /**
     * Set the amount of precision used to calculate the correct text size to fit within its
     * bounds. Lower precision is more precise and takes more time.
     *
     * @param precision The amount of precision.
     */
    public void setPrecision(float precision) {
        mHelper.setPrecision(precision);
    }

    @Override
    public void onTextSizeChange(float textSize, float oldTextSize) {
        // do nothing
    }
}
