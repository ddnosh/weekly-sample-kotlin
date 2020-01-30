package com.androidwind.weekly.kotlin

import kotlin.reflect.KProperty

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */

fun main(args: Array<String>) {

    //1. 函数作为参数传递: T.()->Unit 和 ()->Unit
    //() -> Unit//表示无参数无返回值的Lambda表达式类型
    //(T) -> Unit//表示接收一个T类型参数，无返回值的Lambda表达式类型
    //(T) -> R//表示接收一个T类型参数，返回一个R类型值的Lambda表达式类型

    //()->Unit
    fun logFun(msg1: String, msg2: String): Int {
        println("----->msg:${msg1},${msg2}----")
        return 1;
    }

    fun getResult1(
        arg01: String,
        arg02: String,
        method: (arg1: String, arg2: String) -> Int
    ) {
        println("----->msg:before")
        val ret = method(arg01, arg02);
        println("----->msg:after = $ret")
    }

    println(getResult1("参数1", "参数2", ::logFun))

    //T.()->Unit
    fun getResult2(
        method: Test.() -> Unit
    ) {
        println("----->msg:before")
        val test1 = Test()
        test1.apply(method)
        println("----->msg:after ${test1.a}")
    }

    println(
        getResult2(
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
}

class Test {
    var a: String? = null

}

// 创建接口
interface Base {
    fun print()
}

// 实现此接口的委托类
class BaseImpl(val x: Int) : Base {
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

class DerivedProperty<T>(val name: String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        println("this is a property delegate")
        return true as T
    }

}