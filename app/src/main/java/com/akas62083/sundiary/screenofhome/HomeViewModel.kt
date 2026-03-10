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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DiaryRepository
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
}