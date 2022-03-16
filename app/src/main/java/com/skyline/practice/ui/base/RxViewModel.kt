package com.skyline.practice.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class RxViewModel: ViewModel() {
    protected val mDisposables = CompositeDisposable()

    val isLoading = MutableLiveData(false)

    override fun onCleared() {
        super.onCleared()
        mDisposables.clear()
    }

    protected fun observe(fun1: () -> Disposable) {
        fun1().let {
            mDisposables.add(it)
        }
    }
}