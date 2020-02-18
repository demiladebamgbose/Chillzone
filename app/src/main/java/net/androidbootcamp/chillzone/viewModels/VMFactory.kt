package net.androidbootcamp.chillzone.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.repositories.MovieRepository
import net.androidbootcamp.chillzone.ui.gallery.GalleryViewModel
import net.androidbootcamp.chillzone.ui.home.HomeViewModel
import javax.inject.Inject

class VMFactory constructor(chillApp: ChillApp): ViewModelProvider.Factory {
    @Inject lateinit var movieRepository:MovieRepository

    init {
        chillApp.appComponent.inject(this)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass. isAssignableFrom(GalleryViewModel::class.java)) {
            return GalleryViewModel() as T
        } else if (modelClass. isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(movieRepository) as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}