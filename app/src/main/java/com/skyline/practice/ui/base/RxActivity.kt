package com.skyline.practice.ui.base

import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class RxActivity: AppCompatActivity() {

    protected val mDisposables = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        mDisposables.clear()
    }
}