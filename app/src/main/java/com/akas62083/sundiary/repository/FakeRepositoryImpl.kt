package com.akas62083.sundiary.repository

import com.akas62083.sundiary.Repository
import com.akas62083.sundiary.db.diary.DiaryDao
import com.akas62083.sundiary.db.diary.DiaryEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakeRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao
) : Repository {
    val fakeDiaries = listOf(
        DiaryEntity(
            id = 38,
            title = "damy",
            content = "",
            date = 20230408,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 37,
            title = "damy",
            content = "",
            date = 20230407,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 36,
            title = "damy",
            content = "",
            date = 20230404,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 35,
            title = "damy",
            content = "",
            date = 20230331,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 34,
            title = "damy",
            content = "",
            date = 20230330,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 33,
            title = "damy",
            content = "",
            date = 20230327,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 32,
            title = "damy",
            content = "",
            date = 20230322,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 31,
            title = "damy",
            content = "",
            date = 20230321,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 30,
            title = "damy",
            content = "",
            date = 20230318,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 29,
            title = "damy",
            content = "",
            date = 20230313,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 28,
            title = "damy",
            content = "",
            date = 20230312,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 27,
            title = "damy",
            content = "",
            date = 20230309,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 26,
            title = "damy",
            content = "",
            date = 20230305,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 25,
            title = "damy",
            content = "",
            date = 20230304,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 24,
            title = "damy",
            content = "",
            date = 20230301,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 23,
            title = "damy",
            content = "",
            date = 20230225,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 22,
            title = "damy",
            content = "",
            date = 20230224,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 21,
            title = "damy",
            content = "",
            date = 20230220,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 20,
            title = "damy",
            content = "",
            date = 20230216,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 19,
            title = "damy",
            content = "",
            date = 20230215,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 18,
            title = "damy",
            content = "",
            date = 20230214,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 17,
            title = "damy",
            content = "",
            date = 20230210,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 16,
            title = "damy",
            content = "",
            date = 20230206,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 15,
            title = "damy",
            content = "",
            date = 20230205,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 14,
            title = "damy",
            content = "",
            date = 20230201,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 13,
            title = "damy",
            content = "",
            date = 20230128,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 12,
            title = "damy",
            content = "",
            date = 20230127,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 11,
            title = "damy",
            content = "",
            date = 20230124,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 10,
            title = "damy",
            content = "",
            date = 20230120,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 9,
            title = "damy",
            content = "",
            date = 20230119,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 8,
            title = "damy",
            content = "",
            date = 20230118,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 7,
            title = "damy",
            content = "",
            date = 20230115,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 6,
            title = "damy",
            content = "",
            date = 20230111,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 5,
            title = "damy",
            content = "",
            date = 20230110,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 4,
            title = "damy",
            content = "",
            date = 20230107,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 3,
            title = "damy",
            content = "",
            date = 20230103,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 2,
            title = "damy",
            content = "",
            date = 20230102,
            edit = false,
            isLiked = false
        ),
        DiaryEntity(
            id = 1,
            title = "damy",
            content = "",
            date = 20230101,
            edit = false,
            isLiked = false
        ),
    )
    override fun getAllDiary(): Flow<List<DiaryEntity>> = flowOf(fakeDiaries)

    override suspend fun insertDiary(diary: DiaryEntity) = diaryDao.insertDiary(diary)
    override suspend fun deleteDiary(diary: DiaryEntity) {}
    override suspend fun updateDiary(diary: DiaryEntity) {}
    override fun getDiaryById(id: Long): Flow<DiaryEntity> = flowOf(fakeDiaries.first())
    override fun getDiaryByLiked(): Flow<List<DiaryEntity>> = flowOf()
    override suspend fun getCommentByAi(sentence: String): String = "テストコメントです。"
}