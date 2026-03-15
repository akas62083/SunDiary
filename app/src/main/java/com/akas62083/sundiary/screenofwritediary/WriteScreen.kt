package com.akas62083.sundiary.screenofwritediary

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.akas62083.sundiary.screenofwritediary.component.SelectScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WriteScreen(
    viewModel: WriteViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    WriteContent(
        uiState = uiState,
        navController = navController,
        onEvent = viewModel::onEvent,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteContent(
    uiState: WriteUiState,
    navController: NavController,
    onEvent: (WriteEvent) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        uri?.let {
            onEvent(WriteEvent.OnImageSelected(it.toString()))
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text(uiState.localDate.toString())
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                onEvent(WriteEvent.SaveDiary)
                                navController.popBackStack()
                            },
                            enabled = uiState.check()
                        ) { Text("保存") }
                    }
                }
            )
        },
        bottomBar = {

        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).padding(horizontal = 10.dp)) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("タイトル")
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiState.title,
                            onValueChange = { onEvent(WriteEvent.UpdateTitle(it)) },
                            singleLine = true
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("今日の地球")
                        Spacer(modifier = Modifier.height(5.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            SelectScreen(
                                select = Wether.Sunny,
                                onEvent = onEvent,
                                uiState = uiState
                            )
                            SelectScreen(
                                select = Wether.Cloudy,
                                onEvent = onEvent,
                                uiState = uiState
                            )
                            SelectScreen(
                                select = Wether.Rainy,
                                onEvent = onEvent,
                                uiState = uiState
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text("内容")
                        TextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = uiState.content,
                            onValueChange = { onEvent(WriteEvent.UpdateContent(it)) },
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center ) {
                    Button(
                        onClick = {
                            launcher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    ) {
                        Text("写真")
                    }
                }
            }
        }
    }
}
