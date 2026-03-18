package com.akas62083.sundiary

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("comment")
    suspend fun commentAi(
        @Body request: CommentRequest
    ): CommentResponse
}

@Serializable
data class CommentRequest(
    val sentence: String
)

@Serializable
data class CommentResponse(
    val comment: String
)