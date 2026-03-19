package com.akas62083.sundiary.screenofhome

import com.akas62083.sundiary.db.diary.DiaryEntity

data class HomeUiState (
    val diaries: List<DiaryEntity> = emptyList(),
    val isWroteDiaryToday: Boolean = true,
    val screen: Screens = Screens.Home,
)

enum class Screens {
    Home,
    Search
}