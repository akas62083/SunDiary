package com.akas62083.sundiary.screenofdetail

import com.akas62083.sundiary.db.diary.DiaryEntity

data class DetailUiState (
    val diary: DiaryEntity = DiaryEntity(id = -1, title = "", content = "", date = 0, edit = false, imageUrl = "", isLiked = false,),
    val diaries: List<DiaryEntity> = emptyList(),
    val nextDiary: DiaryEntity = DiaryEntity(id = -1, title = "", content = "", date = 0, edit = false, imageUrl = "", isLiked = false,),
    val backDiary: DiaryEntity = DiaryEntity(id = -1, title = "", content = "", date = 0, edit = false, imageUrl = "", isLiked = false,),
)