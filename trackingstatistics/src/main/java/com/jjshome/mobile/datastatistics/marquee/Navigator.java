package com.jjshome.mobile.datastatistics.marquee;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjshome.mobile.datastatistics.DSAgent;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class Navigator implements ActivityLifecycleCallbacks {

    private MarqueeBallView mMarqueeBallView;

    private static Object sCurActivity;

    private static DialogFragment dialogFragment;

    private boolean isShow = false;

    private static PopupWindow popupWindow;

    private static Dialog dialog;

    private static Navigator instance;

    private static AlertDialog alertDialog;

    private final List<View> mViews = new ArrayList<>();
    private WeakReference activityRefer = null;


    public static void initPopupWindow(PopupWindow pop) {
        popupWindow = pop;
    }

    public static void initAlertDialog(AlertDialog alert) {
        alertDialog = alert;
    }

    public static void initDialog(Dialog myDialog) {
        dialog = myDialog;
    }

    public static Navigator getInstance() {
        if (instance == null) {
            instance = new Navigator();
        }
        return instance;
    }

    public void showMarqueeBallView(Context context, String workerId, String token) {
        mMarqueeBallView = MarqueeBallView.getInstance(context);
        mMarqueeBallView.setWorkerId(workerId);
        mMarqueeBallView.setToken(token);
        if (mMarqueeBallView != null && !TextUtils.isEmpty(workerId)) {
            mMarqueeBallView.show(true);
            isShow = true;
        }
    }

    public void initMarqueeBallView() {
        mMarqueeBallView = null;
    }

    public static Activity getCurActivity() {
        return (Activity) sCurActivity;
    }

    public static DialogFragment getDialogFragment() {
        return dialogFragment;
    }

    public static PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public static Dialog getDialog() {
        return dialog;
    }

    public static AlertDialog getAlertDialog() {
        return alertDialog;
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        registerFragmentLifeCycle(activity);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        DSAgent.onResume(activity);
        sCurActivity = activity;
        /***无埋点**********/
        this.activityRefer = new WeakReference(activity);
        View decorView = activity.getWindow().getDecorView();
        mViews.clear();
        //找出两个页面中的view
        iteratorView(decorView);
        /***无埋点**********/
        if (mMarqueeBallView == null) {
            return;
        }
        String thisName = activity.getClass().getName();
        if (!"com.jjshome.mobile.datastatistics.ui.PageAndElementActivity".equals(thisName) &&
                !"com.jjshome.mobile.datastatistics.ui.ElementActivity".equals(thisName)) {
            if (isShow) {
                mMarqueeBallView.setVisibility(View.VISIBLE);
            } else {
                mMarqueeBallView.setVisibility(View.VISIBLE);
                mMarqueeBallView.show(true);
                mMarqueeBallView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });
                isShow = true;
            }
        }

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        DSAgent.onPause(activity);
        if (mMarqueeBallView == null) {
            return;
        }
        sCurActivity = null;
//        dialog = null;
        mMarqueeBallView.setVisibility(View.GONE);
//        mMarqueeBallView.show(false);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
//        popupWindow = null;

    }


    private void registerFragmentLifeCycle(Activity activity) {
        if (!(activity instanceof FragmentActivity)) {
            return;
        }
        FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
        if (fm == null) {
            return;
        }
        fm.registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentPreAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                super.onFragmentPreAttached(fm, f, context);
//                private static DialogFragment v4dialogFragment;
//
//                private static android.app.DialogFragment dialogFragment;
//                sendLog(f, Page.FRAGMENT.ON_PRE_ATTACH);
            }

            @Override
            public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
                super.onFragmentAttached(fm, f, context);
//                sendLog(f, Page.FRAGMENT.ON_ATTACH);
            }

            @Override
            public void onFragmentPreCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentPreCreated(fm, f, savedInstanceState);
//                sendLog(f, Page.FRAGMENT.ON_PRE_CREATE);
            }

            @Override
            public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
//                sendLog(f, Page.FRAGMENT.ON_CREATE);
            }

            @Override
            public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                super.onFragmentActivityCreated(fm, f, savedInstanceState);
//                sendLog(f, Page.FRAGMENT.ON_ACTIVITY_CREATE);
            }

            @Override
            public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState);
//                sendLog(f, Page.FRAGMENT.ON_VIEW_CREATE);
            }

            @Override
            public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentStarted(fm, f);
//                sendLog(f, Page.FRAGMENT.ON_START);
            }

            @Override
            public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentResumed(fm, f);
                Log.d("===============Resumed", f.getClass().getName());
                if (f instanceof DialogFragment) {
                    dialogFragment = (DialogFragment) f;
                }
//                sendLog(f, Page.FRAGMENT.ON_RESUME);
            }

            @Override
            public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentPaused(fm, f);
                Log.d("===============Paused", f.getClass().getSimpleName());
//                sendLog(f, Page.FRAGMENT.ON_PAUSE);
            }

            @Override
            public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentStopped(fm, f);
//                sendLog(f, Page.FRAGMENT.ON_STOP);
            }

            @Override
            public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
                super.onFragmentSaveInstanceState(fm, f, outState);
//                sendLog(f, Page.FRAGMENT.ON_SAVE_INSTANCE_STATE);
            }

            @Override
            public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentViewDestroyed(fm, f);
//                sendLog(f, Page.FRAGMENT.ON_VIEW_DESTROY);
            }

            @Override
            public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDestroyed(fm, f);
                if (f instanceof DialogFragment) {
                    dialogFragment = null;
                }
//                sendLog(f, Page.FRAGMENT.ON_DESTROY);
            }

            @Override
            public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
                super.onFragmentDetached(fm, f);
//                sendLog(f, Page.FRAGMENT.ON_DETACH);
            }

        }, true);
    }


    private void iteratorView(View view) {
        if (view instanceof EditText) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int childCount = vg.getChildCount();
            for (int i = 0; i < childCount; i++) {
                iteratorView(vg.getChildAt(i));
            }
        }
    }


    public View touchAnyView(MotionEvent motionEvent) {
        if (activityRefer.get() == null) return null;
        View touchView = findTouchView(motionEvent);
        if (touchView == null) {
            Toast.makeText((Activity) activityRefer.get(), "点击到空白区域", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText((Activity) activityRefer.get(), findTouchViewTag(touchView), Toast.LENGTH_SHORT).show();
        }

        return touchView;
    }

    private View findTouchView(MotionEvent motionEvent) {
        int[] location = new int[2];
        for (View v : mViews) {
            if (v.isShown()) {
                v.getLocationOnScreen(location);
                Rect r = new Rect();
                v.getGlobalVisibleRect(r);
                boolean contains = r.contains((int) motionEvent.getX(), (int) motionEvent.getY());
                if (contains) {
                    //TODO 在这里如果需要项目化功能,还需要对此view是否可见?是否可点进行一些判断,再确定用户点击的是哪个View
                    return v;
                }
            }
        }
        return null;
    }

    private String findTouchViewTag(View v) {
        if (v == null) return "View is null";
        if (v instanceof TextView) {
            if (v instanceof CheckBox) {
                CheckBox cb = (CheckBox) v;
                return "CheckBox, text is:" + cb.getText();
            } else if (v instanceof Button) {
                Button btn = (Button) v;
                return "Button, text is:" + btn.getText();
            } else if (v instanceof EditText) {
                EditText et = (EditText) v;
                return "EditText, text is:" + et.getText();
            } else {
                TextView tv = (TextView) v;
                return "TextView, text is:" + tv.getText().toString();
            }
        } else if (v instanceof ImageView) {
            ImageView im = (ImageView) v;
            return "ImageView, contentDrscription is:" + im.getContentDescription();
        } else if (v instanceof Spinner) {
            Spinner spinner = (Spinner) v;
            return "Spinner, item is:" + spinner.getCount();
        }
        return "No Identity";
    }
}
