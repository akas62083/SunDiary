package com.akas62083.sundiary.di

import android.content.Context
import androidx.room.Room
import com.akas62083.sundiary.Api
import com.akas62083.sundiary.db.AppDatabase
import com.akas62083.sundiary.db.diary.DiaryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
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
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideApi(json: Json): Api {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.example.com/") //TODO
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(Api::class.java)
    }
}