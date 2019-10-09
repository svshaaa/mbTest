package com.mobile.flamy.modulflamy.di.components

import com.mobile.flamy.modulflamy.di.modules.ApiModule
import com.mobile.flamy.modulflamy.di.modules.BusModule
import com.mobile.flamy.modulflamy.di.modules.ContextModule
import com.mobile.flamy.modulflamy.di.modules.RoomModule
import com.mobile.flamy.modulflamy.mvp.presenter.AnswerFragmentPresenter
import com.mobile.flamy.modulflamy.mvp.presenter.FavQuestionsFragmentPresenter
import com.mobile.flamy.modulflamy.mvp.presenter.HomeActivityPresenter
import com.mobile.flamy.modulflamy.mvp.presenter.SearchFragmentPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(ApiModule::class, ContextModule::class, BusModule::class, RoomModule::class))
interface ApplicationComponent {

    fun inject(homeActivityPresenter: HomeActivityPresenter)
    fun inject(searchFragmentPresenter: SearchFragmentPresenter)
    fun inject(answerFragmentPresenter: AnswerFragmentPresenter)
    fun inject(favQuestionsFragmentPresenter: FavQuestionsFragmentPresenter)

}