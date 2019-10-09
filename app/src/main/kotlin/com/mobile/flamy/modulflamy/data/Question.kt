package com.mobile.flamy.modulflamy.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(tableName = "Question")
data class Question(@ColumnInfo(name = "is_answered") var isAnswered: Boolean = false,
                    @ColumnInfo(name = "profile_image") var profileImage: String,
                    @ColumnInfo(name = "display_name") var displayName: String,
                    @ColumnInfo(name = "reputation") var reputation: Int,
                    @ColumnInfo(name = "answer_count") var answerCount: Int,
                    @ColumnInfo(name = "question_id") @PrimaryKey var questionId: Long,
                    @ColumnInfo(name = "title") var title: String)