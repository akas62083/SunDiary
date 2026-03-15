package com.akas62083.sundiary.repository

import com.akas62083.sundiary.Repository
import com.akas62083.sundiary.db.diary.DiaryDao
import com.akas62083.sundiary.db.diary.DiaryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao
): Repository {

    override suspend fun insertDiary(diary: DiaryEntity) = diaryDao.insertDiary(diary)
    override suspend fun deleteDiary(diary: DiaryEntity) = diaryDao.deleteDiary(diary)
    override suspend fun updateDiary(diary: DiaryEntity) = diaryDao.updateDiary(diary)
    override fun getAllDiary(): Flow<List<DiaryEntity>> = diaryDao.getAllDiary()
    override fun getDiaryById(id: Long): Flow<DiaryEntity> = diaryDao.getDiaryById(id)
    override fun getDiaryByLiked(): Flow<List<DiaryEntity>> = diaryDao.getDiaryByLiked()
}