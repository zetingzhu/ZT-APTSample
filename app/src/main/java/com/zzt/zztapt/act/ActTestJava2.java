package com.zzt.zztapt.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zzt.zztapt.R;

public class ActTestJava2 extends AppCompatActivity {
    Button button4, button5, button6;
    RadioGroup radio_group, radio_group1;
    RadioButton radioButton, radioButton2;
    SwitchCompat switch1, switch2;
    private AppCompatTextView tv_click1;

    public static void start(Context context) {
        Intent starter = new Intent(context, ActTestJava2.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_click_act2);
        initView();
    }

    private void initView() {
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        radio_group = findViewById(R.id.radio_group);
        radio_group1 = findViewById(R.id.radio_group1);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        tv_click1 = findViewById(R.id.tv_click1);

        tv_click1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ASM- textview 点击 Java");
            }
        });
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                System.out.println("ASM- radio_group Java");
            }
        });
        radio_group1.setOnCheckedChangeListener((group, checkedId) -> System.out.println("ASM- radio_group Lambda"));

        radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("ASM- radioButton Java");
            }
        });


        radioButton2.setOnCheckedChangeListener((buttonView, isChecked) -> System.out.println("ASM- radioButton Lambda"));

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("ASM- switch Java");
            }
        });

        switch2.setOnCheckedChangeListener((buttonView, isChecked) -> System.out.println("ASM- switch Lambda"));

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ASM- 点击按钮 4");
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ASM- 点击按钮 5");
            }
        });

        button6.setOnClickListener(v -> System.out.println("ASM- 点击按钮 6"));
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}