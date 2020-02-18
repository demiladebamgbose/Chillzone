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

    init {
        movieRepository = mRepository
    }

    fun discoverMovies (keyString: String): Deferred<MovieResult> {
        return viewModelScope.async {
            movieRepository.discoverMoviesApi(keyString)
        }
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    @Bindable
    val num = MutableLiveData<String>().apply {
        value = "7788"
    }

    val text: LiveData<String> = _text

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }
}