package com.akas62083.sundiary.screenofwritediary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akas62083.sundiary.DiaryRepository
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
    val repository: DiaryRepository,
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
                            title = it.title.substring(1, it.title.length),
                            content = it.content,
                            selected = if(it.title[0] == 's') Selected.Sunny
                                else if(it.title[0] == 'c') Selected.Cloudy
                                else Selected.Rainy
                            )
                    }
                }
            }
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
        if(diaryId == null) {
            viewModelScope.launch {
                repository.insertDiary(
                    DiaryEntity(
                        title =
                            if (uiState.value.selected == Selected.Sunny) "s" + uiState.value.title
                            else if (uiState.value.selected == Selected.Cloudy) "c" + uiState.value.title
                            else "r" + uiState.value.title,
                        content = uiState.value.content,
                        date = uiState.value.localDate.toString().replace("-", "").toInt(),
                        edit = false,
                        imageUrl = "",
                        isLiked = false,
                        tags = emptySet(),

                        )
                )
            }
        } else {
            viewModelScope.launch {
                repository.updateDiary(
                    DiaryEntity(
                        title =
                            if (uiState.value.selected == Selected.Sunny) "s" + uiState.value.title
                            else if (uiState.value.selected == Selected.Cloudy) "c" + uiState.value.title
                            else "r" + uiState.value.title,
                        content = uiState.value.content,
                        date = uiState.value.localDate.toString().replace("-", "").toInt(),
                        imageUrl = "",
                        edit = true,
                        tags = emptySet(),
                        id = diaryId
                    )
                )
            }
        }
    }
}

// adb tcpip 5555
// adb connect ip:5555