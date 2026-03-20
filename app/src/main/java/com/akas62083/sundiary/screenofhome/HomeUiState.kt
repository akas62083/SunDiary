package com.akas62083.sundiary.screenofhome

import com.akas62083.sundiary.db.diary.DiaryEntity

data class HomeUiState (
    val diaries: List<DiaryEntity> = emptyList(),
    val isWroteDiaryToday: Boolean = true,
    val screen: Screens = Screens.Home,

    val checkBoxOfTitle: Boolean = false, // 検索の言葉で検索されるか否か
    val chechBoxOfContent: Boolean = true, //
    val checkBoxOfComment: Boolean = false, //

    val checkBoxOfDate: Boolean = false, // true だったら日付検索モードに入る(何月何日から何月何日までという形で。)。検索の言葉と合わせる。
    val checkBoxOfIsLiked: Boolean = false,// trueだったらLikeしてるかどうかをCheckできる。検索の言葉と合わせて検索する。
    val checkBoxOfEdit: Boolean = true, //
    val checkBoxOfNotEdit: Boolean = true,

    val isSearch: IsSearch = IsSearch.Not,
    val searchList: List<DiaryEntity> = emptyList()
)

enum class Screens {
    Home,
    Search
}
enum class IsSearch {
    Searching,
    Not,
}