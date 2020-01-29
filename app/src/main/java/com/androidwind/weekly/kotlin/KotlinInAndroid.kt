package com.androidwind.weekly.kotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class KotlinInAndroid : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //直接用id, 不再使用findviewbyid
        btn_1.setOnClickListener { toastMe() } //lambda表达式更加简洁
        iv_1.loadUrl("https://www.baidu.com/img/bd_logo1.png")
        toast("a toast")
        toast("a toast", Toast.LENGTH_LONG)
    }

    private fun toastMe() {
        Toast.makeText(this, "clicked!", Toast.LENGTH_LONG).show()
    }

    fun ImageView.loadUrl(url: String) {//在任何类上添加函数
        Glide.with(iv_1.getContext()).load(url).into(iv_1)
    }

    fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, message, duration).show()
    }
}