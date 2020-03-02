package net.androidbootcamp.chillzone.dagger

import androidx.room.Room
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.R
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
    fun providesUserRepository(loginDataSource: LoginDataSource, appDatabase: AppDatabase) : UserRepository {
        return UserRepository(loginDataSource, appDatabase)
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

    @Singleton
    @Provides
    fun providesGSO(chillApp: ChillApp): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(chillApp.baseContext.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
}

