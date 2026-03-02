package com.akas62083.sundiary

import com.akas62083.sundiary.db.diary.DiaryDao
import jakarta.inject.Inject

class DiaryRepository @Inject constructor(
    private val diaryDao: DiaryDao
) {

}