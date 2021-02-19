package com.androidwind.weekly.kotlin.other;

public interface CallBackFromJava<T> {
    /**
     * Called when the data is changed.
     * @param t  The new data
     */
    void onChanged(T t);
}
