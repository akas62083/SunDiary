package com.akas62083.sundiary.screenofdetail.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.akas62083.sundiary.R
import com.akas62083.sundiary.db.diary.DiaryEntity

@Composable
fun DiaryContent(
    modifier: Modifier = Modifier,
    diary: DiaryEntity,
    commentAi: () -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    with(sharedTransitionScope) {
        Box(
            modifier
        ) {
            Column(modifier = Modifier.padding(10.dp).verticalScroll(rememberScrollState())) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (diary.title == "") "タイトルが取得できませんでした。"
                        else diary.title,
                        modifier = Modifier
                        .padding(bottom = 10.dp),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                HorizontalDivider()
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = if (diary.id.toInt() != -1) {
                            diary.date.toString().substring(0, 4) + "-" + diary.date.toString()
                                .substring(4, 6) + "-" + diary.date.toString().substring(6, 8)
                        } else {
                            "日付が取得できませんでした"
                        },
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = diary.imageUrl,
                        contentDescription = "image",
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = diary.content,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .padding(vertical = 10.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (diary.commentByAi == null) {
                    Row(
                        modifier = Modifier.padding(5.dp)
                    ) {
                        OutlinedButton(
                            onClick = { commentAi }, colors = ButtonDefaults.textButtonColors(
                                containerColor = Color(0xffffffdd),
                                contentColor = Color(0xff000000)
                            )
                        ) { Text("AIによる評価を取得する") }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth().background(
                            color = Color(0xffffffdd),
                            shape = RoundedCornerShape(10.dp)
                        ).padding(10.dp)
                    ) {
                        Column {
                            Row {
                                Icon(
                                    painter = painterResource(R.drawable.moon_stars_24dp_1f1f1f_fill0_wght400_grad0_opsz24),
                                    contentDescription = "moon_and_star",
                                )
                                Column {
                                    Text("月のコメント")
                                    Text(
                                        "AIによる文章評価",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                            Text(diary.commentByAi ?: "取得できませんでした。")
                        }
                    }
                }
            }
        }
    }
}