package com.mobile.flamy.modulflamy.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.adapters.AnswersAdapter
import com.mobile.flamy.modulflamy.api.model.answer.AnswerItem
import com.mobile.flamy.modulflamy.api.model.answer.AnswerModel
import com.mobile.flamy.modulflamy.mvp.presenter.AnswerFragmentPresenter
import com.mobile.flamy.modulflamy.mvp.view.AnswerFragmentView

class AnswerFragment : BaseFragment(), AnswerFragmentView {

        @InjectPresenter(tag = "AnswerFragment", type = PresenterType.GLOBAL, presenterId = "AnswerFragment")
        lateinit var presenter: AnswerFragmentPresenter

        val TAG = AnswerFragment::class.java.simpleName
        private lateinit var mAdapter: AnswersAdapter

        private lateinit var mRvAnswers: RecyclerView
        private lateinit var mProgressView: CircularProgressView
        private lateinit var mIvBack: ImageView
        private lateinit var mIvFavorite: ImageView

        private var mAnswersList : ArrayList<AnswerItem> = ArrayList()
        private lateinit var mQuestionItem : QuestionItem

        override fun getLayoutRes(): Int = R.layout.fragment_answer

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)

                mAnswersList = arguments.getParcelable<AnswerModel>("answerModel").items
                mQuestionItem = arguments.getParcelable("questionItem")

                mRvAnswers = view.findViewById(R.id.rv_answers)
                mProgressView = view.findViewById(R.id.progress_view_answer)
                mIvBack = view.findViewById(R.id.iv_back)
                mIvFavorite = view.findViewById(R.id.iv_favorites)

                mAdapter = AnswersAdapter()
                mRvAnswers.adapter = mAdapter
                mRvAnswers.layoutManager = LinearLayoutManager(context, OrientationHelper.VERTICAL, false)
                mAdapter.setUpAnswers(answersList = mAnswersList)

                presenter.checkFav(questionItem = mQuestionItem)

                mIvFavorite.setOnClickListener {
                        presenter.addQuestionToFav(questionItem = mQuestionItem)
                }

                mIvBack.setOnClickListener {
                        activity.onBackPressed()
                }

        }

        //MARK - view implementation

        override fun addQuestionToFav() {
               mIvFavorite.setBackgroundResource(R.drawable.ic_favorite)
        }

        override fun delQuestionFromFav() {
                mIvFavorite.setBackgroundResource(R.drawable.ic_favorite_border)
        }

}
