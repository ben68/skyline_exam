package com.skyline.practice.ui

import android.os.Bundle
import com.skyline.practice.R
import com.skyline.practice.replaceWith
import com.skyline.practice.ui.base.RxActivity
import com.skyline.practice.ui.home.HomeActivity
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class MainActivity: RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        Observable.timer(1500, TimeUnit.MILLISECONDS)
                .subscribe {
                    replaceWith(HomeActivity::class.java)
                }
                .let {
                    mDisposables.add(it)
                }
    }
}