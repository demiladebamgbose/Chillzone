package net.androidbootcamp.chillzone.dagger

import dagger.Module
import dagger.Provides
import net.androidbootcamp.chillzone.ChillApp
import net.androidbootcamp.chillzone.viewModels.VMFactory
import org.jetbrains.annotations.Nullable
import javax.inject.Singleton

@Module
class AppModule constructor(val chillApp: ChillApp) {

    @Singleton
    @Provides
    fun providesChillApp(): ChillApp  {
        return chillApp
    }

    @Singleton
    @Provides
    fun providesVMFactory() :VMFactory {
        return VMFactory()
    }

}