package com.foreks.android.marvel.api

import com.foreks.android.marvel.constants.PRIVATE_API_KEY
import com.foreks.android.marvel.constants.PUBLIC_API_KEY
import com.foreks.android.marvel.util.md5
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RestApi {

    @GET("characters")
    fun getMarvelCharacter(
        @Query("apikey") apiKey: String = PUBLIC_API_KEY,
        @Query("hash") hash: String = md5("1$PRIVATE_API_KEY$PUBLIC_API_KEY"),
        @Query("ts") ts: String = "1"
    ): Single<ResponseBody>

    @GET("characters/{characterId}/comics")
    fun getComics(
        @Path("characterId") id: String,
        @Query("apikey") apiKey: String = PUBLIC_API_KEY,
        @Query("hash") hash: String = md5("1$PRIVATE_API_KEY$PUBLIC_API_KEY"),
        @Query("ts") ts: String = "1"
    ): Single<ResponseBody>

}