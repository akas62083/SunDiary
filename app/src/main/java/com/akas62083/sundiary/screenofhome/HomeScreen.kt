package com.akas62083.sundiary.screenofhome

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.akas62083.sundiary.screenofhome.composable.ItemCard
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(LocalDate.now().toString())
                }
            )
        },
        bottomBar = {
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .padding(horizontal = 10.dp)
        ) {
            if(!uiState.isWroteDiaryToday) {
                Spacer(modifier = Modifier.height(10.dp))
                Card(
                    modifier = Modifier.height(90.dp) //160
                        .fillMaxWidth()
                        .clickable { navController.navigate("write") },
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Row {
                            Spacer(modifier = Modifier.width(30.dp))
                            Box(modifier = Modifier.weight(1f).padding(7.dp)) {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    imageVector = Icons.Default.Edit,
                                    contentDescription = "sun_mark"
                                )
                            }
                            Box(modifier = Modifier.weight(7f).fillMaxSize(), contentAlignment = Alignment.Center) {
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
            LazyColumn() {
                items(uiState.diaries, key = { it.id }) {
                    ItemCard(
                        diary = it,
                        uiState = uiState
                    )
                }
            }
        }
    }
}