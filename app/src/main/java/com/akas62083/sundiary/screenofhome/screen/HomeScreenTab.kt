package com.akas62083.sundiary.screenofhome.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.akas62083.sundiary.screenofhome.HomeUiState
import com.akas62083.sundiary.screenofhome.composable.ItemCard

@Composable
fun HomeScreenTab(
    uiState: HomeUiState,
    navigateToWriteScreen: () -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState,
    isLikeClick: (Long) -> Unit,
    editClick: (Long) -> Unit,
    navigateToDetailScreen: (Long) -> Unit,
) {
    Column(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        if (!uiState.isWroteDiaryToday) {
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                modifier = Modifier.height(90.dp) //160
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .clickable { navigateToWriteScreen() },
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(30.dp))
                        Box(modifier = Modifier.weight(1f).padding(7.dp)) {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = Icons.Default.Edit,
                                contentDescription = "sun_mark"
                            )
                        }
                        Box(
                            modifier = Modifier.weight(7f).fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("今日の気分は？")
                        }
                        Icon(
                            modifier = Modifier.weight(1f).fillMaxSize(),
                            imageVector = Icons.Default.ArrowRight,
                            contentDescription = "right_arrow"
                        )
                        Spacer(modifier = Modifier.width(30.dp))
                    }
                }
            }
        }
        LazyColumn(state = listState) {
            itemsIndexed(uiState.diaries, key = { _, it -> it.id }) { index, it ->
                val currentYM = it.date / 100
                val prevYM = if (index > 0) uiState.diaries[index - 1].date / 100 else -1

                if (currentYM != prevYM) {
                    HorizontalDivider()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        val dateStr = it.date.toString()
                        Text("${dateStr.substring(0, 4)}年${dateStr.substring(4, 6)}月")
                    }
                }
                ItemCard(
                    diary = it,
                    uiState = uiState,
                    isLikeClick = { id ->
                        isLikeClick(id)
                    },
                    editClick = { id ->
                        editClick(id)
                    },
                    diaryClick = { id ->
                        navigateToDetailScreen(id)
                    }
                )
            }
        }
    }
}