package com.akas62083.sundiary.screenofhome.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.sharp.ArrowForward
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akas62083.sundiary.screenofhome.HomeUiState
import com.akas62083.sundiary.screenofhome.IsSearch
import com.akas62083.sundiary.screenofhome.composable.ItemCard

@Composable
fun SearchScreenTab(
    uiState: HomeUiState,
    clickTitleCheckBox: () -> Unit,
    clickContentCheckBox: () -> Unit,
    clickCommentCheckBox: () -> Unit,
    search: (String) -> Unit,
    isLikeClick: (Long) -> Unit,
    editClick: (Long) -> Unit,
    navigateToDetailScreen: (Long) -> Unit,
) {
    var serchwords by remember { mutableStateOf("") }
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
                        modifier = Modifier.clickable { search(serchwords) }
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
                            }
                        )
                    }
                }
            }
        }
    }
}