package com.mobile.flamy.modulflamy.data

import io.reactivex.Flowable

class QuestionDaoManager(private val questionDao: QuestionDao) {

    fun getAllQuestions(): Flowable<List<Question>> {
        return this.questionDao.getAllQuestions()
    }

    fun insertQuestion(question: Question){
        return this.questionDao.insertQuestion(question = question)
    }

    fun deleteQuestion(question: Question){
        return this.questionDao.deleteQuestion(question = question)
    }

    fun findQuestionByQuestionId(questionId: Long): Question{
        return this.questionDao.findQuestionByQuestionId(questionId = questionId)
    }
}