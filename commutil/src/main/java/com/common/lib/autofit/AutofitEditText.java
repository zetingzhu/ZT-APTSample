package com.common.lib.autofit;

import android.content.Context;
import androidx.appcompat.widget.AppCompatEditText;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.common.lib.tint.TintEditText;

/**
 * A {@link TextView} that re-sizes its text to be no larger than the width of the view.
 *
 * @attr ref R.styleable.AutofitTextView_sizeToFit
 * @attr ref R.styleable.AutofitTextView_minTextSize
 * @attr ref R.styleable.AutofitTextView_precision
 */
public class AutofitEditText extends TintEditText implements AutofitHelper.OnTextSizeChangeListener {
    /*默认的绝对字体大小*/
    public static final int DFT_FONT_SIZE = 12;
    private AutofitHelper mHelper;

    public AutofitEditText(Context context) {
        super(context);
        init(context, null, 0);
    }

    public AutofitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public AutofitEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        mHelper = AutofitHelper.create(this, attrs, defStyle)
                .addOnTextSizeChangeListener(this);

        if (isSizeToFit()) {
            addOnLayoutChangeListener(new AutofitOnLayoutChangeListener());
        }
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
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    public void setMinTextSize(int minSize) {
        mHelper.setMinTextSize(TypedValue.COMPLEX_UNIT_SP, minSize);
    }

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit    The desired dimension unit.
     * @param minSize The desired size in the given units.
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


    /**
     * 单独设置EditText控件中hint文本的字体大小，可能与EditText文字大小不同
     *
     * @param hintText hint的文本内容
     * @param textSize hint的文本的文字大小（以px为单位）
     */
    public void setHintTextSize(String hintText, float textSize) {
        // 新建一个可以添加属性的文本对象
        SpannableString ss = new SpannableString(hintText);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) textSize, false);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        // 设置hint
        setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }


    private class AutofitOnLayoutChangeListener implements OnLayoutChangeListener {
        @Override
        public void onLayoutChange(View view, int left, int top, int right, int bottom,
                                   int oldLeft, int oldTop, int oldRight, int oldBottom) {
            autofitHint();

        }
    }

    /**
     * 对于需要处理hint的EditText 得到不换行的适配textsize
     *
     * @param size
     * @return
     */
    public float getAutofitHintTextSize(float size) {
        if (getHint() == null
                || getHint().length() == 0)
            return size;
        if (getHint().length() == 0)
            return size;
        TextPaint paint = new TextPaint();
        paint.set(getPaint());//不能少了 已经设置好的Paint，否则算出的宽度不准
        paint.setTextSize(size);
        int w = (int) getWidth() - getPaddingLeft() - getPaddingRight();
//                StaticLayout layout = new StaticLayout(ed_upwd.getHint(), paint, w, Layout.Alignment.ALIGN_NORMAL,
//                        1.0f, 0.0f, true);
//                int lineCount = layout.getLineCount();
//                if (lineCount > 1) {
//                    size--;
//                    return getMySize(size);
//                }
        if (paint.measureText(getHint().toString()) > w) {
            size--;//循环获取，这里最小单位是按照1来递减
            return getAutofitHintTextSize(size);
        }
        return size;
    }

    /**
     * 适配hint
     */
    void autofitHint() {
        if (getHint() == null
                || getHint().length() == 0)
            return;
        setHintTextSize(getHint().toString(), getAutofitHintTextSize(getTextSize()));
    }

    /**
     * 手动调用适应宽度代码
     */
    public void doFitWidth() {
        mHelper.doFitWidth();
    }
}
