package com.akas62083.sundiary

import com.akas62083.sundiary.db.diary.DiaryDao
import com.akas62083.sundiary.db.diary.DiaryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryRepository @Inject constructor(
    private val diaryDao: DiaryDao
) {
    suspend fun insertDiary(diary: DiaryEntity) = diaryDao.insertDiary(diary)
    suspend fun deleteDiary(diary: DiaryEntity) = diaryDao.deleteDiary(diary)
    suspend fun updateDiary(diary: DiaryEntity) = diaryDao.updateDiary(diary)
    fun getAllDiary(): Flow<List<DiaryEntity>> = diaryDao.getAllDiary()
    fun getDiaryById(id: Long): Flow<DiaryEntity> = diaryDao.getDiaryById(id)
    fun getDiaryByLiked(): Flow<List<DiaryEntity>> = diaryDao.getDiaryByLiked()
}