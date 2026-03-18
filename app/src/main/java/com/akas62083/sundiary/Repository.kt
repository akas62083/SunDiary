package com.akas62083.sundiary

import com.akas62083.sundiary.db.diary.DiaryEntity
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertDiary(diary: DiaryEntity): Long
    suspend fun deleteDiary(diary: DiaryEntity)
    suspend fun updateDiary(diary: DiaryEntity)
    fun getAllDiary(): Flow<List<DiaryEntity>>
    fun getDiaryById(id: Long): Flow<DiaryEntity>
    fun getDiaryByLiked(): Flow<List<DiaryEntity>>
    suspend fun getCommentByAi(sentence: String): String
}