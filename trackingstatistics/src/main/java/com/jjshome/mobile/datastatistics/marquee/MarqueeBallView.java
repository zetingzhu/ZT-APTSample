package com.jjshome.mobile.datastatistics.marquee;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jjshome.mobile.datastatistics.R;
import com.jjshome.mobile.datastatistics.utils.DimensionPixeUtils;
import com.jjshome.mobile.datastatistics.utils.ViewInfoData;
import com.jjshome.mobile.datastatistics.utils.ViewUtil;


/**
 * 圈选的小球
 */
public class MarqueeBallView extends AppCompatImageView implements View.OnClickListener {

    private Context context;

    private float mLastX;

    private float mLastY;

    private final int[] mLocation;

    private boolean mIsDragging;

    private final int mMoveSlotSquare;

    private final WindowManager mWm;

    private RedFrameContainerView mRedFrameContainerView;

    private FrameInfo mFrameInfo;

    private FrameInfoView mFrameInfoView;

    private String workerId;

    private String token;

    private static MarqueeBallView instance;


    private long downTime = 0;
    private long upTime = 0;

    public static MarqueeBallView getInstance(Context context) {
        if (instance == null) {
            instance = new MarqueeBallView(context);
        }
        return instance;
    }

    public void setWorkerId(String workerId) {
        this.workerId = workerId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public MarqueeBallView(Context context) {
        super(context);
        this.context = context;
//        this.workerId = workerId;
//        this.token = token;
        final int touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        mMoveSlotSquare = touchSlop * touchSlop;
        mLocation = new int[2];
        mWm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        setBackgroundResource(R.drawable.circle);
        mRedFrameContainerView = new RedFrameContainerView(context);
        mFrameInfoView = new FrameInfoView(context);
        mFrameInfoView.setOnCloseClickListener(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downTime = System.currentTimeMillis();
                mLastX = event.getRawX();
                mLastY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float diffX = event.getRawX() - mLastX;
                float diffY = event.getRawY() - mLastY;
                if (!mIsDragging) {
                    mRedFrameContainerView.update();
                    showFrameContainerView();
                    mIsDragging = true;
                }
//                if (diffX * diffX + diffY * diffY > 0) {
                updatePositionBy((int) (diffX), (int) (diffY));
                mLastX = event.getRawX();
                mLastY = event.getRawY();
//                }
                break;
            case MotionEvent.ACTION_UP:
                upTime = System.currentTimeMillis();
                mIsDragging = false;
                if ((upTime - downTime) < 200) {
                    isOnCliskListener();
                    removeFrameContainerView();
                    break;
                }
                if (mFrameInfo != null) {
                    setVisibility(GONE);
                    Bitmap screenBitmap = Falcon.takeScreenshotBitmap(mFrameInfo.activity);
                    setVisibility(VISIBLE);
                    showFrameInfoView(screenBitmap);
                }
                removeFrameContainerView();
                break;
            default:
                break;
        }
        return true;

    }


    /**
     * 是否为点击时间
     */
    private void isOnCliskListener() {
        setVisibility(GONE);
//        StatisBasicMyDialog myDialog = new StatisBasicMyDialog(Navigator.getCurActivity());
//        myDialog.setCancelable(false);
//        myDialog.setEanbleBackKey(true);
//        myDialog.setOnDialogBtnListener(new StatisBasicMyDialog.OnDialogBtnListener() {
//            @Override
//            public void onClick(int type, StatisBasicMyDialog dialog) {
//                if (type == 0) {//左侧
//                    //圈选
//                    setVisibility(VISIBLE);
//                } else {
//                    //退出圈选
//                    show(false);
//                    Navigator.getInstance().initMarqueeBallView();
//                }
//            }
//        });
//        myDialog.show();
    }

    private void updatePositionBy(final int x, final int y) {
        final WindowManager.LayoutParams params = (WindowManager.LayoutParams) getLayoutParams();
        params.x += x;
        params.y -= y;
        mWm.updateViewLayout(this, params);
        getLocationOnScreen(mLocation);
        int result = DimensionPixeUtils.getHight(Navigator.getCurActivity());
        mFrameInfo = mRedFrameContainerView.showFrame(mLocation[0] + getWidth() / 2, (mLocation[1] - result) + getHeight() / 2);
//        mFrameInfo = mRedFrameContainerView.showFrame(mLocation[0] + getWidth() / 2, mLocation[1] + getHeight() / 2);
    }


    public void show(boolean showMarqueeBall) {
        if (showMarqueeBall) {
            showView(this, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, false);
        } else {
            removeSafely(this);
            removeFrameContainerView();
            removeFrameInfoView();
        }

    }

    public boolean isShow = false;

    private void showView(View view, int width, int height, boolean focus) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(getContext())) {
            requestPermission();
            return;
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

        lp.format = PixelFormat.RGBA_8888;
        lp.width = width;
        lp.height = height;
        lp.gravity = Gravity.START | Gravity.BOTTOM;
        int result = DimensionPixeUtils.getHight(Navigator.getCurActivity());
        if (result == 0) {
            lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                    WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR |
                    WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        } else {
            lp.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        }
//        // 可回退
            lp.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }

//
        if (focus) {
            lp.flags |= WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        }
        removeSafely(view);
        mWm.addView(view, lp);
        isShow = true;
    }

    private void requestPermission() {
        Toast.makeText(getContext(), "请开启弹窗权限，才能展示圈选入口！", Toast.LENGTH_LONG).show();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("package:" + getContext().getPackageName()));
                getContext().startActivity(intent);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }


    private void showFrameContainerView() {
        showView(mRedFrameContainerView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
    }

    private void removeFrameContainerView() {
        removeSafely(mRedFrameContainerView);
    }

    private void showFrameInfoView(Bitmap bitmap) {
        if (mFrameInfo == null) {
            return;
        }

        View actView = mFrameInfo.activity.getWindow().getDecorView();
        Bitmap screenBitmap = Bitmap.createBitmap(actView.getMeasuredWidth(),
                actView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas a = new Canvas(screenBitmap);
        actView.draw(a);
//        Bitmap screenBitmap = Falcon.takeScreenshotBitmap(mFrameInfo.activity);
        Bitmap viewBitmap = Bitmap.createBitmap(mFrameInfo.view.getMeasuredWidth(),
                mFrameInfo.view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(viewBitmap);
        mFrameInfo.view.draw(c);
        String obj = ViewUtil.getViewText(mFrameInfo.view);
        if (mFrameInfo != null && mFrameInfo.activity != null && !mFrameInfo.activity.isFinishing()) {
            ViewInfoEntity result = new ViewInfoEntity();
            result.height = mFrameInfo.height;
            result.location = mFrameInfo.location;
            result.page = mFrameInfo.page;
            result.path = mFrameInfo.path;
            result.width = mFrameInfo.width;
            result.position = mFrameInfo.position;
            result.screenshotUri = screenBitmap;
            result.viewUri = viewBitmap;
            result.uploadUri = bitmap;
            result.obj=obj;
            ViewInfoData.setViewInfoEntity(result);
//            Intent intent = new Intent(mFrameInfo.activity, PageAndElementActivity.class);
//            intent.putExtra("workerId", workerId);
//            intent.putExtra("token", token);
//            mFrameInfo.activity.startActivity(intent);
            Log.d("=====4", System.currentTimeMillis() + "");
        }


//        Log.d("=====1",System.currentTimeMillis()+"");
//        //获取
//        CollectViewInfoTask task = new CollectViewInfoTask(mFrameInfo.activity, mFrameInfo.view, new CollectViewInfoTask.Callback() {
//            @Override
//            public void onDataReady(@Nullable ViewInfoEntity result) {
//                Log.d("=====3",System.currentTimeMillis()+"");
//                if (mFrameInfo!=null&&mFrameInfo.activity != null && !mFrameInfo.activity.isFinishing()) {
//                    result.height = mFrameInfo.height;
//                    result.location = mFrameInfo.location;
//                    result.page = mFrameInfo.page;
//                    result.path = mFrameInfo.path;
//                    result.width = mFrameInfo.width;
//                    result.position = mFrameInfo.position;
//                    ViewInfoData.setViewInfoEntity(result);
//                    Intent intent = new Intent(mFrameInfo.activity, PageAndElementActivity.class);
//                    intent.putExtra("workerId", workerId);
//                    intent.putExtra("token", token);
//                    mFrameInfo.activity.startActivity(intent);
//                    Log.d("=====4",System.currentTimeMillis()+"");
//                }
//            }
//        });
//        task.execute(screenBitmap, viewBitmap,bitmap);
        Log.d("=====2", System.currentTimeMillis() + "");
    }

    private void removeFrameInfoView() {
        removeSafely(mFrameInfoView);
    }

    private void removeSafely(final View view) {
        try {
            if (view != null) {
                mWm.removeView(view);
            }
        } catch (Throwable t) {
            // ignore
        }
    }

    @Override
    public void onClick(View v) {
        removeSafely(mFrameInfoView);
    }
}
