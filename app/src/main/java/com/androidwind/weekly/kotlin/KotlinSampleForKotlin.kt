package com.androidwind.weekly.kotlin

import java.math.BigInteger

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */

fun main(args: Array<String>) {
    println("this is my first kotlin.")
    /*
    val:常量; var:变量
     */
    val name = "Jack"
//    name = "Rose" //报错

    var age = 18
    age = 20;

    //也可明确类型
    var count: Int = 100

    /*
    换行: 1. \n; 2. """ +|
     */
    val line = "here is one line\nthis is another line"
    println(line)

    val newLine = """here is one newLine
        |here is another newLine""".trimMargin()
    println(newLine)

    /*
    占位符:$
     */
    var template = "this is a template string called $name and length is ${name.length}"
    println(template)

    /*
    null: ? ?. ?: !!
     */
    var nullableString1: String? = "abc" //?可为空, 不加?则不能为空
    var nullableString2: String? = null
    println(nullableString1?.length)//?.如果为空则返回null, 不为空则返回实际值
    println(nullableString2?.length)
    println(nullableString2?.length ?: "i am null")//?:如果为空则用:后面的赋值
//    println(nullableString2!!.length)//!!如果为空则强制抛异常
    var nameNullable: String? = null
    var len = nameNullable?.length
    println(len == null)

    /*
    延迟初始化: lateinit var, by lazy
     */
    lateinit var lateInitByLateinit: String//lateinit var只能用来修饰类属性, 不能用来修饰局部变量和基本类型
    fun testLateinit() {
        lateInitByLateinit = "this is a lateinit string"
    }
    testLateinit()
    println(lateInitByLateinit)
    //by lazy用来修饰val变量, 可以用来修饰局部变量和基本类型，等下一次调用到的时候才会进行初始化
    val lazyByLazy: String by lazy {
        println("here is lazy init")
        "Zoo"
    }
    println(lazyByLazy)//lazyByLazy被调用到了，开始初始化，执行println("here is lazy init")，并且赋值"Zoo"

    /*
    is 判断类型
     */
    if ("kotlin" is String) {
        println("it's string")
    }

    /*
    as 类型转换
     */
    val y: Int? = 123;
    val x: String? = y as? String;
    println(x);

    /*
    函数
     */
    fun method1(name: String): String {//单个参数
        return "hello $name"
    }

    fun method2(name: String) = {
        "hello $name"
    }

    fun method3(name: String) = "hello $name"

    println(method1("kotlin1"))
    println(method2("kotlin2"))
    println(method3("kotlin3"))

    fun methodWithMultipleArgs(vararg name: String) {//多个参数
        println("params size is ${name.size}")
    }
    methodWithMultipleArgs("aaron")
    methodWithMultipleArgs("aaron", "beyond")

    fun methodWithOneLine(age: Int): Boolean = age == 20
    println(methodWithOneLine(20))

    //@JvmStatic and @JvmField
    println(JvmClass.name);

    /*
    Class
     */
    //open表示此类可以被继承, 用在函数上面表示此函数可以被重写
    open class KotlinClass(val name: String) {
        open fun print(content: String?) {
            println(this.name + content)
        }
    }

    val kotlinClass = KotlinClass("charles")
    kotlinClass.print(" say: hello")

    /*
    Class extends Class and Implements interface
     */
    class SubKotlinClass(name: String) : KotlinClass(name), CallBack { //父类构造函数直接赋值, 不再调用super
        override fun getName(id: Int) {
            println("id = $id")
        }

        override fun print(content: String?) {
            println(this.name + content + "!!!")
        }
    }

    val subKotlinClass = SubKotlinClass("Sub")
    subKotlinClass.print(" say: hello")

    /*
    Class with primary constructor, 主构造器定义在类头部, 因此需要init空间做初始化
     */
    class KotlinClassConstructor constructor(name: String) {
        private val name: String

        init {
            this.name = name
        }
    }

    /*
    Class with secondary constructor, 次级构造器, 可以有多个
     */
    class KotlinClassSecondaryConstructor {
        private var name: String
        private var age: Int = 0
        private var male: Boolean = false

        constructor(name: String) {
            this.name = name;
        }

        constructor(name: String, age: Int) {
            this.name = name
            this.age = age
        }

        constructor(name: String, age: Int, male: Boolean) : this(name, age) {
            this.name = name
            this.age = age
            this.male = male
        }

        fun print() {
            println(this.name)
        }
    }

    val kotlinClassSecondaryConstructor1 = KotlinClassSecondaryConstructor("Michael")
    kotlinClassSecondaryConstructor1.print()
    val kotlinClassSecondaryConstructor2 = KotlinClassSecondaryConstructor("Michael", 18, true)
    kotlinClassSecondaryConstructor2.print()

    /*
    DataClass
     */
    data class DataClassSample(val x: Int, val y: Int)

    val data = DataClassSample(100, 200)
    println(data.x + data.y)

    /*
    ArrayList and for
     */
    val names = arrayListOf<String>("dog", "cat")
    for (name in names) {
        println("names contans:$name")
    }

    for (i in 0 until names.size) {//从0一直到names.size - 1, 方便数组遍历而设计
        println("[for until]names contans:${names[i]}")
    }

    /*
    Map
     */
    val ages = mapOf<String, Int>("a" to 1, "b" to 2)
    for ((key, value) in ages) {
        println("$key -> $value")
    }

    /*
    可变数组
     */
    val bags = mutableListOf(1, 2, 3)
    bags.add(4)
    println(bags.last())
    println(bags[0])

    /*
    while
     */
    var cnt: Int = 0;
    while (cnt < 5) {
        println(cnt++)
    }

    if (cnt == 5)
        println("cnt = 5")

    /*
    when: 等价于switch
     */
    var a = 1
    val b = 2;
    val c = 3

    when (b) {
        1 -> println("the result is 1")
        2 -> {
            a = 11
            println(a)
        }
        1, 2 -> println("1 or 2")
        in 1..2 -> println("in 1 and 2")
        else -> {
            println("nothing")
        }
    }

    println(Utils1.label)
    println(Utils2.hello())
    //
    println(CompanionTest.name)
    println(CompanionTest.run())
    //
    val getterAndsetter = KotlinGetterAndSetter()
    getterAndsetter.x = 100
    println("getter and setter:" + getterAndsetter.x)

    /*
        内联函数
     */
    //let -> 闭包内使用it作为当前这个对象的参数; 返回值是函数最后一行, 或者return语句
    fun letTest(): Int {
        // fun <T, R> T.let(f: (T) -> R): R { f(this)}
        "letTest".let {
            println(it)
            return 1
        }
    }
    println(letTest())
    //apply -> 闭包内可以任意调用此对象; 并且最终也会返回此对象
    fun applyTest() {
        // fun <T> T.apply(f: T.() -> Unit): T { f(); return this }
        ArrayList<String>().apply {
            add("applyTest")
            println("this = $this, size = $size")
        }.let { println(it) }
    }
    applyTest()
    //with -> 闭包内可以任意调用此对象; 返回值是函数最后一行, 或者return语句
    fun withTest() {
        // fun <T, R> with(receiver: T, f: T.() -> R): R = receiver.f()
        with(ArrayList<String>()) {
            add("withTest")
            println("this = $this, size = $size")
        }.let { println(it) }
    }
    withTest()
    //run -> run只接收一个lambda函数为参数; 返回值是函数最后一行, 或者return语句
    fun runTest() {
        // fun <T, R> T.run(f: T.() -> R): R = f()
        "runTest".run {
            println("this = " + this)
        }.let { println(it) }
    }
    runTest()
}

/*
Object: 单例, Object修饰的类为静态类, 里面的方法和变量都是静态的
 */
object Utils1 {
    val label: String
        get() {
            if (true)
                return "here is singleton fun"
            else
                return ""
        }
}

object Utils2 {
    fun hello() {
        println("here is an object demo")
    }
}

/*
Interface
*/
interface CallBack {
    fun getName(id: Int)
}

/*
companion object:伴生对象，相当于java中的static
*/
class CompanionTest {
    companion object { //一个类中只能存在一个伴生对象
        val name: String = "Vincent"
        fun run() {
            println("I am running!")
        }
    }
}

/*
getter and setter: 自带, 默认隐藏
 */
class KotlinGetterAndSetter {
    var x: Int = 0
        set(value) {
            field = value
        }
        get() = field
}

/*
@JvmStatic and @JvmField，主要是方便java调用
*/
open class JvmClass {
    companion object {
        @JvmField
        val name: String = "jvm test"

        @JvmStatic
        fun method() {
            println("call method")
        }
    }
}