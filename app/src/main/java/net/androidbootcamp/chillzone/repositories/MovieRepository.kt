package net.androidbootcamp.chillzone.repositories

import net.androidbootcamp.chillzone.retrofit.MovieInterface
import net.androidbootcamp.chillzone.retrofit.model.Movie
import javax.inject.Inject

class MovieRepository constructor() {

    @Inject lateinit var movieInterface: MovieInterface

    suspend fun discoverMoviesApi (key:String) : List<Movie> {
        return movieInterface.discoverMovies(1,"key", "en", "sort" )
            .results
    }
}