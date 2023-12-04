package johan.kovalsikoski.controledeentrada.di

import android.app.Application
import android.provider.ContactsContract.Data
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import johan.kovalsikoski.controledeentrada.data.local.Database
import johan.kovalsikoski.controledeentrada.data.local.dao.GuestDao
import johan.kovalsikoski.controledeentrada.data.local.dao.GuestDao_Impl
import johan.kovalsikoski.controledeentrada.data.repository.GuestsRepositoryImpl
import johan.kovalsikoski.controledeentrada.domain.repository.GuestsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTrackerDatabase(app: Application): Database {
        return Room.databaseBuilder(
            app,
            Database::class.java,
            "guest_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGuestsRepository(database: Database): GuestsRepository {
        return GuestsRepositoryImpl(database.dao)
    }


}