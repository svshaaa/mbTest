package com.mobile.flamy.modulflamy.ui.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AlertDialog
import android.view.ViewGroup
import android.widget.Toast
import com.mobile.flamy.modulflamy.mvp.view.BaseActivityView
import com.mobile.flamy.modulflamy.ui.fragment.BaseFragment
import com.mobile.flamy.modulflamy.R
import com.arellomobile.mvp.MvpAppCompatActivity


abstract class BaseActivity : MvpAppCompatActivity(), BaseActivityView {

    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(getLayoutRes())
    }

    abstract fun getLayoutRes(): Int

    protected fun switchFragment(fragment: BaseFragment, TAG: String) {
        fragmentManager = supportFragmentManager

        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.replace(R.id.container, fragment, TAG)
        transaction.commit()
    }

    protected fun removeFragment(fragment: BaseFragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
        transaction.remove(fragment)
        transaction.commit()
    }

    fun showMessageSnackbar(message: Int) {
        val viewGroup = (this.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
        Snackbar.make(viewGroup, message, Snackbar.LENGTH_LONG).show()
    }

    fun showMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showMessageDialog(message: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton(getString(R.string.ok), null)
        builder.setMessage(message)
        builder.show()
    }
}
