package com.androidwind.weekly.kotlin

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */

fun main(args: Array<String>) {

    //1. 函数作为参数传递, 可以用作回调: T.()->Unit 和 ()->Unit
    //() -> Unit//表示无参数无返回值的Lambda表达式类型
    //(T) -> Unit//表示接收一个T类型参数，无返回值的Lambda表达式类型
    //(T) -> R//表示接收一个T类型参数，返回一个R类型值的Lambda表达式类型

    //()->Unit
    //1.1 不带参数和返回值的函数作为形参
    fun action0(method: () -> Unit) {
        method()
        println("this is action0")
    }

    fun method0() {
        println("this is method0 which is invoked")
    }

    //format->step1
    action0({
        println("this is action0")
    })
    //format->step2
    action0() {
        println("this is action0")
    }
    //format->step3
    action0 {
        println("this is action0")
    }

    fun action1(first: Int, method: () -> Unit) {
        method()
        println("this is action1")
    }

    //format->step1
    action1(1, {
        println("第1种写法")
    })
    //format->step2
    action1(1) {
        println("第2种写法")
    }

    val method: () -> Unit = {
        println("第3种写法")
    }
    action1(1, method)

    //1.2 带参数和返回值的函数作为形参
    fun method1(msg1: Int, msg2: Int): Int {
        println("this is method1")
        return msg1 + msg2;
    }

    fun getResult1(
        arg01: Int,
        arg02: Int,
        method: (arg1: Int, arg2: Int) -> Int
    ) {
        println("----->msg:before")
        val ret = method(arg01, arg02);
        println("----->msg:after = $ret")
    }

    println(getResult1(1, 2, ::method1))

    //T.()->Unit
    fun getResult3(
        method: Test.() -> Unit
    ) {
        println("----->msg:before")
        val test1 = Test()
        test1.apply(method)
        println("----->msg:after ${test1.a}")
    }

    println(
        getResult3(
            {
                a = "Tim"
            })
    )

    //2. 类委托
    val b = BaseImpl(10)
    Derived(b).print() // 输出 10
    Derived(b).otherPrint() //输出other

    //3. 属性委托
    val isLogin: Boolean by DerivedProperty("tom")
    if (isLogin) {
        println("this is a property when invoked")
    }

    //4. 协程
    GlobalScope.launch {
        delay(1000)
        print("World")
    }
    print("Hello ")
    Thread.sleep(2000)
    print("!")
}

class Test {
    var a: String? = null

}

// 创建接口
interface Base {
    fun print()
}

// 实现此接口的委托类
class BaseImpl(private val x: Int) : Base {
    override fun print() {
        println(x)
    }
}

// 通过关键字 by 建立代理类, 能够调用委托类已实现的方法和属性，不需再实现print()方法
class Derived(b: Base) : Base by b {
    fun otherPrint() {
        println("other")
    }
}

class DerivedProperty<T>(private val name: String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        println("this is a property delegate: $name")
        when (name) {
            "tom" -> return true as T
            "jerry" -> return false as T
            else -> return false as T
        }

        return true as T
    }

}