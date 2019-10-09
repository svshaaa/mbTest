package com.mobile.flamy.modulflamy.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.api.ApiManager
import com.mobile.flamy.modulflamy.data.Question
import com.mobile.flamy.modulflamy.data.QuestionDaoManager
import com.mobile.flamy.modulflamy.mvp.view.FavQuestionsView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class FavQuestionsFragmentPresenter: BasePresenter<FavQuestionsView>() {

    private val disposeBag = CompositeDisposable()
    var questionList : ArrayList<Question> = ArrayList()

    @Inject
    lateinit var apiManager: ApiManager

    @Inject
    lateinit var questionDaoManager: QuestionDaoManager

    val TAG = FavQuestionsFragmentPresenter::class.java.simpleName

    init {
        getAppComponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
        disposeBag.clear()
    }


    fun startLoading(){
        viewState.startLoading()
        disposeBag.add(questionDaoManager.getAllQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.endLoading()
                    questionList.clear()
                    questionList.addAll(it)
                    viewState.setUpFavQuestions(quesionsList = questionList)
                },{
                    viewState.showEmptyFavList()
                })
        )
    }

}