package com.akas62083.sundiary.screenofwritediary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akas62083.sundiary.DiaryRepository
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
    val repository: DiaryRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(WriteUiState(LocalDate.now()))
    val uiState: StateFlow<WriteUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val tags = emptySet<String>()
            repository.getAllDiary().collect { entries ->
                tags + entries.flatMap { it.tags }
            }
            _uiState.update { currentState ->
                currentState.copy(tags = tags)
            }
        }
    }
    fun onEvent(event: WriteEvent) {
        when(event) {
            is WriteEvent.UpdateTitle -> { updateTitle(event.value) }
            is WriteEvent.UpdateSelected -> { selected(event.value)}
            is WriteEvent.UpdateContent -> { updateContent(event.value) }
            is WriteEvent.SaveDiary -> { saveDiary() }
        }
    }

    fun updateTitle(value: String) {
        _uiState.update { currentState ->
            currentState.copy(title = value)
        }
    }
    fun selected(value: Selected) {
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
        viewModelScope.launch {
            repository.insertDiary(
                DiaryEntity(
                    title = uiState.value.title,
                    content = uiState.value.content,
                    date = uiState.value.localDate.toString().replace("-", "").toInt(),
                    edit = false,
                    imageUrl = "",
                    isLiked = false,
                    tags = emptySet(),

                    )
            )
        }
    }
}
