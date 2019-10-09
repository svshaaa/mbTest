package com.mobile.flamy.modulflamy.data

import android.arch.persistence.room.*
import io.reactivex.Flowable

@Dao
interface QuestionDao {

    @Query("select * from question")
    fun getAllQuestions(): Flowable<List<Question>>

    @Query("select * from question where question_id = :questionId")
    fun findQuestionByQuestionId(questionId: Long): Question

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQuestion(question: Question)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateQuestion(question: Question)

    @Delete
    fun deleteQuestion(question: Question)
}