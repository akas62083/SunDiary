package com.akas62083.sundiary.screenofstar

import androidx.compose.ui.unit.Dp
import com.akas62083.sundiary.db.diary.DiaryEntity

data class StarUiState(
    val diaries: List<DiaryEntity> = emptyList(),
    val offsetList: List<StarOffset> = emptyList(),
    val maxHeight: Dp? = null,
    val maxWidth: Dp? = null,
    val refresh: Boolean = false
)

data class StarOffset(
    val x: Dp,
    val y: Dp,
    val star: Int,
    val color: Int
)