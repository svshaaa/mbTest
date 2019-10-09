package com.mobile.flamy.modulflamy.api

import com.mobile.flamy.modulflamy.api.model.question.QuestionModel
import com.mobile.flamy.modulflamy.api.model.answer.AnswerModel
import io.reactivex.Observable

class ApiManager(service: ApiService) {

    val apiService: ApiService = service

    fun getQuestions(page: Int, tag: String): Observable<QuestionModel> {
        return apiService.getQuestions(page = page, tag = tag)
    }

    fun getAnswers(questiodId: Long): Observable<AnswerModel> {
        return apiService.getAnswers(questionId = questiodId)
    }
}