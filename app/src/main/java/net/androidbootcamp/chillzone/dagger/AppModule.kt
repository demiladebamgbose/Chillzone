package net.androidbootcamp.chillzone.dagger

import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.firebase.auth.LoginDataSource
import net.androidbootcamp.chillzone.repositories.MovieRepository
import net.androidbootcamp.chillzone.repositories.UserRepository
import net.androidbootcamp.chillzone.retrofit.MovieInterface
import net.androidbootcamp.chillzone.retrofit.Retrofity
import net.androidbootcamp.chillzone.room.AppDatabase
import net.androidbootcamp.chillzone.viewModels.VMFactory
import javax.inject.Singleton

@Module
class AppModule constructor(val chillApp: ChillApp) {
    @Singleton
    @Provides
    fun providesChillApp() : ChillApp  {
        return chillApp
    }

    @Singleton
    @Provides
    fun providesVMFactory() : VMFactory {
        return VMFactory(chillApp)
    }

    @Singleton
    @Provides
    fun providesMovieInterface() : MovieInterface {
        return Retrofity.retrofit.create(MovieInterface::class.java)
    }

    @Singleton
    @Provides
    fun providesMovieRepository(movieInterface: MovieInterface) : MovieRepository {
        return MovieRepository(movieInterface)
    }

    @Singleton
    @Provides
    fun providesAppDatabase() : AppDatabase {
        return Room.databaseBuilder(chillApp,
            AppDatabase::class.java,
            "chilLzone")
            .build()
    }

    @Singleton
    @Provides
    fun providesUserRepository(loginDataSource: LoginDataSource) : UserRepository {
        return UserRepository(loginDataSource)
    }

    @Singleton
    @Provides
    fun providesLoginDataSource(auth: FirebaseAuth): LoginDataSource {
        return LoginDataSource(auth)
    }

    @Singleton
    @Provides
    fun providesAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}