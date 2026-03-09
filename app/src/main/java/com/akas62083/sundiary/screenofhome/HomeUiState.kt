package com.akas62083.sundiary.screenofhome

import com.akas62083.sundiary.db.diary.DiaryEntity

data class HomeUiState (
    val diaries: List<DiaryEntity> = emptyList(),
    val isWroteDiaryToday: Boolean = true,
)