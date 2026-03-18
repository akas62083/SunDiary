package com.akas62083.sundiary.screenofdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akas62083.sundiary.Repository
import com.akas62083.sundiary.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repository: Repository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val diaryId = savedStateHandle.toRoute<Route.DetailScreen>().id
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val diary = repository.getDiaryById(diaryId).first()
            val diaries = repository.getAllDiary().first()
            var nextDiary = diary
            var backDiary = diary
            val index = diaries.indexOf(diary)
            if(index + 1 != diaries.size) {
                nextDiary = diaries[index + 1]
            }
            if(index != 0) {
                backDiary = diaries[index - 1]
            }
            _uiState.update { currentState ->
                currentState.copy(
                    diary = diary,
                    diaries = diaries,
                    backDiary = backDiary,
                    nextDiary = nextDiary
                )
            }
        }
    }
    fun onEvent(event: DetailEvent) {
        when(event) {
            is DetailEvent.CommentAi -> { commentAi() }
            is DetailEvent.OnNext -> { onNext() }
            is DetailEvent.OnBack -> { onBack() }
        }
    }
    fun commentAi() {
        viewModelScope.launch {
            try {
                val comment = repository.getCommentByAi(uiState.value.diary.content)
                _uiState.update { currentState ->
                    currentState.copy(
                        diary = currentState.diary.copy(
                            commentByAi = comment,
                            edit = true
                        )
                    )
                }
                repository.updateDiary(uiState.value.diary.copy(commentByAi = comment, edit = true))
            } catch(e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(diary = currentState.diary.copy(commentByAi = "通信に失敗しました。"))
                }
            }
        }
    }
    fun onNext() {
        val diary = uiState.value.nextDiary
        val diaries = uiState.value.diaries
        var nextDiary = diary
        var backDiary = diary
        val index = diaries.indexOf(diary)
        if(index + 1 != diaries.size) {
            nextDiary = diaries[index + 1]
        }
        if(index != 0) {
            backDiary = diaries[index - 1]
        }
        _uiState.update { currentState ->
            currentState.copy(
                diary = diary,
                diaries = diaries,
                nextDiary = nextDiary,
                backDiary = backDiary
            )
        }
    }
    fun onBack() {
        val diary = uiState.value.backDiary
        val diaries = uiState.value.diaries
        var nextDiary = diary
        var backDiary = diary
        val index = diaries.indexOf(diary)
        if(index + 1 != diaries.size) {
            nextDiary = diaries[index + 1]
        }
        if(index != 0) {
            backDiary = diaries[index - 1]
        }
        _uiState.update { currentState ->
            currentState.copy(
                diary = diary,
                diaries = diaries,
                nextDiary = nextDiary,
                backDiary = backDiary
            )
        }
    }
}