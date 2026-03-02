package com.akas62083.sundiary.di

import android.content.Context
import androidx.room.Room
import com.akas62083.sundiary.DiaryRepository
import com.akas62083.sundiary.db.AppDatabase
import com.akas62083.sundiary.db.diary.DiaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_databse"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideDiaryDao(databse: AppDatabase): DiaryDao = databse.getDiaryDao()
    @Provides
    @Singleton
    fun provideDiaryRepository(dao: DiaryDao): DiaryRepository = DiaryRepository(dao)


}