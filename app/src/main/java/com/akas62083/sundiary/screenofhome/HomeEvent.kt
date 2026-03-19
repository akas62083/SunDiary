package com.akas62083.sundiary.screenofhome

sealed interface HomeEvent {
    data class IsLikeClick(val id: Long): HomeEvent
    data class TabChange(val screen: Screens): HomeEvent
}