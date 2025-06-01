package com.example.directorio.di

import android.content.Context
import androidx.room.Room
import com.example.directorio.room.CronosDatabase
import com.example.directorio.room.ContactoDataBaseDao
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideCronosDao(cronosDatabase: CronosDatabase): ContactoDataBaseDao {
        return cronosDatabase.cronosDao()
    }

    @Singleton
    @Provides
    fun provideCronosDatabase(@ApplicationContext context: Context): CronosDatabase {
        return Room.databaseBuilder(
            context,
            CronosDatabase::class.java,
            "cronos_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

}