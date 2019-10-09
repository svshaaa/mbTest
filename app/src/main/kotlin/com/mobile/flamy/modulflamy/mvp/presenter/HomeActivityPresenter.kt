package com.mobile.flamy.modulflamy.mvp.presenter

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.api.ApiManager
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.mobile.flamy.modulflamy.data.Question
import com.mobile.flamy.modulflamy.data.QuestionDaoManager
import com.mobile.flamy.modulflamy.mvp.view.HomeActivityView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class HomeActivityPresenter : BasePresenter<HomeActivityView>() {

    private val disposeBag = CompositeDisposable()
    var questionList : ArrayList<Question> = ArrayList()

    @Inject
    lateinit var apiManager: ApiManager

    @Inject
    lateinit var questionDaoManager: QuestionDaoManager

    @Inject
    lateinit var context: Context

    init {
        getAppComponent().inject(this)
    }

    fun getAnswers(questionItem: QuestionItem, questionsId: Long, TAG: Int){
        disposeBag.add(apiManager.getAnswers(questiodId = questionsId)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if(it.items.size == 0){
                        viewState.showError(R.string.no_answers)
                    }else {
                        viewState.showAnswersFragment(questionItem = questionItem, answerModel = it, TAG = TAG)
                    }
                }, {
                })
        )
    }

    fun onBackPressed(){
        viewState.onBack()
    }

    fun showSearchFragment() {
        viewState.showSearchFragment()
    }

    fun showFavQuestionsFragment() {
        viewState.showFavQuestionsFragment(R.string.fromSearchToFavTAG)
    }

}