package com.mobile.flamy.modulflamy.mvp.presenter

import android.support.annotation.VisibleForTesting
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.mobile.flamy.modulflamy.App
import com.mobile.flamy.modulflamy.di.components.ApplicationComponent

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    private val disposables = CompositeDisposable()

    protected fun unsubscribeOnDestroy(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    fun getAppComponent(): ApplicationComponent = App.mApplicationComponent
}
