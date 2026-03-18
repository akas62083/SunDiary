package com.akas62083.sundiary.screenofwritediary

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.akas62083.sundiary.Repository
import com.akas62083.sundiary.Route
import com.akas62083.sundiary.db.diary.DiaryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class WriteViewModel @Inject constructor(
    val repository: Repository,
    @ApplicationContext private val context: Context,
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
                            selected = it.wether,
                            imageUrl = it.imageUrl
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
            is WriteEvent.TakeAPicture -> { takeAPicture() }
            is WriteEvent.DoneTakeAPicture -> { doneTakeAPicture() }
            is WriteEvent.DeleteImage -> { deleteImage() }
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
                        imageUrl = uiState.value.imageUrl ?: "",
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
        val uri = Uri.parse(value)
        val savePath = saveImageToInternalStorage(uri)
        _uiState.update { currentState ->
            currentState.copy(imageUrl = savePath, cameraMode = CameraMode.Done)
        }
    }
    private fun saveImageToInternalStorage(uri: Uri): String {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val fileName = "diary_${UUID.randomUUID()}.jpg"
            val file = File(context.filesDir, fileName)

            inputStream?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            file.absolutePath
        } catch (e: Exception) {
            ""
        }
    }


    fun takeAPicture() {
        _uiState.update { currentState ->
            currentState.copy(
                cameraMode = CameraMode.Take,
                uri = getCameraUri()
            )
        }
    }
    private fun getCameraUri(): Uri {
        val fileName = "diary_${UUID.randomUUID()}.jpg"
        val file = File(context.filesDir, fileName)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }
    fun doneTakeAPicture() {
        _uiState.update { currentState ->
            currentState.copy(
                cameraMode = CameraMode.Done,
                imageUrl = currentState.uri?.toString(),
                uri = null
            )
        }
    }
    fun deleteImage() {
        File(uiState.value.imageUrl).delete()
        _uiState.update { currentState ->
            currentState.copy(
                imageUrl = null,
                cameraMode = CameraMode.None,
                uri = null
            )
        }
    }
}
