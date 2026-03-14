package com.akas62083.sundiary

import com.akas62083.sundiary.db.diary.DiaryDao
import com.akas62083.sundiary.db.diary.DiaryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DiaryRepository @Inject constructor(
    private val diaryDao: DiaryDao
) {
    val fakeDiaries = listOf(
        DiaryEntity(id = 1, title = "damy", content = "", date = 20230101, edit = false, isLiked = false),
        DiaryEntity(id = 2, title = "damy", content = "", date = 20230104, edit = false, isLiked = false),
        DiaryEntity(id = 3, title = "damy", content = "", date = 20230131, edit = false, isLiked = false),
        DiaryEntity(id = 4, title = "damy", content = "", date = 20230201, edit = false, isLiked = false),
        DiaryEntity(id = 5, title = "damy", content = "", date = 20230301, edit = false, isLiked = false),
        DiaryEntity(id = 6, title = "damy", content = "", date = 20230361, edit = false, isLiked = false),
        DiaryEntity(id = 7, title = "damy", content = "", date = 20230362, edit = false, isLiked = false),

        DiaryEntity(id = 8, title = "damy", content = "", date = 20240131, edit = false, isLiked = false),
        DiaryEntity(id = 9, title = "damy", content = "", date = 20240201, edit = false, isLiked = false),
        DiaryEntity(id = 10, title = "damy", content = "", date = 20240211, edit = false, isLiked = false),
        DiaryEntity(id = 11, title = "damy", content = "", date = 20240301, edit = false, isLiked = false),
        DiaryEntity(id = 12, title = "damy", content = "", date = 20240401, edit = false, isLiked = false),
        DiaryEntity(id = 13, title = "damy", content = "", date = 20240441, edit = false, isLiked = false),
        DiaryEntity(id = 14, title = "damy", content = "", date = 20240461, edit = false, isLiked = false),
        DiaryEntity(id = 15, title = "damy", content = "", date = 20240467, edit = false, isLiked = false),
        DiaryEntity(id = 16, title = "damy", content = "", date = 20240471, edit = false, isLiked = false),
        DiaryEntity(id = 17, title = "damy", content = "", date = 20240481, edit = false, isLiked = false),
        DiaryEntity(id = 18, title = "damy", content = "", date = 20240491, edit = false, isLiked = false),
        DiaryEntity(id = 19, title = "damy", content = "", date = 20240501, edit = false, isLiked = false),
        DiaryEntity(id = 20, title = "damy", content = "", date = 20240601, edit = false, isLiked = false),
        DiaryEntity(id = 21, title = "damy", content = "", date = 20240701, edit = false, isLiked = false),

        DiaryEntity(id = 22, title = "damy", content = "", date = 20250101, edit = false, isLiked = false),
        DiaryEntity(id = 23, title = "damy", content = "", date = 20250102, edit = false, isLiked = false),
        DiaryEntity(id = 24, title = "damy", content = "", date = 20250103, edit = false, isLiked = false),
        DiaryEntity(id = 25, title = "damy", content = "", date = 20250104, edit = false, isLiked = false),
        DiaryEntity(id = 26, title = "damy", content = "", date = 20250105, edit = false, isLiked = false),
        DiaryEntity(id = 27, title = "damy", content = "", date = 20250106, edit = false, isLiked = false),
        DiaryEntity(id = 28, title = "damy", content = "", date = 20250111, edit = false, isLiked = false),
        DiaryEntity(id = 29, title = "damy", content = "", date = 20250122, edit = false, isLiked = false),
        DiaryEntity(id = 30, title = "damy", content = "", date = 20250131, edit = false, isLiked = false),
        DiaryEntity(id = 31, title = "damy", content = "", date = 20250141, edit = false, isLiked = false),
        DiaryEntity(id = 32, title = "damy", content = "", date = 20250151, edit = false, isLiked = false),
        DiaryEntity(id = 33, title = "damy", content = "", date = 20250161, edit = false, isLiked = false),
        DiaryEntity(id = 34, title = "damy", content = "", date = 20250241, edit = false, isLiked = false),
        DiaryEntity(id = 35, title = "damy", content = "", date = 20250251, edit = false, isLiked = false),
        DiaryEntity(id = 36, title = "damy", content = "", date = 20250261, edit = false, isLiked = false),
        DiaryEntity(id = 37, title = "damy", content = "", date = 20250271, edit = false, isLiked = false),
        DiaryEntity(id = 38, title = "damy", content = "", date = 20250281, edit = false, isLiked = false),
    ).asReversed()
    suspend fun insertDiary(diary: DiaryEntity) = diaryDao.insertDiary(diary)
    suspend fun deleteDiary(diary: DiaryEntity) = diaryDao.deleteDiary(diary)
    suspend fun updateDiary(diary: DiaryEntity) = diaryDao.updateDiary(diary)
    // fun getAllDiary(): Flow<List<DiaryEntity>> = flowOf(fakeDiaries) //テスト用(表示のみ)
    fun getAllDiary(): Flow<List<DiaryEntity>> = diaryDao.getAllDiary() //テストじゃない用
    fun getDiaryById(id: Long): Flow<DiaryEntity> = diaryDao.getDiaryById(id)
    fun getDiaryByLiked(): Flow<List<DiaryEntity>> = diaryDao.getDiaryByLiked()
}
