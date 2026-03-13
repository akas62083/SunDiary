package com.akas62083.sundiary.screenofhome

sealed interface HomeEvent {
    data class IsLikeClick(val id: Long): HomeEvent
}