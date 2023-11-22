package com.zzt.zztapt.bytecode;

import android.view.View;
import android.widget.TextView;

/**
 * @author: zeting
 * @date: 2023/11/20
 */
public class JavaBytecode {
    public static void main(String[] args) {
        TextView textView = new TextView(null);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
