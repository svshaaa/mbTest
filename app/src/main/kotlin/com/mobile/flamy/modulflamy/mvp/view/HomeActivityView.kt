package com.mobile.flamy.modulflamy.mvp.view

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.mobile.flamy.modulflamy.api.model.answer.AnswerModel
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem

@StateStrategyType(value = AddToEndStrategy::class)
interface HomeActivityView : MvpView {
    fun onBack()

    fun showError(message: Int)

    fun showSearchFragment()
    fun showFavQuestionsFragment(TAG: Int)
    fun showAnswersFragment(questionItem: QuestionItem, answerModel: AnswerModel, TAG: Int)
}