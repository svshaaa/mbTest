package com.mobile.flamy.modulflamy.api.model.answer

import android.os.Parcelable
import com.mobile.flamy.modulflamy.api.model.OwnerModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswerItem (
        val owner: OwnerModel,
        val is_accepted: Boolean,
        val score: Int,
        val answer_id: Long,
        val creation_date: Long,
        val question_id: Long,
        val title: String,
        val body_markdown: String
) : Parcelable
