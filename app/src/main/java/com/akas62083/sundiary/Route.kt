package com.akas62083.sundiary

import kotlinx.serialization.Serializable

object Route {
    @Serializable
    data object HomeScreen

    @Serializable
    data class WriteScreen(
        val id: Long?
    )

    @Serializable
    data class DetailScreen(
        val id: Long
    )
}