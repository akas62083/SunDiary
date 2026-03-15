package com.akas62083.sundiary.screenofhome.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.akas62083.sundiary.R
import com.akas62083.sundiary.db.diary.DiaryEntity
import com.akas62083.sundiary.screenofhome.HomeUiState
import com.akas62083.sundiary.screenofwritediary.Wether
import java.time.LocalDate

@Composable
fun ItemCard(
    diary: DiaryEntity,
    uiState: HomeUiState,
    isLikeClick: (Long) -> Unit,
    editClick: (Long) -> Unit,
    diaryClick: (Long) -> Unit
) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 10.dp)
                .clickable(){
                    diaryClick(diary.id)
                },
            elevation = CardDefaults.cardElevation(defaultElevation = 7.dp)
        ) {
            val year = diary.date / 10000
            val month = diary.date % 10000 / 100
            val day = diary.date % 100
            Row(
                modifier = Modifier.padding(10.dp).fillMaxWidth().height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier.weight(1.5f).fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = year.toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = month.toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = day.toString(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "年",
                        )
                        Text(
                            text = "月",
                        )
                        Text(
                            text = "日",
                        )
                    }
                }
                Row(
                    modifier = Modifier.weight(1.5f).fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        modifier = Modifier.padding(10.dp).fillMaxHeight().aspectRatio(1f),
                        painter = painterResource(
                            when(diary.wether) {
                                Wether.Sunny -> R.drawable.sunny
                                Wether.Cloudy -> R.drawable.sun_and_cloud
                                else -> R.drawable.cloudy
                            }
                        ),
                        contentDescription = "tenki",
                        tint = Color(0xffa6201a)
                    )
                }
                Row(modifier = Modifier.weight(5f).fillMaxSize()) {
                    Column {
                        Text(
                            modifier = Modifier.weight(1f).fillMaxSize(),
                            text = "「${diary.title}」",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            modifier = Modifier.weight(2f),
                            text = diary.content,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Column(modifier = Modifier.weight(2f).fillMaxSize()) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "id: ${(diary.id)}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        text = if (diary.edit) "編集済" else if (diary.date == LocalDate.now()
                                .toString().replace("-", "").toInt()
                        ) "編集可" else "未編集",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        modifier = Modifier.weight(1f)
                            .clickable(
                                interactionSource = null,
                                indication = null
                            ) { isLikeClick(diary.id) },
                        imageVector = Icons.Default.Star,
                        tint = if(diary.isLiked) Color.Black else Color.LightGray,
                        contentDescription = "liked",
                    )
                }
            }

        }
        if(diary.date == LocalDate.now().toString().replace("-", "").toInt() && !diary.edit) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { editClick(diary.id) }) {
                    Text("編集する")
                }
            }
        }
    }
}