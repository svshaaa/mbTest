package com.mobile.flamy.modulflamy.mvp.presenter

import com.arellomobile.mvp.InjectViewState
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.mobile.flamy.modulflamy.api.ApiManager
import com.mobile.flamy.modulflamy.data.Question
import com.mobile.flamy.modulflamy.data.QuestionDaoManager
import com.mobile.flamy.modulflamy.mvp.view.AnswerFragmentView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class AnswerFragmentPresenter: BasePresenter<AnswerFragmentView>() {

    @Inject
    lateinit var apiManager: ApiManager

    @Inject
    lateinit var questionDaoManager: QuestionDaoManager

    private val disposeBag = CompositeDisposable()

    val TAG = AnswerFragmentPresenter::class.java.simpleName

    init {
        getAppComponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
        disposeBag.clear()
    }

    fun addQuestionToFav(questionItem: QuestionItem){
        disposeBag.add(Observable.fromCallable { questionDaoManager.findQuestionByQuestionId(questionId = questionItem.question_id) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    delFromFav(questionItem = it)
                    viewState.delQuestionFromFav()
                },{
                    addToFav(questionItem = questionItem)
                    viewState.addQuestionToFav()
                }))
    }

    fun addToFav(questionItem: QuestionItem){
        val newQuestion = Question(isAnswered = questionItem.is_answered,
                profileImage = questionItem.owner.profile_image,
                reputation = questionItem.owner.reputation,
                displayName = questionItem.owner.display_name,
                answerCount = questionItem.answer_count,
                questionId = questionItem.question_id,
                title = questionItem.title)

        disposeBag.add(Observable.fromCallable { questionDaoManager.insertQuestion(newQuestion) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun delFromFav(questionItem: Question){
        disposeBag.add(Observable.fromCallable { questionDaoManager.deleteQuestion(questionItem) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
    }

    fun checkFav(questionItem: QuestionItem){
        disposeBag.add(Observable.fromCallable { questionDaoManager.findQuestionByQuestionId(questionId = questionItem.question_id) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.addQuestionToFav()
                },{
                    viewState.delQuestionFromFav()
                }))
    }

}