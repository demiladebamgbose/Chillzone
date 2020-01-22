package net.androidbootcamp.chillzone.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.ui.gallery.GalleryViewModel
import javax.inject.Inject

class VMFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass. isAssignableFrom(GalleryViewModel::class.java)) {
            return GalleryViewModel() as T
        } else throw IllegalArgumentException("Unknown ViewModel class")
    }
}