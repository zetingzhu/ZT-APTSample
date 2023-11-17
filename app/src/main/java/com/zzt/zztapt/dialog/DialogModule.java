package com.zzt.zztapt.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;


import java.lang.ref.WeakReference;

/**
 * 展示dialog的工具
 */
public class DialogModule extends BaseShowDismissAppCompatDialog {
    private static final String TAG = DialogModule.class.getSimpleName();
    final DialogController mAlert;

    public DialogModule(Context context) {
        this(context, 0);
    }

    public DialogModule(Context context, int theme) {
        super(context, theme);
        this.mAlert = new DialogController(getContext(), this, getWindow());
    }

    protected DialogModule(Context context, boolean cancelable, OnCancelListener cancelListener) {
        this(context, 0);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void show() {
        super.show();
        mAlert.startHandleDelay();
    }

    public static class Builder {
        private DialogController P;
        private DialogModule dialog;

        public Builder(@NonNull Context context) {
            this(context, 0);
        }

        public Builder(@NonNull Context context, @StyleRes int themeResId) {
            dialog = new DialogModule(context, themeResId);
            P = dialog.mAlert;
            P.mCancelable = false;
        }

        @NonNull
        public Context getContext() {
            return P.getContext();
        }

        /**
         * 设置背景颜色
         *
         * @param resid
         */
        public Builder setBackgroundResource(@DrawableRes int resid) {
            P.dialog_layout_resid = resid;
            return this;
        }

        /**
         * 设置又上角删除按钮
         *
         * @param deleteClick
         */
        public Builder setRightDeleteClick(Drawable delImg, OnDialogViewClickListener deleteClick) {
            P.delImg = delImg;
            P.deleteClick = deleteClick;
            return this;
        }

        /**
         * 设置又上角删除按钮
         *
         * @param delImg
         * @param left
         * @param top
         * @param right
         * @param bottom
         * @param deleteClick
         * @return
         */
        public Builder setRightDeleteClick(Drawable delImg, int left, int top, int right, int bottom, OnDialogViewClickListener deleteClick) {
            P.delImg = delImg;
            P.delImgLeft = left;
            P.delImgRight = right;
            P.delImgTop = top;
            P.delImgBottom = bottom;
            P.deleteClick = deleteClick;
            return this;
        }

        /**
         * 是否隐藏顶部默认间距
         *
         * @param boo
         * @return
         */
        public Builder isHideTopSpace(boolean boo) {
            P.isHideTopSpace = boo;
            return this;
        }

        /**
         * 设置弹框上的小帽子（v1.4.1）
         *
         * @param headImage
         */
        public Builder setHeadImage(Drawable headImage) {
            P.headImg = headImage;
            return this;
        }

        /**
         * 设置弹框主体下方的关闭按钮
         *
         * @param bottomCloseImage
         */
        public Builder setBottomCloseImage(Drawable bottomCloseImage, OnDialogViewClickListener closeClick) {
            P.closeBottomImg = bottomCloseImage;
            P.closeClick = closeClick;
            return this;
        }

        /**
         * 设置上线间距
         *
         * @param top
         * @param bottom
         * @return
         */
        public Builder setSpaceTopBottom(int top, int bottom) {
            P.spaceMarginTop = top;
            P.spaceMarginBottom = bottom;
            return this;
        }


        /**
         * 设置底部按钮与弹框底部的间距
         *
         * @param bottom
         * @return
         */
        public Builder setBtnSpaceBottom(int bottom) {
            P.spaceBtnBottom = bottom;
            return this;
        }

        /**
         * 设置第一个按钮和第二个按钮之间的距离
         *
         * @param mid
         * @return
         */
        public Builder setBtnSpaceMid(int mid) {
            P.spaceBtnMid = mid;
            return this;
        }


        /**
         * 设置左右间距
         *
         * @param left
         * @param right
         * @return
         */
        public Builder setSpaceLeftRight(int left, int right) {
            P.spaceMarginLeft = left;
            P.spaceMarginRight = right;
            return this;
        }

        /**
         * 设置左边按钮显示
         *
         * @param leftButton
         * @param leftButtonSize
         * @param leftButtonColor
         * @param leftBg
         * @param leftClick
         * @return
         */
        public Builder setLeftButton(@NonNull String leftButton, int leftButtonSize,
                                     @ColorInt int leftButtonColor, @ColorInt int leftBg, OnDialogViewClickListener leftClick) {
            P.mButtonLeftText = leftButton;
            P.leftClick = leftClick;
            P.leftButtonSize = leftButtonSize;
            P.leftButtonColor = leftButtonColor;
            P.leftBg = leftBg;
            return this;
        }

        /**
         * 设置右边按钮显示
         *
         * @param rightButton
         * @param rightButtonSize
         * @param rightButtonColor
         * @param rightBg
         * @param rightClick
         * @return
         */
        public Builder setRightButton(@NonNull String rightButton, int rightButtonSize,
                                      @ColorInt int rightButtonColor, @ColorInt int rightBg, OnDialogViewClickListener rightClick) {
            P.mButtonRightText = rightButton;
            P.rightClick = rightClick;
            P.rightButtonSize = rightButtonSize;
            P.rightButtonColor = rightButtonColor;
            P.rightBg = rightBg;
            return this;
        }

        /**
         * 设置底部按钮高度
         *
         * @param bottomHeight
         * @return
         */
        public Builder setBottomHeight(int bottomHeight) {
            P.bottomHeight = bottomHeight;
            return this;
        }

        /**
         * 设置新版的按钮 第一个
         */
        public Builder setFirstButton(@NonNull String firstButton, int firstButtonSize,
                                      @ColorInt int firstButtonColor, @ColorInt int firstButtonBg, OnDialogViewClickListener firstButtonClick) {
            P.mButtonFirstText = firstButton;
            P.firstClick = firstButtonClick;
            P.firstButtonSize = firstButtonSize;
            P.firstButtonColor = firstButtonColor;
            P.firstBg = firstButtonBg;
            return this;
        }

        /**
         * 设置新版的按钮 第一个
         */
        public Builder setFirstButton(@NonNull String firstButton, int firstButtonSize,
                                      @ColorInt int firstButtonColor, Drawable firstBgDrawable, OnDialogViewClickListener firstButtonClick) {
            P.mButtonFirstText = firstButton;
            P.firstClick = firstButtonClick;
            P.firstButtonSize = firstButtonSize;
            P.firstButtonColor = firstButtonColor;
            P.firstBgDrawable = firstBgDrawable;
            return this;
        }

        /**
         * 设置第一个按钮间距
         */
        public Builder setFirstButtonMar(int left, int top, int right, int bottom) {
            P.firstBtnLeft = left;
            P.firstBtnTop = top;
            P.firstBtnRight = right;
            P.firstBtnBottom = bottom;
            return this;
        }

        /**
         * 设置新版的按钮 第二个
         */
        public Builder setSecondButton(@NonNull String secondButton, int secondButtonSize,
                                       @ColorInt int secondButtonColor, @ColorInt int secondButtonBg, OnDialogViewClickListener secondButtonClick) {
            P.mButtonSecondText = secondButton;
            P.secondClick = secondButtonClick;
            P.secondButtonSize = secondButtonSize;
            P.secondButtonColor = secondButtonColor;
            P.secondtBg = secondButtonBg;
            return this;
        }


        /**
         * 设置底部按钮样式，只有一个按钮 单一
         *
         * @param rightButton      按钮文本
         * @param rightButtonSize  按钮文本大小
         * @param rightButtonColor 按钮文本颜色
         * @param rightBg          按钮背景色
         * @param radius           按钮圆角弧度
         * @param rightClick       按钮点击事件
         * @return
         */
        public Builder setBottomButton(@NonNull String rightButton, int rightButtonSize,
                                       @ColorInt int rightButtonColor, @ColorInt int rightBg, int radius, OnDialogViewClickListener rightClick) {
            P.mButtonRightText = rightButton;
            P.rightClick = rightClick;
            P.rightButtonSize = rightButtonSize;
            P.rightButtonColor = rightButtonColor;
            P.rightBg = rightBg;
            P.cornerRadius = radius;
            P.isBottomSingleBtn = true;
            return this;
        }


        /**
         * 设置按钮的圆角半径
         *
         * @param radius
         * @return
         */
        public Builder setCornerRadius(int radius) {
            P.cornerRadius = radius;
            return this;
        }

        /**
         * 设置底部按钮的边距
         *
         * @param left
         * @param top
         * @param right
         * @param bottom
         * @return
         */
        public Builder setBottomButtonMar(int left, int top, int right, int bottom) {
            P.bottomMarginLeft = left;
            P.bottomMarginTop = top;
            P.bottomMarginRight = right;
            P.bottomMarginBottom = bottom;
            return this;
        }

        /**
         * 设置底部按钮的边距 ，在此还默认按钮为居中显示
         *
         * @param top
         * @param bottom
         * @return
         */
        public Builder setBottomButtonMar(int top, int bottom) {
            P.bottomMarginTop = top;
            P.bottomMarginBottom = bottom;
            P.isGravityCenter = true;
            return this;
        }

        /**
         * 设置可以取消
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }

        /**
         * 设置取消监听
         *
         * @param onCancelListener
         * @return
         */
        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }

        /**
         * 消失监听
         *
         * @param onDismissListener
         * @return
         */
        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }

        /**
         * 添加文本
         *
         * @param textContent
         * @param textSize
         * @param textColor
         * @param getViewListener
         * @return
         */
        public Builder appendTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, GetViewListener getViewListener) {
            P.appendWTextView(textContent, textSize, textColor, getViewListener);
            return this;
        }

        public Builder appendTextView(@NonNull String textContent, int textSize, @ColorInt int textColor) {
            P.appendWTextView(textContent, textSize, textColor, null);
            return this;
        }

        public Builder appendTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, int top, int bottom) {
            P.appendWTextView(textContent, textSize, textColor, Gravity.CENTER, 0, top, 0, bottom, null);
            return this;
        }

        public Builder appendTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, int left, int top, int right, int bottom) {
            P.appendWTextView(textContent, textSize, textColor, Gravity.CENTER, left, top, right, bottom, null);
            return this;
        }

        public Builder appendTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, int gravity, int left, int top, int right, int bottom) {
            P.appendWTextView(textContent, textSize, textColor, gravity, left, top, right, bottom, null);
            return this;
        }

        public Builder appendTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, int top, int bottom, GetViewListener<WTextView> getViewListener) {
            P.appendWTextView(textContent, textSize, textColor, Gravity.CENTER, 0, top, 0, bottom, getViewListener);
            return this;
        }

        public Builder appendTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, int left, int top, int right, int bottom, GetViewListener<WTextView> getViewListener) {
            P.appendWTextView(textContent, textSize, textColor, Gravity.CENTER, left, top, right, bottom, getViewListener);
            return this;
        }

        public Builder appendTextView(@NonNull String textContent, int textSize, @ColorInt int textColor, int gravity, int left, int top, int right, int bottom, GetViewListener<WTextView> getViewListener) {
            P.appendWTextView(textContent, textSize, textColor, gravity, left, top, right, bottom, getViewListener);
            return this;
        }

        /**
         * 添加图片
         *
         * @param img
         * @param getViewListener
         * @return
         */
        public Builder appendImageView(Drawable img, GetViewListener getViewListener) {
            P.appendWImageView(img, getViewListener);
            return this;
        }

        public Builder appendImageView(Drawable img) {
            P.appendWImageView(img, null);
            return this;
        }

        public Builder appendImageView(@NonNull String imgUrl, GetViewListener<WImageView> getViewListener) {
            P.appendWImageView(imgUrl, null);
            return this;
        }

        public Builder appendImageView(@NonNull String imgUrl, int left, int top, int right, int bottom, GetViewListener<WImageView> getViewListener) {
            P.appendWImageView(imgUrl, ImageView.ScaleType.FIT_CENTER, Gravity.CENTER, left, top, right, bottom, getViewListener);
            return this;
        }

        public Builder appendImageView(Drawable img, int top, int bottom) {
            P.appendWImageView(img, ImageView.ScaleType.FIT_CENTER, Gravity.CENTER, 0, top, 0, bottom, null);
            return this;
        }

        public Builder appendImageView(Drawable img, int top, int bottom, GetViewListener<WImageView> getViewListener) {
            P.appendWImageView(img, ImageView.ScaleType.FIT_CENTER, Gravity.CENTER, 0, top, 0, bottom, getViewListener);
            return this;
        }

        /**
         * 添加一个View
         *
         * @param view
         * @return
         */
        public Builder appendView(@NonNull WFrameLayout view) {
            P.appendView(view);
            return this;
        }

        public Builder appendView(@NonNull WFrameLayout view, int layoutGravity, int left, int top, int right, int bottom) {
            P.appendView(view, layoutGravity, left, top, right, bottom);
            return this;
        }

        public Builder appendView(@NonNull WFrameLayout view, int layoutGravity, int left, int top, int right, int bottom, OnDialogViewClickListener onListener) {
            P.appendView(view, layoutGravity, left, top, right, bottom, onListener);
            return this;
        }


        /**
         * 添加一个线
         *
         * @param height
         * @param left
         * @param top
         * @param right
         * @param bottom
         * @return
         */
        public Builder appendLine(@ColorInt int lineBg, int height, int left, int top, int right, int bottom) {
            P.appendLine(lineBg, height, left, top, right, bottom);
            return this;
        }


        /**
         * 添加一个View
         *
         * @param addSysViewListener
         * @return
         */
        public Builder appendAddView(AddSysViewListener addSysViewListener) {
            P.appendAddView(addSysViewListener.getAddView(dialog), null);
            return this;
        }

        public Builder appendAddView(AddSysViewListener addSysViewListener, GetViewListener<View> getViewListener) {
            P.appendAddView(addSysViewListener.getAddView(dialog), getViewListener);
            return this;
        }

        public Builder appendAddView(AddSysViewListener addSysViewListener, int left, int top, int right, int bottom) {
            P.appendAddView(addSysViewListener.getAddView(dialog), left, top, right, bottom);
            return this;
        }

        /**
         * 设置对话框延迟消失时间
         *
         * @param delayMillis
         * @return
         */
        public Builder setDialogDelayMillis(long delayMillis) {
            P.dialogDelayMillis = delayMillis;
            return this;
        }


        @NonNull
        public DialogModule create() {
            P.installContent();
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            } else {
                dialog.setCanceledOnTouchOutside(false);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            return dialog;
        }


        /**
         * 添加一个方法，拿到绘制的view
         */
        public View getContentView() {
            return P.getContentView();
        }

        @NonNull
        public DialogModule show() {
            create();
            dialog.show();
            return dialog;
        }
    }

    /**
     * 添加一个系统 View 比如添加一个 Edittext ,
     *
     * @param <T>
     */
    public interface AddSysViewListener<T extends View> {
        // 获取View
        T getAddView(DialogInterface dialog);
    }

    /**
     * 点击View事件监听
     */
    public interface OnDialogViewClickListener {
        void onClick(DialogInterface dialog, View v);
    }

    /**
     * 获得当前View的监听
     *
     * @param <T>
     */
    public interface GetViewListener<T extends View> {
        void getDialogView(T view);
    }


    /**
     * 设置自定义view 的dialog持有数据
     */
    public interface WDialogInterface {
        void setDialog(DialogModule wDialog);

        DialogModule getWDialog();

        void WDismiss();
    }


    /**
     * dialog 中的文本
     */
    public static class WTextView extends AppCompatTextView implements WDialogInterface {
        private Context mContext;
        // 设置dialog
        protected WeakReference<DialogModule> mTextViewDialog;

        public WTextView(Context context) {
            super(context);
            initView(context);
        }

        public WTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView(context);
        }

        public WTextView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView(context);
        }


        private void initView(Context context) {
            this.mContext = context;
        }

        @Override
        public void setText(CharSequence text, BufferType type) {
            super.setText(text, type);
            Log.d(TAG, "设置的文本：" + text);
            // TODO 这里处理如果是url 需要进行富文本操作
        }

        @Override
        public void setDialog(DialogModule wDialog) {
            this.mTextViewDialog = new WeakReference<>(wDialog);
        }

        @Override
        public DialogModule getWDialog() {
            if (mTextViewDialog != null) {
                return mTextViewDialog.get();
            }
            return null;
        }

        @Override
        public void WDismiss() {
            if (getWDialog() != null) {
                getWDialog().dismiss();
            }
        }

    }

    /**
     * dialog中的图片布局
     */
    public static class WImageView extends AppCompatImageView implements WDialogInterface {
        private Context mContext;
        // 设置dialog
        protected WeakReference<DialogModule> mImageViewDialog;

        public WImageView(Context context) {
            super(context);
            initView(context);
        }

        public WImageView(Context context, AttributeSet attrs) {
            super(context, attrs);
            initView(context);
        }

        public WImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView(context);
        }

        private void initView(Context context) {
            this.mContext = context;
        }

        @Override
        public void setImageDrawable(@Nullable Drawable drawable) {
            super.setImageDrawable(drawable);
        }

        public void setImageUrl(String url) {

        }

        @Override
        public void setDialog(DialogModule wDialog) {
            this.mImageViewDialog = new WeakReference<>(wDialog);
        }

        @Override
        public DialogModule getWDialog() {
            if (mImageViewDialog != null) {
                return mImageViewDialog.get();
            }
            return null;
        }

        @Override
        public void WDismiss() {
            if (getWDialog() != null) {
                getWDialog().dismiss();
            }
        }
    }

    /**
     * 布局中添加的View
     */
    public static class WFrameLayout extends FrameLayout implements WDialogInterface {
        // 设置监听
        protected OnDialogViewClickListener onDialogViewClickListener;
        private Context mContext;
        // 设置dialog
        protected WeakReference<DialogModule> mFrameLayoutDialog;

        public WFrameLayout(@NonNull Context context) {
            super(context);
            initView(context);
        }

        public WFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
            initView(context);
        }

        public WFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            initView(context);
        }

        private void initView(Context context) {
            this.mContext = context;
        }


        public void setOnDialogViewClickListener(OnDialogViewClickListener onDialogViewClickListener) {
            this.onDialogViewClickListener = onDialogViewClickListener;
        }

        @Override
        public void setDialog(DialogModule wDialog) {
            this.mFrameLayoutDialog = new WeakReference<>(wDialog);
        }

        @Override
        public DialogModule getWDialog() {
            if (mFrameLayoutDialog != null) {
                return mFrameLayoutDialog.get();
            }
            return null;
        }

        @Override
        public void WDismiss() {
            if (getWDialog() != null) {
                getWDialog().dismiss();
            }
        }

    }

}
