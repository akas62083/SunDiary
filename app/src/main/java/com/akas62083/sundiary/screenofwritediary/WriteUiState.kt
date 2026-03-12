package com.akas62083.sundiary.screenofwritediary

import java.time.LocalDate

data class WriteUiState(
    val localDate: LocalDate,
    val title: String = "",
    val selected: Selected = Selected.None,
    val content: String = "",
    val tags: Set<String> = emptySet(),
    val mode: Mode = Mode.New
) {
    val check = {
        title.isNotEmpty() && content.isNotEmpty() && selected != Selected.None
    }
}

enum class Selected {
    Sunny,
    Cloudy,
    Rainy,
    None,
}

sealed interface Mode{
    object New: Mode
    data class Edit(val id: Long): Mode
}