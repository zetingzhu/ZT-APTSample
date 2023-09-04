package com.zzt.zztapt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    var btn1: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn1 = findViewById(R.id.button)
        btn1?.setOnClickListener {
            Toast.makeText(it.context, "点击按钮", Toast.LENGTH_SHORT).show()
        }
    }
}