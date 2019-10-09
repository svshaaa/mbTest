package com.mobile.flamy.modulflamy.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mobile.flamy.modulflamy.api.model.OwnerModel
import com.mobile.flamy.modulflamy.api.model.question.QuestionItem
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.data.Question
import com.mobile.flamy.modulflamy.mvp.presenter.HomeActivityPresenter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class QuestionsFavAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mQuestionsList: ArrayList<QuestionItem> = ArrayList()
    private var mQuestionsSourceList: ArrayList<QuestionItem> = ArrayList()
    lateinit var homePresenter: HomeActivityPresenter

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemView =layoutInflater.inflate(R.layout.cell_question, parent, false)
        return QuestionViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mQuestionsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is QuestionViewHolder){
            holder.bind(presenter = homePresenter, questionItem = mQuestionsList[position])
        }
    }

    fun setUpQuestions(questionsList: ArrayList<Question>){
        this.mQuestionsSourceList.clear()
        questionsList.forEach{
            var questionItem: QuestionItem = QuestionItem(
                    owner = OwnerModel(reputation = it.reputation, profile_image = it.profileImage, display_name = it.displayName),
                    is_answered = it.isAnswered,
                    answer_count = it.answerCount,
                    question_id = it.questionId,
                    title = it.title)
            this.mQuestionsSourceList.add(questionItem)
        }
        filter(query = "")
        notifyDataSetChanged()
    }

    fun setUpHomePresenter(presenter: HomeActivityPresenter){
        this.homePresenter = presenter
    }

    fun filter(query: String){
        mQuestionsList.clear()
        mQuestionsSourceList.forEach{
            if(it.title.contains(query))
                mQuestionsList.add(it)
        }
        notifyDataSetChanged()
    }

    class QuestionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private var mIvAvatar: CircleImageView = itemView.findViewById(R.id.iv_avatar)
        private var mTvTitle: TextView = itemView.findViewById(R.id.tv_title)
        private var mTvAnswerCount: TextView = itemView.findViewById(R.id.tv_answer_count)
        private var mRootQuestion: LinearLayout = itemView.findViewById(R.id.root_question)

        fun bind(presenter: HomeActivityPresenter, questionItem: QuestionItem){
            mTvTitle.text = questionItem.title
            if(questionItem.is_answered) {
                mTvAnswerCount.visibility = View.VISIBLE
                mTvAnswerCount.text = questionItem.answer_count.toString()
            }else{
                mTvAnswerCount.visibility = View.GONE
            }
            val picasso = Picasso.get()
            picasso.load(questionItem.owner.profile_image).into(mIvAvatar)

            mRootQuestion.setOnClickListener {
                presenter.getAnswers(questionItem = questionItem, questionsId = questionItem.question_id, TAG = R.string.fromFavToAnsTAG)
            }
        }


    }
}