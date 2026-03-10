package com.akas62083.sundiary.screenofwritediary

sealed interface WriteEvent {
    data class UpdateTitle(val value: String): WriteEvent
    data class UpdateSelected(val value: Selected): WriteEvent
    data class UpdateContent(val value: String): WriteEvent
    data object SaveDiary: WriteEvent
}