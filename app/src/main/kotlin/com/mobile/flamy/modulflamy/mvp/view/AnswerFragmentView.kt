package com.mobile.flamy.modulflamy.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = SkipStrategy::class)
interface AnswerFragmentView : MvpView {

    fun addQuestionToFav()
    fun delQuestionFromFav()
}