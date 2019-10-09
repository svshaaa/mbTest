package com.mobile.flamy.modulflamy.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.mobile.flamy.modulflamy.mvp.view.BaseFragmentView

abstract class BaseFragment : MvpAppCompatFragment(), BaseFragmentView {

    lateinit var snackbar: Snackbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(getLayoutRes(), container, false)
    }

    abstract fun getLayoutRes(): Int

    fun showMessageSnackbar(message: Int) {
        snackbar = Snackbar.make(view!!, message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

}