package com.foreks.android.marvel.modules.repo


import android.annotation.SuppressLint
import android.os.Parcelable
import com.foreks.android.marvel.api.RestApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize
import okhttp3.ResponseBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class MarvelRepo(
    private val restApi: RestApi
) {

    fun singleMarvelCharacters(): Single<List<MarvelCharacter>> {
        return restApi.getMarvelCharacter()
            .map {
                parseMarvelCharacters(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun singleComics(id: String): Single<List<Comics>> {
        return restApi.getComics(id)
            .map {
                parseComics(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    private fun parseMarvelCharacters(responseBody: ResponseBody): List<MarvelCharacter> {
        val marvelCharacterList = mutableListOf<MarvelCharacter>()

        val jsonObject = JSONObject(responseBody.string())
        jsonObject.optJSONObject("data")?.optJSONArray("results")?.let { jsonArray ->


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

    @SuppressLint("SimpleDateFormat")
    private fun parseComics(responseBody: ResponseBody): List<Comics> {
        val comicsList = mutableListOf<Comics>()

        val jsonObject = JSONObject(responseBody.string())
        val dataObject = jsonObject.optJSONObject("data")
        val totalItemSize = dataObject?.optString("total") ?: "-"


        dataObject?.optJSONArray("results")?.let { jsonArray ->
            for (x in 0 until jsonArray.length()) {
                val jsonObjectData = jsonArray.getJSONObject(x)

                val dtStart = jsonObjectData.optString("modified").replaceAfter("T","")
                val format = SimpleDateFormat("yyyy-MM-dd")
                val date: Date = format.parse(dtStart)


                val imageUrl = jsonObjectData.optJSONObject("thumbnail")?.let { thumbnail ->
                    thumbnail.optString("path") + "." + thumbnail.optString("extension")
                }
                comicsList.add(
                    Comics(
                        title = jsonObjectData.optString("title") ?: "-",
                        image = imageUrl ?: "-",
                        description = jsonObjectData.optString("description") ?: "-",
                        date = date
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
    val description: String,
    val date: Date
) : Parcelable

