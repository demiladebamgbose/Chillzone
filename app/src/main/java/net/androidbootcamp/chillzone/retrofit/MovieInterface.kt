package net.androidbootcamp.chillzone.retrofit

import net.androidbootcamp.chillzone.retrofit.model.MovieResult
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.Executors

interface MovieInterface {
        @GET ("discover/movie")
        suspend fun  discoverMovies(@Query("page")page : Int,
                       @Query("api_key")apiKey : String,
                       @Query("language") language: String,
                       @Query("sort_by") sort_by :String
        ) : MovieResult
}

class Retrofity {
    companion object{
        private val mBaseUrl = "https://api.themoviedb.org/3/"
        val retrofit : Retrofit =
            Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(OkHttpClient.Builder()
                    .addNetworkInterceptor(HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(Executors.newSingleThreadExecutor())
                .build()
    }


}