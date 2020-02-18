package net.androidbootcamp.chillzone.retrofit.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie constructor(@Expose @SerializedName("popularity") var popularity : Double,
                             @Expose @SerializedName("vote_count") var voteCount : Int,
                             @Expose @SerializedName("video") var video : Boolean,
                             @Expose @SerializedName("poster_path") var posterPath : String,
                             @Expose @SerializedName("id") var id : Int,
                             @Expose @SerializedName("adult")var adult : Boolean,
                             @Expose @SerializedName("backdrop_path")var backdropPath : String,
                             @Expose @SerializedName("original_language") var originalLanguage : String,
                             @Expose @SerializedName("original_title") var originalTitle : String,
                             @Expose @SerializedName("genre_ids") var genreIds : MutableList<Int>,
                             @Expose @SerializedName("title") var title : String,
                             @Expose @SerializedName("vote_average") var voteAverage : Double,
                             @Expose @SerializedName("overview") var overview : String,
                             @Expose @SerializedName("release_date") var releaseDate : String
)

data class MovieResult constructor(@Expose @SerializedName ("page") var page : Int,
                                   @Expose @SerializedName("total_results") var totalResults : Int,
                                   @Expose @SerializedName("total_pages") var totalPages : Int,
                                   @Expose @SerializedName("results") var results : MutableList<Movie>
)

