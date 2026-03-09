package com.akas62083.sundiary.screenofhome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akas62083.sundiary.DiaryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DiaryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    init {
        viewModelScope.launch {
            repository.getAllDiary()
                .combine(flowOf(System.currentTimeMillis())) { entries, now ->
                    val todayStart = calculateTodayStart(now)
                    val hasWrittenToday = entries.any { it.date >= todayStart }
                    entries to hasWrittenToday
                }
                .collect { (entries, hasWrittenToday) ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            diaries = entries,
                            isWroteDiaryToday = hasWrittenToday
                        )
                    }
                }
        }
    }
























































    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateTodayStart(millis: Long): Long {
        return Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}