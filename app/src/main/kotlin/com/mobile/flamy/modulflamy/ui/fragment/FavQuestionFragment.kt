package com.mobile.flamy.modulflamy.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.adapters.QuestionsFavAdapter
import com.mobile.flamy.modulflamy.data.Question
import com.mobile.flamy.modulflamy.mvp.presenter.FavQuestionsFragmentPresenter
import com.mobile.flamy.modulflamy.mvp.view.FavQuestionsView
import com.mobile.flamy.modulflamy.ui.activity.HomeActivity

class FavQuestionFragment : BaseFragment(), FavQuestionsView {

    @InjectPresenter(tag = "FavQuestionFragment", type = PresenterType.GLOBAL, presenterId = "FavQuestionFragment")
    lateinit var presenter: FavQuestionsFragmentPresenter

    val TAG = FavQuestionFragment::class.java.simpleName
    private lateinit var mAdapter: QuestionsFavAdapter

    private lateinit var mSearchView: SearchView
    private lateinit var mRvFavQuestions: RecyclerView
    private lateinit var mProgressView: CircularProgressView
    private lateinit var mTvEmptyFavList: TextView

    override fun getLayoutRes(): Int = R.layout.fragment_fav_questions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSearchView = view.findViewById(R.id.fav_searchView)
        mRvFavQuestions = view.findViewById(R.id.fav_recycler_view)
        mProgressView = view.findViewById(R.id.fav_progress_view)
        mTvEmptyFavList = view.findViewById(R.id.tvEmptyFavList)

        mAdapter = QuestionsFavAdapter()
        mAdapter.setUpHomePresenter((activity as HomeActivity).presenter)
        mRvFavQuestions.adapter = mAdapter
        mRvFavQuestions.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
        presenter.startLoading()

        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                mAdapter.filter(query = newText)
                return false
            }
            override fun onQueryTextSubmit(query: String): Boolean {
                mAdapter.filter(query = query)
                return false
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mSearchView.setQuery(savedInstanceState?.getCharSequence("query"), false)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putCharSequence("query", mSearchView.query)
    }

    //MARK - view implementation

    override fun startLoading() {
        mProgressView.visibility = View.VISIBLE
        mRvFavQuestions.visibility = View.GONE
        mTvEmptyFavList.visibility = View.GONE
    }

    override fun endLoading() {
        mRvFavQuestions.visibility = View.VISIBLE
        mProgressView.visibility = View.GONE
        mTvEmptyFavList.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(activity, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun showEmptyFavList() {
        mTvEmptyFavList.visibility = View.VISIBLE
        mRvFavQuestions.visibility = View.GONE
        mProgressView.visibility = View.GONE
    }

    override fun setUpFavQuestions(quesionsList: ArrayList<Question>) {
        if(quesionsList.size == 0){
            mTvEmptyFavList.visibility = View.VISIBLE
            mRvFavQuestions.visibility = View.GONE
            mProgressView.visibility = View.GONE
        }else {
            mRvFavQuestions.visibility = View.VISIBLE
            mTvEmptyFavList.visibility = View.GONE
            mProgressView.visibility = View.GONE
            mAdapter.setUpQuestions(questionsList = quesionsList)
        }
    }
}