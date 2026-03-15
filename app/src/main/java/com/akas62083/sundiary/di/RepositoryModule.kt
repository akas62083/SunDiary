package com.akas62083.sundiary.di

import com.akas62083.sundiary.Repository
import com.akas62083.sundiary.repository.DiaryRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(
        diaryRepositoryImpl: DiaryRepositoryImpl // ここをFakeRepositoryImplに変えたらfakeデータが表示される。型のほうだけでよい。
    ): Repository
}
