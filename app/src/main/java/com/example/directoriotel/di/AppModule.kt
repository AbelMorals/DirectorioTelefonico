package com.example.directorio.di

import android.content.Context
import androidx.room.Room
import com.example.directorio.room.ContactoDatabase
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
    fun provideContactoDao(contactoDatabase: ContactoDatabase): ContactoDataBaseDao {
        return contactoDatabase.contactoDao()
    }

    @Singleton
    @Provides
    fun provideContactoDatabase(@ApplicationContext context: Context): ContactoDatabase {
        return Room.databaseBuilder(
            context,
            ContactoDatabase::class.java,
            "contactos_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

}