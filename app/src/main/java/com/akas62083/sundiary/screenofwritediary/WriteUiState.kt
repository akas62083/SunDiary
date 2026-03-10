package com.akas62083.sundiary.screenofwritediary

import java.time.LocalDate

data class WriteUiState(
    val localDate: LocalDate,
    val title: String = "",
    val selected: Selected = Selected.None,
    val content: String = "",
    val tags: Set<String> = emptySet()
)

enum class Selected {
    Sunny,
    Cloudy,
    Rainy,
    None,
}