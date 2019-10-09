package com.mobile.flamy.modulflamy.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.mobile.flamy.modulflamy.api.model.answer.AnswerModel
import com.mobile.flamy.modulflamy.data.Question

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface FavQuestionsView : MvpView {

    @StateStrategyType(value = SkipStrategy::class)
    fun startLoading()

    @StateStrategyType(value = SkipStrategy::class)
    fun endLoading()

    @StateStrategyType(value = SkipStrategy::class)
    fun showError(textResource: Int)

    fun showEmptyFavList()
    fun setUpFavQuestions(quesionsList: ArrayList<Question>)
}