package com.akas62083.sundiary.screenofstar

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akas62083.sundiary.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

@HiltViewModel
class StarViewModel @Inject constructor(
    repository: DiaryRepository,
): ViewModel() {
    private val _uiState = MutableStateFlow(StarUiState())
    val uiState: StateFlow<StarUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                repository.getAllDiary(),
                uiState.map { it.maxHeight }.distinctUntilChanged(),
                uiState.map { it.maxWidth }.distinctUntilChanged()
            ) { diaries, height, width ->
                if(height != null && width != null && diaries.isNotEmpty()) {
                    val list = diaries.map {
                        val luckyColor = Random.nextInt(1..777)
                        StarOffset(
                            x = ((Random.nextFloat() * (width.value - 10)) + 5).dp,
                            y = ((Random.nextFloat() * (height.value - 10)) + 5).dp,
                            color = if (luckyColor == 777) 6 else Random.nextInt(1..5),
                            star = Random.nextInt(1..5),
                        )
                    }
                    _uiState.update { currentState ->
                        currentState.copy(offsetList = list, diaries = diaries)
                    }
                }
            }.collect()
        }
    }

    fun onEvent(event: StarEvent) {
        when (event) {
            is StarEvent.ChengeMaxHeight -> {
                _uiState.update { currentState ->
                    currentState.copy(maxHeight = event.height, maxWidth = event.width)
                }
            }
        }
    }
}