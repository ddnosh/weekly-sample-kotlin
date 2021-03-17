package com.androidwind.weekly.kotlin.other

import android.app.Activity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

// 由于 MainScope() 还没有正式发布，所以添加这个注解来避免 IDE 的警告
class SimpleScopedActivity : Activity(),
        CoroutineScope by MainScope() { // 使用 by 指定代理实现

    override fun onDestroy() {
        super.onDestroy()
        cancel() // 调用 CoroutineScope 的 cancel 函数
    }
}