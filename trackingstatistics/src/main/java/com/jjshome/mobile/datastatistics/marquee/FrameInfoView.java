package com.jjshome.mobile.datastatistics.marquee;

import android.content.Context;
import androidx.annotation.NonNull;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jjshome.mobile.datastatistics.R;


public class FrameInfoView extends FrameLayout {

    private TextView mPathTextView;

    private TextView mWidthTextView;

    private TextView mHeightTextView;

    private TextView mListTextView;

    private Button mCloseButton;

    public FrameInfoView(@NonNull Context context) {
        super(context);
        inflate(context, R.layout.view_frame_info, this);
        mPathTextView = findViewById(R.id.tv_path);
        mWidthTextView = findViewById(R.id.tv_width);
        mHeightTextView = findViewById(R.id.tv_height);
        mListTextView = findViewById(R.id.tv_list);
        mCloseButton = findViewById(R.id.btn_close);
    }

    public void setData(FrameInfo info) {
        mPathTextView.setText("path: " + info.path);
        mWidthTextView.setText("width: " + info.width);
        mHeightTextView.setText("height: " + info.height+"=="+info.page);
        int listCount = info.position == null ? 0 : info.position.size();
        mListTextView.setText("从decorView到该控件经历了" + listCount + "个列表型控件,在列表中的位置分别是" + (listCount == 0 ? "[-]" : info.position.toString()));
    }

    public void setOnCloseClickListener(OnClickListener listener) {
        mCloseButton.setOnClickListener(listener);
    }

}
