package net.androidbootcamp.chillzone.repositories

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.callbackFlow
import net.androidbootcamp.chillzone.retrofit.MovieInterface
import net.androidbootcamp.chillzone.retrofit.model.Movie
import net.androidbootcamp.chillzone.retrofit.model.MovieResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import javax.inject.Inject

class MovieRepository constructor(mInterface: MovieInterface) {
    var movieInterface:MovieInterface

    init {
        movieInterface = mInterface
    }

    var callback: Callback<MovieResult> = object :Callback<MovieResult> {
        override fun onResponse(call:Call<MovieResult>, response: Response<MovieResult>):Unit  {

        }
        override fun onFailure(call:Call<MovieResult>, t:Throwable):Unit  {

        }

    }


    suspend fun discoverMoviesApi (key:String) : MovieResult {
        return movieInterface.discoverMovies(1,key, "en", "sort" )
    }
}