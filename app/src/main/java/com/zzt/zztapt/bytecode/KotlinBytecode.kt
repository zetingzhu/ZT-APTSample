package com.zzt.zztapt.bytecode

import android.view.View
import android.widget.TextView

/**
 * @author: zeting
 * @date: 2023/11/20
 *
 */
class KotlinBytecode {
    val textView = TextView(null)

    fun main(args: Array<String>) {
        textView.setOnClickListener { }
    }

    fun main2(args: Array<String>) {
        textView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

            }
        })
    }
}