package com.akas62083.sundiary.db.diary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {
    @Insert
    suspend fun insertDiary(diary: DiaryEntity): Long
    @Delete
    suspend fun deleteDiary(diary: DiaryEntity)
    @Update
    suspend fun updateDiary(diary: DiaryEntity)
    @Query("select * from diary_items order by diary_date desc, diary_id desc")
    fun getAllDiary(): Flow<List<DiaryEntity>>
    @Query("select * from diary_items where diary_id = :id")
    fun getDiaryById(id: Long): Flow<DiaryEntity>
    @Query("select * from diary_items where diary_is_liked = 1 order by diary_date desc")
    fun getDiaryByLiked(): Flow<List<DiaryEntity>>
}