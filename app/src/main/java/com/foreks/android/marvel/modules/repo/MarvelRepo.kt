package com.foreks.android.marvel.modules.repo


import android.os.Parcelable
import com.foreks.android.marvel.api.RestApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import okhttp3.ResponseBody
import org.json.JSONObject

class MarvelRepo(
    private val restApi: RestApi
) {

    fun singleMarvelCharacters(): Single<List<MarvelCharacter>> {
        return restApi.getAll()
            .map {
                parseMarvelCharacters(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun singleComics(): Single<List<Comics>> {
        return restApi.getAll()
            .map {
                parseComics(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    private fun parseMarvelCharacters(responseBody: ResponseBody): List<MarvelCharacter> {
        val marvelCharacterList = mutableListOf<MarvelCharacter>()

        val jsonObject = JSONObject(responseBody.string())
        jsonObject.optJSONArray("results")?.let { jsonArray ->


            for (x in 0 until jsonArray.length()) {
                val jsonObjectData = jsonArray.getJSONObject(x)
                val date = jsonObjectData.optString("modified")


                val imageUrl = jsonObjectData.optJSONObject("thumbnail")?.let { thumbnail ->
                    thumbnail.optString("path") + "." + thumbnail.optString("extension")
                }

                val dateDisplay =
                    if (date.isNotEmpty() && date.length > 10) {
                        date.substring(5, 7) + "." + date.substring(8, 10) + "." + date.substring(
                            0,
                            4
                        )
                    } else {
                        "-"
                    }
                marvelCharacterList.add(
                    MarvelCharacter(
                        id = jsonObjectData.optString("id") ?: "-",
                        name = jsonObjectData.optString("name") ?: "-",
                        description = jsonObjectData.optString("description") ?: "-",
                        modified = dateDisplay,
                        image = imageUrl ?: "-"
                    )
                )
            }
        }

        return marvelCharacterList.toList()
    }

    private fun parseComics(responseBody: ResponseBody): List<Comics> {
        val comicsList = mutableListOf<Comics>()

        val jsonObject = JSONObject(responseBody.string())
        val dataObject = jsonObject.optJSONObject("data")
        val totalItemSize = dataObject?.optString("total") ?: "-"
        dataObject?.optJSONArray("results")?.let { jsonArray ->
            for (x in 0 until jsonArray.length()) {
                val jsonObjectData = jsonArray.getJSONObject(x)


                val imageUrl = jsonObjectData.optJSONObject("thumbnail")?.let { thumbnail ->
                    thumbnail.optString("path") + "." + thumbnail.optString("extension")
                }
                comicsList.add(
                    Comics(
                        title = jsonObjectData.optString("title") ?: "-",
                        image = imageUrl ?: "-",
                        description = jsonObjectData.optString("description") ?: "-"
                    )
                )
            }
        }

        return comicsList.toList()
    }
}


@Parcelize
data class MarvelCharacter(
    val id: String,
    val name: String,
    val description: String,
    val modified: String,
    val image: String
) : Parcelable

@Parcelize
data class Comics(
    val title: String,
    val image: String,
    val description: String
) : Parcelable

