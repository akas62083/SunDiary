package com.akas62083.sundiary.screenofstar

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akas62083.sundiary.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
            val diaries = repository.getAllDiary().first()
            _uiState.update { currentState ->
                currentState.copy(diaries = diaries)
            }
            combine(flowOf(uiState.value.diaries), flowOf(uiState.value.maxHeight), flowOf(uiState.value.maxWidth)) {}.collect{
                if(uiState.value.maxHeight != null && uiState.value.maxWidth != null && uiState.value.diaries.isNotEmpty()) {
                    val list: MutableList<StarOffset> = mutableListOf()
                    for(i in uiState.value.diaries) {
                        val luckyColor = Random.nextInt(1..777)
                        list.add(
                            StarOffset(
                                x = (Random.nextFloat() * uiState.value.maxWidth!!.value).dp,
                                y = (Random.nextFloat() * uiState.value.maxHeight!!.value).dp,
                                color = if(luckyColor == 777) 6 else Random.nextInt(1..5),
                                star = Random.nextInt(1..5),
                            )
                        )
                    }
                    _uiState.update { currentState ->
                        currentState.copy(offsetList = list)
                    }
                }
            }
        }
    }

    fun onEvent(event: StarEvnet) {
        when (event) {
            is StarEvnet.ChengeMaxHeight -> {
                _uiState.update { currentState ->
                    currentState.copy(maxHeight = event.height, maxWidth = event.width)
                }
            }
        }
    }
}