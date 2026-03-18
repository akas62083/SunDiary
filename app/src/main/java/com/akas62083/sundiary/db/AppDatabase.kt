package com.akas62083.sundiary.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.akas62083.sundiary.db.diary.DiaryDao
import com.akas62083.sundiary.db.diary.DiaryEntity

@Database(
    entities = [DiaryEntity::class],
    version = 5,
    exportSchema = false
)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDiaryDao(): DiaryDao
}
