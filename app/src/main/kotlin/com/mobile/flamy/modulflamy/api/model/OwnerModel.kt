package com.mobile.flamy.modulflamy.api.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OwnerModel (
    val reputation: Int,
    val profile_image: String,
    val display_name: String
) : Parcelable

