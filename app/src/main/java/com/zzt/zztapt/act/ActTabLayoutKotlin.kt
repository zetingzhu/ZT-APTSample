package com.zzt.zztapt.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.zzt.utilcode.util.ColorUtils
import com.zzt.zztapt.R
import com.zzt.zztapt.databinding.ActTablayoutBinding
import com.zzt.zztapt.databinding.ActTablayoutKotlinBinding
import com.zzt.zztapt.entiy.MyVH

/**
 * @author: zeting
 * @date: 2024/7/2
 */
class ActTabLayoutKotlin : AppCompatActivity() {
    val TAG = ActTabLayoutKotlin::class.java.simpleName
    var binding: ActTablayoutKotlinBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActTablayoutKotlinBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        initView()
    }

    private fun initView() {
        binding?.tlTitle1?.apply {
            addTab(this.newTab().setText("Tab 1 Item"))
            addTab(this.newTab().setText("Tab 2 Item"))
            addTab(this.newTab().setText("Tab 3 Item"))
            addTab(this.newTab().setText("Tab 4 Item"))
        }

        Log.d(TAG, "ActTabLayoutKotlin onCreate 5")
        binding?.tlTitle1?.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("ASM-zzz", "ActTabLayoutKotlin 切换事件 5")
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })

        var vpCon1: ViewPager2? = null;
        vpCon1?.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        });
    }
    companion object {
        fun start(context: Context) {
            val starter = Intent(context, ActTabLayoutKotlin::class.java)
            context.startActivity(starter)
        }
    }
}
