package com.zzt.zztapt.act;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zzt.zztapt.R;

public class ActTestJava1 extends AppCompatActivity {

    Button button7;
    OnDialogViewClickListener otherClick;

    public static void start(Context context) {
        Intent starter = new Intent(context, ActTestJava1.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_test_java1);

        button7 = findViewById(R.id.button7);
        otherClick = new OnDialogViewClickListener() {
            @Override
            public void onClickA(Activity act, View v) {
                System.out.println("ASM 点击事件传递");
            }
        };
        button7.setOnClickListener(v -> otherClick.onClickA(ActTestJava1.this, v));
    }


    /**
     * 点击View事件监听
     */
    public interface OnDialogViewClickListener {
        void onClickA(Activity act, View v);
    }
}