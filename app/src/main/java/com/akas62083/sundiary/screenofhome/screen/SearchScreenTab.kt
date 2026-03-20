package com.akas62083.sundiary.screenofhome.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akas62083.sundiary.screenofhome.HomeUiState
import com.akas62083.sundiary.screenofhome.IsSearch
import com.akas62083.sundiary.screenofhome.composable.DatePickerDialog
import com.akas62083.sundiary.screenofhome.composable.ItemCard
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenTab(
    uiState: HomeUiState,
    clickTitleCheckBox: () -> Unit,
    clickContentCheckBox: () -> Unit,
    clickCommentCheckBox: () -> Unit,
    clickIsLikeCheckBox: () -> Unit,
    clickNotEditCheckBox: () -> Unit,
    clickEditCheckBox: () -> Unit,
    search: (String, String, String) -> Unit,
    isLikeClick: (Long) -> Unit,
    editClick: (Long) -> Unit,
    navigateToDetailScreen: (Long) -> Unit,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    var serchwords by remember { mutableStateOf("") }
    var dateCheckboxFrom by remember { mutableStateOf(false) }
    var dateCheckboxTo by remember { mutableStateOf(false) }
    var dateFrom by remember { mutableStateOf("") }
    var dateTo by remember { mutableStateOf("") }

    Box(modifier = Modifier.padding(horizontal = 10.dp)) {
        Column {
            TextField(
                singleLine = true,
                value = serchwords,
                onValueChange = { serchwords = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("検索") },
                leadingIcon = {
                    Icon(
                        Icons.Sharp.Search,
                        contentDescription = null,
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Sharp.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.clickable(
                            interactionSource = null,
                            indication = null
                        ) { search(serchwords, dateFrom, dateTo) },
                        tint = Color(0xffa6201a)
                    )
                },
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth().scrollable(rememberScrollState(), orientation = Orientation.Horizontal)) {
                Spacer(modifier = Modifier.width(5.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = if(uiState.checkBoxOfTitle) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background),
                    border = CardDefaults.outlinedCardBorder(),
                ) {
                    Text(
                        text = "タイトル",
                        fontSize = 20.sp,
                        modifier = Modifier.clickable { clickTitleCheckBox() }
                            .padding(7.5.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = if(uiState.chechBoxOfContent) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Text(
                        text = "本文",
                        fontSize = 20.sp,
                        modifier = Modifier.clickable { clickContentCheckBox() }
                            .padding(7.5.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = if(uiState.checkBoxOfComment) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Text(
                        text = "AIコメント",
                        fontSize = 20.sp,
                        modifier = Modifier.clickable { clickCommentCheckBox() }
                            .padding(7.5.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.width(5.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = if(uiState.checkBoxOfIsLiked) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.clickable { clickIsLikeCheckBox() }
                            .height(IntrinsicSize.Min)
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))
                        Column {
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null)
                            Spacer(modifier = Modifier.weight(1f))
                        }
                        Text(
                            text = "お気に入り",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(7.5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = if(uiState.checkBoxOfNotEdit) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.clickable { clickNotEditCheckBox() }
                    ) {
                        Text(
                            text = "未編集",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(7.5.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = if(uiState.checkBoxOfEdit) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.background),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Row(
                        modifier = Modifier.clickable { clickEditCheckBox() }
                    ) {
                        Text(
                            text = "編集済み",
                            fontSize = 20.sp,
                            modifier = Modifier.padding(7.5.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
                Spacer(modifier = Modifier.width(5.dp))
                Card(
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Text(
                        text = if(dateFrom != "") dateFrom else "1970/01/01",
                        modifier = Modifier.clickable { dateCheckboxFrom = true }
                            .padding(7.5.dp)
                    )
                }
                Text("～")
                Card(
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Text(
                        text = if(dateTo != "") dateTo else "2038/01/19",
                        modifier = Modifier.clickable { dateCheckboxTo = true }
                            .padding(7.5.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            if(uiState.isSearch == IsSearch.Not) {
                LazyColumn {
                    items(uiState.searchList, key = { it.id }) {
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
                            },
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    }
                }
            }
            if(dateCheckboxFrom) {
                DatePickerDialog(
                    value = if(dateFrom == "") null else LocalDate.parse(dateFrom, DateTimeFormatter
                        .ofPattern("yyyy/MM/dd"))
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()
                        .toEpochMilli(),
                    onDismiss = { dateCheckboxFrom = false },
                    picked = { millis ->
                        if(millis != null) {
                            val instant = Instant.ofEpochMilli(millis)
                            val date = instant.atZone(ZoneOffset.UTC).toLocalDate()
                            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                            dateFrom = date.format(formatter)
                        }
                        dateCheckboxFrom = false
                    }
                )
            }
            if(dateCheckboxTo) {
                DatePickerDialog(
                    value = if(dateTo == "") null else LocalDate.parse(dateTo, DateTimeFormatter
                        .ofPattern("yyyy/MM/dd"))
                        .atStartOfDay(ZoneOffset.UTC)
                        .toInstant()
                        .toEpochMilli(),
                    onDismiss = { dateCheckboxTo = false },
                    picked = { millis ->
                        if(millis != null) {
                            val instant = Instant.ofEpochMilli(millis)
                            val date = instant.atZone(ZoneOffset.UTC).toLocalDate()
                            val formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")
                            dateTo = date.format(formatter)
                        }
                        dateCheckboxTo = false
                    }
                )
            }
        }
    }
}