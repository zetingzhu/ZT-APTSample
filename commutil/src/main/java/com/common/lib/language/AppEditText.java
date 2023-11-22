package com.common.lib.language;

import android.content.Context;
import androidx.annotation.ArrayRes;
import androidx.annotation.StringRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import com.common.lib.autofit.AutofitEditText;

/**
 * Created by dufangzhu on 2017/7/17.
 * Android:imeOptions="flagNoExtractUi"
 * flagNoExtractUi  横屏情况下输入法不全屏幕
 * 1、如果xml中设置了 imeOptions需要加入flagNoExtractUi值
 * 2、或者在xml不设置Android:imeOptions 属性
 *
 * mark 更新用户名的地方：inputtype 的属性  maxLines影响到换行
 * 遇到葡萄牙语  不使用singleLine hint会自己换行
 */

public class AppEditText extends AutofitEditText implements LanguageView {
    private int textId;//文字id
    private int hintId;//hint的id
    private int arrResId, arrResIndex;

//    Android:imeOptions
    int imeOptions = -1;

    public AppEditText(Context context) {
        super(context);
        init(context, null);
    }

    public AppEditText(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init(paramContext, paramAttributeSet);
    }

    public AppEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
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
                imeOptions = attributeSet.getAttributeIntValue(ANDROIDXML,"imeOptions", -1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (imeOptions == -1)
            setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
        setOnFocusChangeListener(new FocusChangeDelegate(null));
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

    @Override
    public void setOnFocusChangeListener(View.OnFocusChangeListener l) {
        super.setOnFocusChangeListener(new FocusChangeDelegate(l));
    }

    private static class FocusChangeDelegate implements View.OnFocusChangeListener {
        View.OnFocusChangeListener listener;

        public FocusChangeDelegate(View.OnFocusChangeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            v.setSelected(hasFocus);
            if(v.getBackground() == null) {
                ViewGroup parent = (ViewGroup) v.getParent();
                parent.setSelected(hasFocus);
            }
            if(listener != null)
                listener.onFocusChange(v, hasFocus);
        }
    }
}
