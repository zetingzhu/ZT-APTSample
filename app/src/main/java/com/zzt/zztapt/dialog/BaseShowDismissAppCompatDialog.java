package com.zzt.zztapt.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;

import com.trade.utilcode.util.ScreenUtils;
import com.zzt.zztapt.R;
import com.zzt.zztapt.util.ActivityUtil;


/**
 * @author: zeting
 * @date: 2022/6/20
 * Unable to add window -- token android.os.BinderProxy@803d3d8 is not valid; is your activity running?
 * 继承这个，各种判断 show()和 dismiss() 方法报错的
 */
public class BaseShowDismissAppCompatDialog extends AppCompatDialog {
    public Context mContext;
    // 自定义宽度
    private boolean customDialogWidth = false;

    public BaseShowDismissAppCompatDialog(@NonNull Context context) {
        super(context);
        initContext(context);
    }

    public BaseShowDismissAppCompatDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initContext(context);
    }

    public BaseShowDismissAppCompatDialog(@NonNull Context context, int themeResId, Boolean customWidth) {
        super(context, themeResId);
        this.customDialogWidth = customWidth;
        initContext(context);
    }

    protected BaseShowDismissAppCompatDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initContext(context);
    }

    private void initContext(Context context) {
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        try {
//            // 去掉 AppCompatActivity 中 dialog 隐藏 title
//            supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        super.onCreate(savedInstanceState);
        if (customDialogWidth) {
            calcWindowWidth(getWindow());
        }
    }

    /**
     * 自定义弹框对话框宽度
     *
     * @param window
     */
    public void calcWindowWidth(Window window) {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams params = window.getAttributes();
        int screenWidth = ScreenUtils.getAppScreenWidth();
        int width = (int) getContext().getResources().getDimension(R.dimen.margin_365dp);
        if (width >= screenWidth * 0.9) {
            params.width = (int) (screenWidth * 0.75);
        } else {
            params.width = width;
        }
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
    }

    @Override
    public void show() {
        if (ActivityUtil.isActivityAlive(getContext())) {
            super.show();
        }
    }

    public void showMatchWidth(int gravity) {
        showMatchWidth(gravity, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void showMatchWidth(int gravity, int height) {
        if (ActivityUtil.isActivityAlive(getContext())) {
            super.show();
            Window w = getWindow();
            WindowManager.LayoutParams params = w.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = height;
            w.setGravity(gravity);
        }
    }

    public void showMatchHFixedWCenter() {
        showMatchHFixedW(Gravity.CENTER);
    }

    /**
     * 固定宽度，最大高度
     *
     * @param gravity
     */
    public void showMatchHFixedW(int gravity) {
        if (ActivityUtil.isActivityAlive(getContext())) {
            super.show();
            Window w = getWindow();
            WindowManager.LayoutParams params = w.getAttributes();

            int screenWidth = ScreenUtils.getAppScreenWidth();
            params.width = (int) (screenWidth * 0.85F);
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            w.setGravity(gravity);
        }
    }

    @Override
    public void dismiss() {
        if (ActivityUtil.isActivityAlive(getContext())) {
            super.dismiss();
        }
    }
}
