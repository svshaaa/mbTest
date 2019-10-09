package com.mobile.flamy.modulflamy.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.mobile.flamy.modulflamy.mvp.presenter.SearchFragmentPresenter
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.adapters.QuestionsAdapter
import com.mobile.flamy.modulflamy.mvp.view.SearchFragmentView
import com.mobile.flamy.modulflamy.ui.activity.HomeActivity
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchFragment : BaseFragment(), SearchFragmentView {

    @InjectPresenter(tag = "SearchFragment", type = PresenterType.GLOBAL, presenterId = "SearchFragment")
    lateinit var presenter: SearchFragmentPresenter

    val TAG = SearchFragment::class.java.simpleName
    private lateinit var mAdapter: QuestionsAdapter

    private lateinit var mSearchView: SearchView
    private lateinit var mRvQuestions: RecyclerView
    private lateinit var mProgressView: CircularProgressView
    private lateinit var mTvEmptyList: TextView
    private lateinit var mTvFavCounter: TextView
    private lateinit var mFab: FloatingActionButton
    private var mQuery: CharSequence? = null

    override fun getLayoutRes(): Int = R.layout.fragment_search

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSearchView = view.findViewById(R.id.searchView)
        mRvQuestions = view.findViewById(R.id.recycler_view)
        mProgressView = view.findViewById(R.id.progress_view)
        mTvFavCounter = view.findViewById(R.id.tv_fav_counter)
        mTvEmptyList = view.findViewById(R.id.tvEmptyList)
        mFab = view.findViewById(R.id.fab_fav)

        Observable.create(ObservableOnSubscribe<String> { subscriber ->
            mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    subscriber.onNext(newText!!)
                    return false
                }
                override fun onQueryTextSubmit(query: String?): Boolean {
                    subscriber.onNext(query!!)
                    return false
                }
            })
        })
                .map {text -> text.trim()}
                .debounce(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { text -> text.isNotBlank() }
                .subscribe { text ->
                    presenter.getQuestions(text)
                    Log.e(TAG, "subscriber: $text")
                }

        mAdapter = QuestionsAdapter()
        mAdapter.setUpHomePresenter((activity as HomeActivity).presenter)
        mRvQuestions.adapter = mAdapter
        mRvQuestions.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
        mRvQuestions.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && mFab.isShown())
                    mFab.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    mFab.show()
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        mFab.setOnClickListener {
            (activity as HomeActivity).presenter.showFavQuestionsFragment()
        }

        presenter.updateteFavCounter()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mQuery = savedInstanceState?.getCharSequence("query")
        Log.e(TAG, " onActivityCreated: query = $mQuery")
        mSearchView.setQuery(savedInstanceState?.getCharSequence("query"), false)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.e(TAG, " onSaveInstanceState: query = ${mSearchView.query}")
        outState?.putCharSequence("query", mSearchView.query)
    }

    //MARK - view implementation

    override fun startLoading() {
        Log.e(TAG, "startLoading")
        mProgressView.visibility = View.VISIBLE
        mRvQuestions.visibility = View.GONE
        mTvEmptyList.visibility = View.GONE
        mTvFavCounter.visibility = View.GONE
        mFab.visibility = View.GONE
    }

    override fun endLoading() {
        Log.e(TAG, "endLoading")
        mRvQuestions.visibility = View.VISIBLE
        mTvFavCounter.visibility = View.VISIBLE
        mFab.visibility = View.VISIBLE
        mProgressView.visibility = View.GONE
        mTvEmptyList.visibility = View.GONE
    }

    override fun setUpQuestions(quesionsList: ArrayList<QuestionItem>) {
        Log.e(TAG, "setUpQuestions")
        mRvQuestions.visibility = View.VISIBLE
        mTvFavCounter.visibility = View.VISIBLE
        mFab.visibility = View.VISIBLE
        mTvEmptyList.visibility = View.GONE
        mProgressView.visibility = View.GONE
        mAdapter.setUpQuestions(questionsList = quesionsList)
    }

    override fun showEmptyList() {
        Log.e(TAG, "showEmptyList")
        mTvEmptyList.visibility = View.VISIBLE
        mTvEmptyList.text = getString(R.string.no_answers)
        mRvQuestions.visibility = View.GONE
        mTvFavCounter.visibility = View.GONE
        mFab.visibility = View.GONE
        mProgressView.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(activity, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun updateFavCounter(count: Int) {
        mTvFavCounter.text = count.toString()
    }


}
