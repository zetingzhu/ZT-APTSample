package com.zzt.zztapt.act;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.zzt.zztapt.databinding.ActivityTextClickAct3JavaBinding;

public class ActTestJava3 extends AppCompatActivity {


    ActivityTextClickAct3JavaBinding binding;


    public static void start(Context context) {
        Intent starter = new Intent(context, ActTestJava3.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextClickAct3JavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ASM- AppButton Java");
            }
        });


        binding.appBtn8.setOnClickListener(v ->
                System.out.println("ASM- AppButton Lambda")
        );
    }
}