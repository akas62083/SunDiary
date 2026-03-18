package com.akas62083.sundiary.db.diary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.akas62083.sundiary.screenofwritediary.Wether

@Entity(tableName = "diary_items")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "diary_id")
    val id: Long = 0,
    @ColumnInfo(name = "diary_title")
    val title: String = "",
    @ColumnInfo(name = "diary_content")
    val content: String = "",
    @ColumnInfo(name = "diary_date")
    val date: Int,
    @ColumnInfo(name = "diary_edit")
    val edit: Boolean = false,
    @ColumnInfo(name = "diary_image_url")
    val imageUrl: String = "",
    @ColumnInfo(name = "diary_is_liked")
    val isLiked: Boolean = false,
    @ColumnInfo(name = "diary_wether")
    val wether: Wether = Wether.None, //SelectedはWriteフォルダで定義。
    @ColumnInfo(name = "diary_comment")
    val commentByAi: String? = null
)