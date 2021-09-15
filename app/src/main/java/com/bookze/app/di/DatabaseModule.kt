package com.bookze.app.di

import android.app.Application
import androidx.room.Room
import com.bookze.app.db.BookDao
import com.bookze.app.db.BookDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesBookDatabase(application: Application): BookDatabase =
        Room.databaseBuilder(application, BookDatabase::class.java, "BookDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesBookDao(database: BookDatabase): BookDao = database.getBookDao()
}