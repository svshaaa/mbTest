package com.mobile.flamy.modulflamy.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.PresenterType
import com.mobile.flamy.modulflamy.mvp.presenter.HomeActivityPresenter
import com.mobile.flamy.modulflamy.mvp.view.HomeActivityView
import com.mobile.flamy.modulflamy.ui.fragment.SearchFragment
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.api.model.answer.AnswerModel
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.mobile.flamy.modulflamy.ui.fragment.AnswerFragment
import com.mobile.flamy.modulflamy.ui.fragment.BaseFragment
import com.mobile.flamy.modulflamy.ui.fragment.FavQuestionFragment


class HomeActivity : BaseActivity(), HomeActivityView {

    @InjectPresenter(tag = "HomeActivity", type = PresenterType.GLOBAL, presenterId = "HomeActivity")
    lateinit var presenter: HomeActivityPresenter

    val TAG = HomeActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState != null) return

        switchFragment(SearchFragment(), getString(R.string.toSearchTAG))
    }

    fun switchFragmentFromFragment(fragment: BaseFragment, TAG: String){
        switchFragment(fragment = fragment, TAG = TAG)
    }

    override fun getLayoutRes(): Int =
            R.layout.activity_home

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    //MARK - view implementation

    override fun onBack() {
        val searchFragment = fragmentManager.findFragmentByTag(getString(R.string.toSearchTAG))
        if (searchFragment != null && searchFragment!!.isVisible()) {
            super.onBackPressed()
        }
        val favQuestionFragment = fragmentManager.findFragmentByTag(getString(R.string.fromSearchToFavTAG))
        if(favQuestionFragment != null && favQuestionFragment!!.isVisible()){
            presenter.showSearchFragment()
        }
        val answerFragmentFromFav = fragmentManager.findFragmentByTag(getString(R.string.fromFavToAnsTAG))
        if(answerFragmentFromFav != null && answerFragmentFromFav!!.isVisible()){
            presenter.showFavQuestionsFragment()
        }
        val answerFragmentFromSearch = fragmentManager.findFragmentByTag(getString(R.string.fromSearchToAnsTAG))
        if(answerFragmentFromSearch != null && answerFragmentFromSearch!!.isVisible()){
            presenter.showSearchFragment()
        }
    }

    override fun showSearchFragment() {
        switchFragment(SearchFragment(), getString(R.string.toSearchTAG))
    }

    override fun showFavQuestionsFragment(TAG: Int) {
        switchFragment(FavQuestionFragment(), getString(TAG))
    }

    override fun showAnswersFragment(questionItem: QuestionItem, answerModel: AnswerModel, TAG: Int) {
        val answerFragment = AnswerFragment()
        val bundle = Bundle()
        bundle.putParcelable("answerModel", answerModel)
        bundle.putParcelable("questionItem", questionItem)
        answerFragment.arguments = bundle
        switchFragmentFromFragment(fragment = answerFragment, TAG = getString(TAG))
    }

    override fun showError(message: Int) {
        Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
    }

}
