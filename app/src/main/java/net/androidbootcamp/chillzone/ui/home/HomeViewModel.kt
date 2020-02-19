package net.androidbootcamp.chillzone.ui.home

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.androidbootcamp.chillzone.repositories.MovieRepository
import net.androidbootcamp.chillzone.retrofit.model.Movie
import net.androidbootcamp.chillzone.retrofit.model.MovieResult
import javax.inject.Inject

class HomeViewModel @Inject constructor(mRepository: MovieRepository): ViewModel(), Observable {

    var movieRepository: MovieRepository
    lateinit var movies : MovieResult
    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    @Bindable
    val num = MutableLiveData<String>().apply {
        value = "7788"
    }

    init {
        movieRepository = mRepository
    }

    fun discoverMovies (keyString: String) {
        viewModelScope.async {
            movies = movieRepository.discoverMoviesApi(keyString)
            num.value = movies.results.size.toString()

        }
    }




    val text: LiveData<String> = _text

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}