package com.mobile.flamy.modulflamy.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.api.ApiManager
import com.mobile.flamy.modulflamy.data.QuestionDaoManager
import com.mobile.flamy.modulflamy.mvp.view.SearchFragmentView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@InjectViewState
class SearchFragmentPresenter : BasePresenter<SearchFragmentView>() {

    private val disposeBag = CompositeDisposable()

    @Inject
    lateinit var apiManager: ApiManager

    @Inject
    lateinit var questionDaoManager: QuestionDaoManager

    val TAG = SearchFragmentPresenter::class.java.simpleName
    val PAGE_COUNT = 1

    init {
        getAppComponent().inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposeBag.dispose()
        disposeBag.clear()
    }

    fun getQuestions(tag: String?) {
        if (tag != null) {
            viewState.startLoading()
            disposeBag.add(apiManager.getQuestions(page = PAGE_COUNT, tag = tag)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Log.e(TAG, it.toString())
                        if(it.items.size == 0){
                            viewState.showEmptyList()
                        }else {
                            viewState.endLoading()
                            viewState.setUpQuestions(quesionsList = it.items)
                        }
                    }, {
                        Log.e(TAG, it.toString())
                    })
            )
        }
    }

    fun updateteFavCounter(){
        disposeBag.add(questionDaoManager.getAllQuestions()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.updateFavCounter(it.size)
                },{
                })
        )
    }

}