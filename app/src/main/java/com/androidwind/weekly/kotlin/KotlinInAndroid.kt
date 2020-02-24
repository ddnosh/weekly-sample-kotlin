package com.androidwind.weekly.kotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
class KotlinInAndroid : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //1. 控件直接用id, 不再使用findviewbyid
        btn_1.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                toastMe()
            }

        })
        btn_1.setOnClickListener { toastMe() } //lambda表达式更加简洁
        //2. 控件自定义方法
        iv_1.loadUrl("https://www.baidu.com/img/bd_logo1.png")
        //3. 控件自定义方法可放在单独的类中
        toast("a toast")
        toast("a toast", Toast.LENGTH_LONG)
        //4. 协程
        //两个耗时操作，必须要在异步线程实现处理，处理完成后需要在主线程输出结果
        //way1：
        MainScope().launch {
            val startTime = System.currentTimeMillis()
            println("[way1]tag1：" + Thread.currentThread().name)
            val text1 = withContext(Dispatchers.IO) {
                println("[way1]tag2：" + Thread.currentThread().name)
                delay(1000)
                "Hello "
            }
            val text2 = withContext(Dispatchers.IO) {
                println("[way1]tag3：" + Thread.currentThread().name)
                delay(1000)
                "World!"
            }
            println("[way1]tag4：" + Thread.currentThread().name)
            println(text1 + text2)
            println("[way1]耗时：" + (System.currentTimeMillis() - startTime))
        }
        //way2：
        MainScope().launch {
            val startTime = System.currentTimeMillis()
            println("[way2]tag1：" + Thread.currentThread().name)
            val text1 = getHello("[way2]")
            val text2 = getWorld("[way2]")
            println("[way2]tag4：" + Thread.currentThread().name)
            println(text1 + text2)
            println("[way2]耗时：" + (System.currentTimeMillis() - startTime))
        }
        //way3:异步，不阻塞
        MainScope().launch {
            val startTime = System.currentTimeMillis()
            println("[way3]tag1：" + Thread.currentThread().name)
            val text1 = async { getHello("[way3]") }
            val text2 = async { getWorld("[way3]") }
            println("[way3]tag4：" + Thread.currentThread().name)
            println(text1.await() + text2.await())
            println("[way3]耗时：" + (System.currentTimeMillis() - startTime))
        }
        //5. set赋值, text替换setText
        btn_1.text = "click me......"
    }

    private fun toastMe() {
        Toast.makeText(this, "clicked!", Toast.LENGTH_LONG).show()
    }

    fun ImageView.loadUrl(url: String) {//在任何类上添加函数
        Glide.with(iv_1.getContext()).load(url).into(iv_1)
    }

    private suspend fun getHello(way: String): String {
        return withContext(Dispatchers.IO) {
            println("$way tag2：" + Thread.currentThread().name)
            delay(1000)
            "Hello "
        }
    }

    private suspend fun getWorld(way: String): String {
        return withContext(Dispatchers.IO) {
            println("$way tag3：" + Thread.currentThread().name)
            delay(1000)
            "World!"
        }
    }
}