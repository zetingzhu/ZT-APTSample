package com.jjshome.mobile.datastatistics.marquee;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;


import com.jjshome.mobile.datastatistics.R;
import com.jjshome.mobile.datastatistics.utils.DimensionPixeUtils;

import java.util.List;

public class RedFrameContainerView extends FrameLayout {

    private Rect mRect;


    public RedFrameContainerView(Context context) {
        super(context);
        mRect = new Rect();
    }

    public void update() {
        removeAllViews();

        List<FrameInfo> frameInfos = FrameInfoHelper.getFrameInfos();

        for (FrameInfo info : frameInfos) {
            ImageView iv = new ImageView(getContext());
            iv.setImageResource(R.drawable.frame_border);
            LayoutParams lp = new LayoutParams(info.width, info.height);
            lp.leftMargin = info.location[0];
            int result = DimensionPixeUtils.getHight(info.activity);
            lp.topMargin = info.location[1] - result;

            iv.setAlpha(1f);
            iv.setTag(info);
            addView(iv, lp);
        }

    }

    public FrameInfo showFrame(int x, int y) {
        final int count = getChildCount();
        View targetView = null;
        int area = Integer.MAX_VALUE;
        for (int i = 0; i < count; ++i) {
            View view = getChildAt(i);
            view.getGlobalVisibleRect(mRect);
            view.setVisibility(INVISIBLE);
            if (mRect.contains(x, y)) {
                int width = view.getWidth();
                int height = view.getHeight();
                if (width * height < area) {
                    targetView = view;
                }
            }
        }
        if (targetView != null) {
            targetView.setVisibility(VISIBLE);
            return (FrameInfo) targetView.getTag();
        }
        return null;
    }
}
