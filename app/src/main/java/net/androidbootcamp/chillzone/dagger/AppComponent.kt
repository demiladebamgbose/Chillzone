package net.androidbootcamp.chillzone.dagger

import dagger.Component
import net.androidbootcamp.chillzone.ui.gallery.GalleryFragment
import net.androidbootcamp.chillzone.ui.home.HomeFragment
import net.androidbootcamp.chillzone.ui.login.LoginActivity
import net.androidbootcamp.chillzone.ui.signup.SignupActivity
import net.androidbootcamp.chillzone.viewModels.VMFactory
import javax.inject.Singleton

@Component (modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject (galleryFragment: GalleryFragment)
    fun inject (homeFragment: HomeFragment)
    fun inject (vmFactory: VMFactory)
    fun inject (loginActivity: LoginActivity)
    fun inject (signupActivity: SignupActivity)
}