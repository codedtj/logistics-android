package com.example.duoblogistics.internal.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, tag: String){
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, tag: String?) {
    supportFragmentManager.inTransaction{replace(frameId, fragment, tag)}
}

fun AppCompatActivity.replaceFragmentWithBackStack(fragment: Fragment, frameId: Int, tag: String?, backStackTag: String?) {
    supportFragmentManager.inTransaction{
        addToBackStack(backStackTag)
        replace(frameId, fragment, tag)
    }
}