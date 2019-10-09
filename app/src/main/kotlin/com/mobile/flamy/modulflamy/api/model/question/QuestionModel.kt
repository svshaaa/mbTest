package com.mobile.flamy.modulflamy.api.model.question

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionModel (
        val items: ArrayList<QuestionItem>
) : Parcelable