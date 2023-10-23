package com.jjshome.mobile.datastatistics.marquee;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.jjshome.mobile.datastatistics.utils.Common;
import com.jjshome.mobile.datastatistics.utils.GlobalToast;
import com.jjshome.mobile.datastatistics.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;


public class FrameInfoHelper {
    //    private static boolean isListView=false;
    private static List<FrameInfo> sFrameInfoList = new ArrayList<>();

    static List<FrameInfo> getFrameInfos() {
//        isListView=false;
        sFrameInfoList = new ArrayList<>();
        Activity activity = Navigator.getCurActivity();

        DialogFragment dialogFragment = Navigator.getDialogFragment();
        PopupWindow popupWindow = Navigator.getPopupWindow();
        Dialog dialog = Navigator.getDialog();
        AlertDialog alertDialog = Navigator.getAlertDialog();

        if (dialogFragment != null) {//兼容DialogFragment \Dialog暂时没办法
            View decorView = dialogFragment.getDialog().getWindow().getDecorView();
            findFrame(decorView);
        } else if (popupWindow != null && popupWindow.isShowing()) {
            View decorView = popupWindow.getContentView();
            if (decorView != null) {
                findFrame(decorView);
            } else if (activity != null) {
                decorView = activity.getWindow().getDecorView();
                findFrame(decorView);
            }
        } else if (dialog != null && dialog.isShowing()) {
            View decorView = dialog.getWindow().getDecorView();
            findFrame(decorView);
        } else if (alertDialog != null && alertDialog.isShowing()) {
            View decorView = alertDialog.getWindow().getDecorView();
            findFrame(decorView);
        } else if (activity != null) {
            View decorView = activity.getWindow().getDecorView();
            findFrame(decorView);
        }
        return sFrameInfoList;
    }


    private static void findFrame(View view) {
        if (isViewSelfVisible(view)) {
            if (hasOnclick(view)) {
                FrameInfo frameInfo = getFrameInfoFromView(view);
                sFrameInfoList.add(frameInfo);
            }
            if (view instanceof WebView || view.toString().contains("com.tencent.smtt.sdk.WebView")) {
                Activity activity = Navigator.getCurActivity();
                if (activity != null) {
                    String className = activity.getClass().getName();
                    if (!TextUtils.isEmpty(className) && !className.equals("com.jjshome.esf.activity.EsfDetailsActivity")) {
                        if (className.equals("com.jjshome.oa.minapp.activity.MinAppActivity") || className.equals("com.jjshome.oa.minapp.activity.MinAppMainActivity")) {
                            GlobalToast.show(Navigator.getCurActivity(), "小程序暂不支持圈选", Toast.LENGTH_LONG);
                        } else {
                            GlobalToast.show(Navigator.getCurActivity(), "H5页面暂不支持圈选", Toast.LENGTH_LONG);
                        }

                        return;
                    }
                }
            }
//            FrameInfo frameInfo = getFrameInfoFromView(view);
//            sFrameInfoList.add(frameInfo);

//        if (view.getParent() instanceof ListView) {
//            isListView=true;
//        }
            if (view instanceof ViewParent) {
                ViewGroup group = (ViewGroup) view;
                final int count = group.getChildCount();
                for (int i = 0; i < count; ++i) {
                    findFrame(group.getChildAt(i));

                }
            }
        }
    }


    private static boolean hasOnclick(View view) {


        if (view.hasOnClickListeners()) {
            return true;
        }

        if (view.getParent() instanceof ListView) {
            return true;
        }

        if (view instanceof CheckBox) {
            return true;
        }

        if (view instanceof AppCompatCheckBox) {
            return true;
        }

        if (view instanceof RadioButton) {
            return true;
        }
        if (view.getParent() instanceof RecyclerView) {
            return true;
        }
        if (view.getParent() instanceof GridView) {
            return true;
        }
        if (view instanceof EditText) {
            return true;
        }
        if (view.getParent().toString().contains("android.support.design.widget.TabLayout$SlidingTabStrip")) {
            return true;
        }
//        if (view.getParent().toString().contains("com.jjshome.widget.segmentcontrolview.SegmentControlView")) {
//            Log.d("======xxx", view.toString());
//            return true;
//        }
//        if (view.toString().contains("com.jjshome.widget.segmentcontrolview.SegmentControlView")) {
//            Log.d("======000", view.toString());
//            return true;
//        }
        if (view instanceof SwitchCompat) {
            return true;
        }

        return false;
    }

    private static boolean isViewSelfVisible(View mView) {
//        View currentView = mView;
//
//        Rect currentViewRect = new Rect();
//        boolean partVisible = currentView.getGlobalVisibleRect(currentViewRect);
//        boolean halfPercentVisible = (currentViewRect.bottom - currentViewRect.top) * (currentViewRect.right - currentViewRect.left) >= (mView.getMeasuredHeight() * mView.getMeasuredWidth() / 2);
//        boolean totalViewVisible = partVisible && halfPercentVisible;
//        //被其父布局遮挡超过一半
//        if (!totalViewVisible) {
//            return false;
//        }
        if (mView == null) {
            return false;
        } else if (WindowHelper.isDecorView(mView)) {
            return true;
        } else if ((mView.getWidth() <= 0 || mView.getHeight() <= 0 || mView.getAlpha() <= 0.0F) && !mView
                .getLocalVisibleRect(new Rect())) {
            return false;
        } else if (mView.getVisibility() != View.VISIBLE && mView.getAnimation() != null && mView.getAnimation()
                .getFillAfter()) {
            return true;
        } else {
            return mView.getVisibility() == View.VISIBLE;
        }
    }

    /**
     * 可见区域小于面积的50%，为覆盖
     * @param view
     * @return
     * 参考https://blog.csdn.net/xuguobiao/article/details/50911986
     */
    private static boolean isCover(View view) {
        View currentView = view;

        Rect currentViewRect = new Rect();
        boolean partVisible = currentView.getGlobalVisibleRect(currentViewRect);
        boolean halfPercentVisible = (currentViewRect.bottom - currentViewRect.top) * (currentViewRect.right - currentViewRect.left) >= (view.getMeasuredHeight() * view.getMeasuredWidth() / 2);
        boolean totalViewVisible = partVisible && halfPercentVisible;
        //被其父布局遮挡超过一半
        if (!totalViewVisible)
            return true;

        while (currentView.getParent() instanceof ViewGroup) {
            ViewGroup currentParent = (ViewGroup) currentView.getParent();
            if (currentParent.getVisibility() != View.VISIBLE) {
                return true;
            }

            int start = indexOfViewInParent(currentView, currentParent);
            for (int i = start + 1; i < currentParent.getChildCount(); i++) {
                Rect viewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                View otherView = currentParent.getChildAt(i);

                if (otherView.getVisibility() != View.VISIBLE)
                    continue;

                Rect otherViewRect = new Rect();
                otherView.getGlobalVisibleRect(otherViewRect);
                //如果相交
                if (Rect.intersects(viewRect, otherViewRect)) {

                    int width = Math.min(viewRect.right, otherViewRect.right) - Math.max(viewRect.left, otherViewRect.left);
                    int height = Math.min(viewRect.bottom, otherViewRect.bottom) - Math.max(viewRect.top, otherViewRect.top);
                    //相交部分的2倍大于view本身大小
                    //参考//https://blog.csdn.net/u012476249/article/details/53318891
                    if (width * height * 2 >= view.getMeasuredHeight() * view.getMeasuredWidth()) {
                        return true;
                    }
                }
            }
            currentView = currentParent;
        }

        return false;
    }

    private static int indexOfViewInParent(View view, ViewGroup parent){
        int index;
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index) == view)
                break;
        }
        return index;
    }

    public static FrameInfo getFrameInfoFromView(final View view) {
        Activity activity = Navigator.getCurActivity();


        ArrayList<View> viewTreeList = new ArrayList<>(8);
        ViewParent parent = view.getParent();
        viewTreeList.add(view);

        while (parent instanceof ViewGroup) {
            viewTreeList.add((ViewGroup) parent);
            parent = parent.getParent();
        }

        int endIndex = viewTreeList.size() - 1;
        View rootView = viewTreeList.get(endIndex);
        WindowHelper.init();
        int viewPosition = 0;
        ArrayList<String> listPositions = null;
        String path = WindowHelper.getSubWindowPrefix(rootView);
        if (!WindowHelper.isDecorView(rootView) && !(rootView.getParent() instanceof View)) {
            path = path + "/" + rootView.getClass().getSimpleName();
        }

        if (rootView instanceof ViewGroup) {
            ViewGroup parentView = (ViewGroup) rootView;

            for (int i = endIndex - 1; i >= 0; --i) {
                View childView = viewTreeList.get(i);

                Object viewName = childView.getClass().getSimpleName();
//                if (childView instanceof SwitchCompat) {
//                    SwitchCompat switchCompat = (SwitchCompat) childView;
//                    viewPosition = switchCompat.isChecked() ? 1 : 0;
//                } else if (childView instanceof CheckBox) {
//                    CheckBox checkBox = (CheckBox) childView;
//                    viewPosition = checkBox.isChecked() ? 1 : 0;
//                } else {
//                    viewPosition = parentView.indexOfChild(childView);
//                }

                viewPosition = parentView.indexOfChild(childView);

                // 作为sdk,使用方不一定使用androidx或者support包,故应先判断是否有该类,再通过instanceof判断
                if (ClassHelper.instanceOfAndroidXViewPager(parentView)) {
                    viewPosition = ((ViewPager) parentView).getCurrentItem();
                } else if (parentView instanceof AdapterView) {
                    AdapterView listView = (AdapterView) parentView;
                    viewPosition += listView.getFirstVisiblePosition();
                } else if (parentView instanceof RecyclerView) {
                    /**RecyclerView--postion适配***/
                    RecyclerView recyclerView = (RecyclerView) parentView;
//                    viewPosition= recyclerView.getChildAdapterPosition(view);

                    LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                    viewPosition += layoutManager.findFirstVisibleItemPosition();//获取第一个先是的View的索引
                }
                // todo support包的列表以及RecyclerView的适配代码较长,思路与上述代码一致,此处省略
                if (isListView(parentView)) {
                    if (listPositions == null) {
                        listPositions = new ArrayList<>(4);
                    }
                    listPositions.add(String.valueOf(viewPosition));
                    path = path + "/" + viewName + "[-]";
                }
//                else if (isChexBox(childView)) {
//                    if (listPositions == null) {
//                        listPositions = new ArrayList<>(4);
//                    }
//                    listPositions.add(String.valueOf(viewPosition));
//                    path = path + "/" + viewName + "[-]";
//                }
                else {
                    path = path + "/" + viewName + "[" + viewPosition + "]";
                }
                int id = childView.getId();
                if (id > 2130706432) {
                    String idName = view.getResources().getResourceEntryName(id);
                    path = path + "#" + idName;
                }

                if (!(childView instanceof ViewGroup)) {
                    break;
                }
                parentView = (ViewGroup) childView;
            }
        }
        String pageName = "";
        if (activity == null) {
            pageName = ViewUtil.getActivityName(view);
        } else {
            pageName = activity.getClass().getName();
            if (TextUtils.isEmpty(pageName)) {
                pageName = activity.getClass().getSimpleName();
            }
        }
        int width = view.getWidth();
        int height = view.getHeight();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return new FrameInfo(pageName, path, width, height, listPositions, location, activity, view);

    }

    private static boolean isListView(View view) {
        // todo support包的列表以及RecyclerView的适配代码较长,思路与上述代码一致,此处省略
        if ((view instanceof ViewGroup) && view instanceof RecyclerView) {
            return true;
        }
        if (view instanceof AdapterView) {
            return true;
        }

        return false;
    }

    private static boolean isChexBox(View view) {
        /*********归类到index***********/
        if (view instanceof SwitchCompat) {
            return true;
        } else if (view instanceof CheckBox) {
            return true;
        }
        return false;
    }


}
