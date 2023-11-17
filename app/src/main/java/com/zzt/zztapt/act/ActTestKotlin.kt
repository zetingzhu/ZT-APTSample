package com.zzt.zztapt.act

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CompoundButton
import android.widget.ListView
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zzt.zztapt.R
import com.zzt.zztapt.adapter.RvAdapter


class ActTestKotlin : AppCompatActivity() {
    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, ActTestKotlin::class.java)
            context.startActivity(starter)
        }
    }

    var btn1: Button? = null
    var button2: Button? = null
    var button3: Button? = null
    var recycle_view: RecyclerView? = null
    var list_view: ListView? = null
    var mList: MutableList<String>? = null
    var switch1: SwitchCompat? = null
    var switch2: SwitchCompat? = null
    var radio_group: RadioGroup? = null
    var radio_group1: RadioGroup? = null
    var radioButton: RadioButton? = null
    var radioButton2: RadioButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }


    private fun initData() {
        mList = mutableListOf()
        for (i in 0..3) {
            mList?.add("item:$i")
        }

        recycle_view?.layoutManager = LinearLayoutManager(this@ActTestKotlin)
        recycle_view?.adapter = RvAdapter(
            mList,
            object : RvAdapter.RvItemOnClickListener {
                override fun onClickZ(pos: Int, objects: String?) {
                    println("ASM- recycle_view item pos:$pos object:$objects")
                }
            })
        val adapter =
            ArrayAdapter<String>(
                this,
                android.R.layout.test_list_item,
                mList?.toList() ?: arrayListOf()
            )
        list_view?.setAdapter(adapter)
        list_view?.setOnItemClickListener { parent, view, position, id ->
            println("ASM- list_view item pos:$position  Lambda")
        }
    }

    private fun initView() {
        btn1 = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)
        recycle_view = findViewById(R.id.recycle_view)
        list_view = findViewById(R.id.list_view)
        switch1 = findViewById(R.id.switch1)
        radio_group = findViewById(R.id.radio_group)
        radioButton = findViewById(R.id.radioButton)
        switch2 = findViewById(R.id.switch2)
        radioButton2 = findViewById(R.id.radioButton2)
        radio_group1 = findViewById(R.id.radio_group1)
        btn1?.setOnClickListener {
            println("ASM- 点击跳另外一个页面 Lambda")
            ActTestJava2.start(this@ActTestKotlin)
        }

        button2?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                println("ASM- 第二个按钮")
            }
        })

        button3?.setOnClickListener { view ->
            println("ASM-  按钮 3 Lambda")
            ActTestJava3.start(this@ActTestKotlin)
        }

        switch1?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                println("ASM- switch1 Java")
            }
        })

        switch2?.setOnCheckedChangeListener { buttonView, isChecked ->
            println("ASM- switch 2 Lambda")
        }

        radio_group?.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                println("ASM- radio_group java")
            }
        })
        radio_group1?.setOnCheckedChangeListener { group, checkedId -> println("ASM- radio_group  Lambda") }
        radioButton?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                println("ASM- radioButton java")
            }
        })
        radioButton2?.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                println("ASM- radioButton Lambda")
            }
        })

    }
}