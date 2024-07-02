package com.zzt.zztapt.act;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jjshome.mobile.datastatistics.DSAgent;
import com.zzt.utilcode.util.ColorUtils;
import com.zzt.zztapt.R;
import com.zzt.zztapt.databinding.ActTablayoutBinding;
import com.zzt.zztapt.entiy.MyVH;

/**
 * @author: zeting
 * @date: 2024/7/2
 */
public class ActTabLayout extends AppCompatActivity {
    ActTablayoutBinding binding;

    public static void start(Context context) {
        Intent starter = new Intent(context, ActTabLayout.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActTablayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {

        RecyclerView.Adapter adapter2 = new RecyclerView.Adapter<MyVH>() {
            @NonNull
            @Override
            public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(ActTabLayout.this).inflate(R.layout.item_viewpager, parent, false);
                return new MyVH(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyVH holder, int position) {
                TextView textView = holder.get(R.id.tv_pos);
                textView.setText("内容：" + position);
                textView.setTextColor(ColorUtils.getRandomColor());
                textView.setBackgroundColor(ColorUtils.getRandomColor());
            }

            @Override
            public int getItemCount() {
                return 3;
            }
        };
        // 设置滚动方向
        binding.vpCon1.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        binding.vpCon1.setAdapter(adapter2);
        binding.vpCon1.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        binding.tlTitle1.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("zzz", "切换事件");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tlTitle1, binding.vpCon1,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText("Tab " + position);
                    }
                });
        tabLayoutMediator.attach();


    }
}
