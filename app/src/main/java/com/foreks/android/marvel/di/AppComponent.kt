package com.foreks.android.marvel.di


import android.content.Context
import com.foreks.android.marvel.api.RestApi
import com.foreks.android.marvel.application.AppConfig
import com.foreks.android.marvel.constants.URL_WEB
import com.foreks.android.marvel.modules.repo.MarvelRepo
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Component(modules = [AppModule::class, NetModule::class])
@AppScope
interface AppComponent {
    fun getContext(): Context
    fun getApiInterface(): RestApi
    fun getRetrofit(): Retrofit

}

interface BaseAppComponentBuilder<out T, out K> {
    fun appComponent(appComponent: AppComponent = AppConfig.appComponent): T
    fun build(): K
}


@Module
class AppModule(private val context: Context) {

    @Provides
    @AppScope
    fun provideContext(): Context {
        return context
    }
}

@Module
class MarvelRepoModule(private val restApi: RestApi) {
    @Provides
    @AppScope
    fun provideMarvelRepo() = MarvelRepo(restApi)
}


@Module
class NetModule {
    @Provides
    @AppScope
    internal fun provideNetworkService(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }

    @Provides
    @AppScope
    internal fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL_WEB)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
    }
}