package com.androidwind.weekly.kotlin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty

/**
 * Kotlin语法糖
 *
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */

fun main(args: Array<String>) {

    //1. lambda表达式
    //(1) kotlin中lambda表达式定义在{}中
    //(2) 其参数(如果存在)在 -> 之前声明(参数类型可以省略)
    //(3) 函数体(如果存在)在 -> 后面

    //源代码：无参
    fun l1() {
        println("无参数")
    }
    //lambda
    val l1 = { println("无参数") }
    //调用
    l1()
    //源代码：有参
    fun l2(x: Int, y: String) {
        println(y.length + x)
    }
    //lambda
    val l2 = { x: Int, y: String -> println(y.length + x) }
    //调用
    l2(1, "Mike")
    //lambda表达式可以直接通过run运行
    run { l2(1, "tom") }
    //lambda简化过程
    val people = listOf(User("张三", 18), User("李四", 20))
    //1.1 函数只有lambda一个实参
    //原始完整代码
    println("年纪最大:" + people.maxBy({ user: User -> user.age }))
    //step1:如果lambda表达式是函数调用的最后一个实参，它可以放在括号外面
    println("年纪最大:" + people.maxBy() { user: User -> user.age })
    //step2： 当lambda是函数唯一的实参时，可以去掉函数调用的括号
    println("年纪最大:" + people.maxBy { user: User -> user.age })
    //step3：如果lambda的参数的类型可以推导，那么可以省略参数的类型
    println("年纪最大:" + people.maxBy { user -> user.age })
    //step4：对于lambda中一个参数时，可以使用默认参数名称it来代替命名参数，并且lambda的参数列表可以简化，省略参数列表和->
    println("年纪最大:" + people.maxBy { it.age })
    //1.2 函数有除了lambda外多个实参
    fun lambdaTest1(a: Int, b: (String) -> String) {
        println("$a + ${b(a.toString())}")
    }

    fun lambdaTest2(b: (String) -> String, a: Int) {
        println("$a + ${b(a.toString())}")
    }

    lambdaTest1(11) {
        "hello: $it"
    }

    lambdaTest2({ "hello: $it" }, 22)

    //定义匿名函数，赋值给test变量
    var test = fun(x: Int, y: Int): Int {
        return x + y
    }
    //通过test调用匿名函数
    println(test(2, 4))

    //2. 函数作为参数传递, 可以用作回调: T.()->Unit 和 ()->Unit
    //() -> Unit//表示无参数无返回值的Lambda表达式类型
    //(T) -> Unit//表示接收一个T类型参数，无返回值的Lambda表达式类型
    //(T) -> R//表示接收一个T类型参数，返回一个R类型值的Lambda表达式类型

    //()->Unit
    //2.1 不带参数和返回值的函数作为形参
    fun action0(method: () -> Unit) {
        method()
        println("this is action0")
    }

    fun method0() {
        println("this is method0 which is invoked")
    }

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

    //2.2 带参数和返回值的函数作为形参
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

    //3. 类委托
    val b = BaseImpl(10)
    Derived(b).print() // 输出 10
    Derived(b).otherPrint() //输出other

    //4. 属性委托
    val isLogin: Boolean by DerivedProperty("tom")
    if (isLogin) {
        println("this is a property when invoked")
    }

    //5. 协程
    GlobalScope.launch {
        delay(1000)
        print("World")
    }
    print("Hello ")
    Thread.sleep(2000)
    print("!\n")
    //指定运行在主线程中
    GlobalScope.launch(Dispatchers.Main) { println(Thread.currentThread().name) }

    //6. 扩展函数
    fun ExtClass.foo() = println("ext") // when the same as the member foo
    fun ExtClass.foo(para: Int) = println("ext")

    ExtClass().foo()
    ExtClass().foo(0)
    //7. 闭包：函数中包含函数
    val plus = { x: Int, y: Int -> println("$x plus $y is ${x + y}") }
    val hello = { println("Hello Kotlin") }
    fun closure(args: Array<String>) {
        { x: Int, y: Int ->
            println("$x plus $y is ${x + y}")
        }(2, 8)         // 自执行的闭包
        plus(2, 8)
        hello()
    }
}

class ExtClass {
    fun foo() = println("origin")
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