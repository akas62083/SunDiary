package com.akas62083.sundiary.screenofdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akas62083.sundiary.DiaryRepository
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
    repository: DiaryRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    val diaryId = savedStateHandle.toRoute<Route.DetailScreen>().id
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val diary = repository.getDiaryById(diaryId).first()
            _uiState.update { currentState ->
                currentState.copy(
                    diary = diary
                )
            }
        }
    }



}