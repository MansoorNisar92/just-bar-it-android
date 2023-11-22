package com.android.app.justbarit.di

import android.content.Context
import androidx.room.Room
import com.android.app.justbarit.data.local.dao.BarDao
import com.android.app.justbarit.data.local.database.JustBarItDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): JustBarItDatabase {
        return Room.databaseBuilder(
            context,
            JustBarItDatabase::class.java,
            "just_bar_it_database"
        ).build()
    }

    @Provides
    fun provideCountryDao(database: JustBarItDatabase): BarDao {
        return database.barsDao()
    }
}