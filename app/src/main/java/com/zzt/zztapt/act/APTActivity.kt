package com.zzt.zztapt.act

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.zzt.adapter.StartActivityRecyclerAdapter
import com.zzt.entity.StartActivityDao
import com.zzt.zztapt.databinding.ActivityAptBinding

class APTActivity : AppCompatActivity() {
    val TAG = "APTActivity"
    private lateinit var binding: ActivityAptBinding

    var notifyId = 111


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAptBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        initView()

    }


    private fun initView() {

        val mListDialog: MutableList<StartActivityDao> = ArrayList()
        mListDialog.add(StartActivityDao("编译 Kotlin", " ", "1"))
        mListDialog.add(StartActivityDao("编译 Java", " ", "2"))
        mListDialog.add(StartActivityDao("Java", "点击事件对比", "3"))
        mListDialog.add(StartActivityDao("Java", "点击事件传递分析", "4"))

        StartActivityRecyclerAdapter.setAdapterData(
            binding.rvList,
            RecyclerView.VERTICAL,
            mListDialog
        ) { itemView: View?, position: Int, data: StartActivityDao ->
            when (data.arouter) {
                "1" -> {
                    ActTestKotlin.start(this@APTActivity)
                }

                "2" -> {
                    ActTestJava2.start(this@APTActivity)
                }

                "3" -> {
                    ActTestJava3.start(this@APTActivity)
                }

                "4" -> {
                    ActTestJava1.start(this@APTActivity)
                }
            }
        }
    }
}