package com.foreks.android.marvel.api

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface RestApi {

    @GET("search?")
    fun getEntity(
        @Query("term") term: String = "jack+johnson",
        @Query("entity") entity: String

    ): Single<ResponseBody>

    @GET("search?")
    fun getAll(
        @Query("term") term: String = "jack+johnson"
    ): Single<ResponseBody>

}