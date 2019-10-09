package com.mobile.flamy.modulflamy.api

import com.mobile.flamy.modulflamy.api.model.question.QuestionModel
import com.mobile.flamy.modulflamy.api.model.answer.AnswerModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search?pagesize=30&fromdate=1493596800&order=desc&sort=activity&site=stackoverflow&filter=!.FjtmoGIogpSTdXxMSmzZb*32ax4i")
    fun getQuestions(@Query("page") page: Int, @Query("intitle") tag: String): Observable<QuestionModel>

    @GET("questions/{ids}/answers?page=1&pagesize=5&order=desc&sort=activity&site=stackoverflow&filter=!)Q2AgQTb-WmAUBSAtye3vNCA")
    fun getAnswers(@Path("ids") questionId: Long): Observable<AnswerModel>

}