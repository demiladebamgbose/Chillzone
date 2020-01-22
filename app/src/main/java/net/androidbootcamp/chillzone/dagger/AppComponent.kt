package net.androidbootcamp.chillzone.dagger

import dagger.Component
import net.androidbootcamp.chillzone.ui.gallery.GalleryFragment
import net.androidbootcamp.chillzone.viewModels.VMFactory
import org.jetbrains.annotations.Nullable
import javax.inject.Singleton
import kotlin.reflect.KClass

@Component (modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject (galleryFragment: GalleryFragment)
}