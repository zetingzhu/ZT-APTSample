package com.common.lib.language;

import android.content.Context;
import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;
import android.util.AttributeSet;

import com.common.lib.autofit.AutofitButton;


/**
 * Created by dufangzhu on 2017/7/17.
 * {@link AppTextView}
 */

public class AppButton extends AutofitButton implements LanguageView {

    private int textId;//文字id
    private int hintId;//hint的id
    private int arrResId, arrResIndex;

    //  app:fontStyle="light" medium
    public AppButton(Context context) {
        super(context);
        init(context, null);
    }

    public AppButton(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext, paramAttributeSet);
    }

    public AppButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init(paramContext, paramAttributeSet);
    }

    /**
     * 初始化获取xml的资源id
     *
     * @param context
     * @param attributeSet
     */
    private void init(Context context, AttributeSet attributeSet) {
        try {
            setTransformationMethod(null);
            if (attributeSet != null) {
                String textValue = attributeSet.getAttributeValue(ANDROIDXML, "text");
                if (!(textValue == null || textValue.length() < 2)
                        && textValue.contains("@")) {
                    //如果是 android:text="@string/testText"
                    //textValue会长这样 @156878785,去掉@号就是资源id
                    textId = Integer.parseInt(textValue.substring(1, textValue.length()));
                }
                String hintValue = attributeSet.getAttributeValue(ANDROIDXML, "hint");
                if (!(hintValue == null || hintValue.length() < 2)
                        && hintValue.contains("@")) {
                    hintId = Integer.parseInt(hintValue.substring(1, hintValue.length()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTextById(@StringRes int strId) {
        this.textId = strId;
        setText(strId);
    }

    @Override
    public void setTextWithString(String text) {
        this.textId = 0;
        setText(text);
    }

    @Override
    public void setTextByArrayAndIndex(@ArrayRes int arrId, @StringRes int arrIndex) {
        arrResId = arrId;
        arrResIndex = arrIndex;
        String[] strs = getContext().getResources().getStringArray(arrId);
        setText(strs[arrIndex]);
    }

    @Override
    public void reLoadLanguage() {
        try {
            if (textId > 0) {
                setText(textId);
            } else if (arrResId > 0) {
                String[] strs = getContext().getResources().getStringArray(arrResId);
                setText(strs[arrResIndex]);
            }

            if (hintId > 0) {
                setHint(hintId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
