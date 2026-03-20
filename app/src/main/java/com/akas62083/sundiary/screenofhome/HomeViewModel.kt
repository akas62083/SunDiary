package com.akas62083.sundiary.screenofhome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akas62083.sundiary.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeDiaries()
    }
    private fun observeDiaries() {
        viewModelScope.launch {
            repository.getAllDiary().collect { entries ->

                val todayInt = LocalDate.now()
                    .toString()
                    .replace("-", "")
                    .toInt()

                val wroteToday = entries.any { it.date == todayInt }

                _uiState.update { currentState ->
                    currentState.copy(
                        diaries = entries,
                        isWroteDiaryToday = wroteToday
                    )
                }
            }
        }
    }

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.IsLikeClick -> { isLikeClick(event.id) }
            is HomeEvent.TabChange -> { tabChange(event.screen) }
            is HomeEvent.ClickTitleCheckBox -> { clickTitleCheckBox() }
            is HomeEvent.ClickContentCheckBox -> { clickContentCheckBox() }
            is HomeEvent.ClickCommentCheckBox -> { clickCommentCheckBox() }
            is HomeEvent.Search -> { search(event.search, event.from, event.to) }
            is HomeEvent.ClickIsLikeCheckBox -> { clickIsLikeCheckBox() }
            is HomeEvent.ClickNotEditCheckBox -> { clickNotEditCheckBox() }
            is HomeEvent.ClickEditCheckBox -> { clickEditCheckBox() }
        }
    }

    fun isLikeClick(id: Long) {
        viewModelScope.launch {
            val diary = repository.getDiaryById(id).first()
            repository.updateDiary(
                diary.copy(
                    isLiked = !diary.isLiked
                )
            )
        }
    }
    fun tabChange(screen: Screens) {
        _uiState.update { currentState ->
            currentState.copy(screen = screen)
        }
    }
    fun clickTitleCheckBox() {
        _uiState.update { currentState ->
            currentState.copy(checkBoxOfTitle = !currentState.checkBoxOfTitle)
        }
    }
    fun clickContentCheckBox() {
        _uiState.update { currentState ->
            currentState.copy(chechBoxOfContent = !currentState.chechBoxOfContent)
        }
    }
    fun clickCommentCheckBox() {
        _uiState.update { currentState ->
            currentState.copy(checkBoxOfComment = !currentState.checkBoxOfComment)
        }
    }
    fun search(search: String, fromDate: String, toDate: String) {
        var fromDate2 = fromDate
        var toDate2 = toDate
        if(fromDate2 == "") {
            fromDate2 = "1038/01/19"
        }
        if(toDate2 == "") {
            toDate2 = "2038/01/19"
        }
        _uiState.update { currentState ->
            currentState.copy(isSearch = IsSearch.Searching)
        }
        viewModelScope.launch {
            repository.getDiaryBySearch(
                titleCheck = if(uiState.value.checkBoxOfTitle) 1 else 0,
                contentCheck = if(uiState.value.chechBoxOfContent) 1 else 0,
                commentCheck = if(uiState.value.checkBoxOfComment) 1 else 0,
                isLikeClick = if(uiState.value.checkBoxOfIsLiked) 1 else 0,
                notEditCheck = if(uiState.value.checkBoxOfNotEdit) 1 else 0,
                editCheck = if(uiState.value.checkBoxOfEdit) 1 else 0,
                word = search
            ).map { date ->
                val fromInt = fromDate2.replace("/", "").toInt()
                val toInt = toDate2.replace("/", "").toInt()
                date.filter {
                    it.date in fromInt..toInt
                }
            }
            .collect {
                _uiState.update { currentState ->
                    currentState.copy(searchList = it, isSearch = IsSearch.Not)
                }
            }
        }
    }
    fun clickIsLikeCheckBox() {
        _uiState.update { currentState ->
            currentState.copy(checkBoxOfIsLiked = !currentState.checkBoxOfIsLiked)
        }
    }
    fun clickNotEditCheckBox() {
        _uiState.update { currentState ->
            currentState.copy(checkBoxOfNotEdit = !currentState.checkBoxOfNotEdit)
        }
    }
    fun clickEditCheckBox() {
        _uiState.update { currnetState ->
            currnetState.copy(checkBoxOfEdit = !currnetState.checkBoxOfEdit)
        }
    }
}

// 220 * 110 * 70