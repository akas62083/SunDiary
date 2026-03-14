package com.akas62083.sundiary.screenofstar

import androidx.compose.ui.unit.Dp

sealed interface StarEvent {
    data class ChengeMaxHeight(val height: Dp, val width: Dp): StarEvent
}