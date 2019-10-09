package com.mobile.flamy.modulflamy.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobile.flamy.modulflamy.R
import com.mobile.flamy.modulflamy.api.model.answer.AnswerItem
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class AnswersAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mAnswersList: ArrayList<AnswerItem> = ArrayList()

    fun setUpAnswers(answersList: ArrayList<AnswerItem>){
        mAnswersList.clear()
        mAnswersList.addAll(answersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemView = layoutInflater.inflate(R.layout.cell_answer, parent, false)
        return AnswersViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mAnswersList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is AnswersViewHolder){
            holder.bind(answerItem =  mAnswersList[position])
        }
    }

    class AnswersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        private var mIvAvatar: CircleImageView = itemView.findViewById(R.id.iv_avatar_answer)
        private var mTvTitle: TextView = itemView.findViewById(R.id.tv_title_answer)

        fun bind(answerItem: AnswerItem){
            mTvTitle.text = answerItem.answer_id.toString()
            val picasso = Picasso.get()
            picasso.load(answerItem.owner.profile_image).into(mIvAvatar)
        }

    }
}