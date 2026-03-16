package com.akas62083.sundiary.screenofwritediary

import android.net.Uri
import java.time.LocalDate

data class WriteUiState(
    val localDate: LocalDate,
    val title: String = "",
    val selected: Wether = Wether.None,
    val content: String = "",
    val mode: Mode = Mode.New,
    val imageUrl: String? = null,
    val cameraMode: CameraMode = CameraMode.None,
    val uri: Uri? = null
) {
    val check = {
        title.isNotEmpty() && content.isNotEmpty() && selected != Wether.None
    }
}

enum class Wether {
    Sunny,
    Cloudy,
    Rainy,
    None,
}

sealed interface Mode{
    object New: Mode
    data class Edit(val id: Long): Mode
}
enum class CameraMode {
    None,
    Take,
    Done,
}