package com.akas62083.sundiary.screenofhome

sealed interface HomeEvent {
    data class IsLikeClick(val id: Long): HomeEvent
    data class TabChange(val screen: Screens): HomeEvent
    data object ClickTitleCheckBox: HomeEvent
    data object ClickContentCheckBox: HomeEvent
    data object ClickCommentCheckBox: HomeEvent
    data object ClickIsLikeCheckBox: HomeEvent
    data object ClickNotEditCheckBox: HomeEvent
    data object ClickEditCheckBox: HomeEvent
    data class Search(val search: String, val from: String, val to: String): HomeEvent
}