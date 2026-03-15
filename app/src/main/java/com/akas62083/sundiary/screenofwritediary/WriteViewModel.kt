package com.akas62083.sundiary.screenofwritediary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akas62083.sundiary.Repository
import com.akas62083.sundiary.Route
import com.akas62083.sundiary.db.diary.DiaryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    val repository: Repository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val args = savedStateHandle.toRoute<Route.WriteScreen>()
    private val diaryId: Long? = args.id
    private val _uiState = MutableStateFlow(WriteUiState(LocalDate.now()))
    val uiState: StateFlow<WriteUiState> = _uiState.asStateFlow()

    init {
        if(diaryId != null) {
            _uiState.update { currentState ->
                currentState.copy(mode = Mode.Edit(diaryId))
            }
        }
        viewModelScope.launch {
            if(diaryId != null) {
                repository.getDiaryById(diaryId).collect {
                    _uiState.update { currentState ->
                        currentState.copy(
                            title = it.title,
                            content = it.content,
                            selected = it.wether
                        )
                    }
                }
            }
        }
    }
    fun onEvent(event: WriteEvent) {
        when(event) {
            is WriteEvent.UpdateTitle -> { updateTitle(event.value) }
            is WriteEvent.UpdateSelected -> { selected(event.value)}
            is WriteEvent.UpdateContent -> { updateContent(event.value) }
            is WriteEvent.SaveDiary -> { saveDiary() }
            is WriteEvent.OnImageSelected -> { onImageSelected(event.value) }
        }
    }

    fun updateTitle(value: String) {
        _uiState.update { currentState ->
            currentState.copy(title = value)
        }
    }
    fun selected(value: Wether) {
        _uiState.update { currentState ->
            currentState.copy(selected = value)
        }
    }
    fun updateContent(value: String) {
        _uiState.update { currentState ->
            currentState.copy(content = value)
        }
    }
    fun saveDiary() {
        if(diaryId == null) {
            viewModelScope.launch {
                repository.insertDiary(
                    DiaryEntity(
                        title = uiState.value.title,
                        content = uiState.value.content,
                        date = uiState.value.localDate.toString().replace("-", "").toInt(),
                        edit = false,
                        imageUrl = "",
                        isLiked = false,
                        wether = uiState.value.selected
                    )
                )
            }
        } else {
            viewModelScope.launch {
                repository.updateDiary(
                    DiaryEntity(
                        title = uiState.value.title,
                        content = uiState.value.content,
                        date = uiState.value.localDate.toString().replace("-", "").toInt(),
                        imageUrl = uiState.value.imageUrl ?: "",
                        edit = true,
                        id = diaryId,
                        wether = uiState.value.selected
                    )
                )
            }
        }
    }
    fun onImageSelected(value: String) {
        _uiState.update { currentState ->
            currentState.copy(imageUrl = value)
        }
    }
}