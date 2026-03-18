package com.akas62083.sundiary.screenofdetail

sealed interface DetailEvent {
    data object CommentAi: DetailEvent
    data object OnNext: DetailEvent
    data object OnBack: DetailEvent
}