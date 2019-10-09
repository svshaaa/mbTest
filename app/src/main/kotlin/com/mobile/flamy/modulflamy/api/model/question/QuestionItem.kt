package com.mobile.flamy.modulflamy.api.model.question

import android.os.Parcelable
import com.mobile.flamy.modulflamy.api.model.OwnerModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionItem (

        val owner: OwnerModel,
        val is_answered: Boolean,
        val answer_count: Int,
        val question_id: Long,
        val title: String

) : Parcelable
