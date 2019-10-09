package com.mobile.flamy.modulflamy.api.model.answer

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AnswerModel (
        val items: ArrayList<AnswerItem>
) : Parcelable