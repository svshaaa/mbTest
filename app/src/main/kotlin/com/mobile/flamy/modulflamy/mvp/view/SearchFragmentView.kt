package com.mobile.flamy.modulflamy.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.*
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface SearchFragmentView : MvpView {

    @StateStrategyType(value = SkipStrategy::class)
    fun startLoading()

    @StateStrategyType(value = SkipStrategy::class)
    fun endLoading()

    @StateStrategyType(value = SkipStrategy::class)
    fun showError(textResource: Int)

    fun showEmptyList()
    fun updateFavCounter(count: Int)
    fun setUpQuestions(quesionsList: ArrayList<QuestionItem>)


}