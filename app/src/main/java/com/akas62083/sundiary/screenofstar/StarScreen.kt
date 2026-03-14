package com.akas62083.sundiary.screenofstar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.akas62083.sundiary.R

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StarScreen(
    viewModel: StarViewModel,
    navController: NavController,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(0.00001.dp),
                title = {
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(0.00001.dp)
            ){}
        }
    ) { innerPadding ->
        BoxWithConstraints(modifier = Modifier.padding(innerPadding).fillMaxSize().background(Color(0xff070A38))) {
            viewModel.onEvent(StarEvnet.ChengeMaxHeight(maxHeight, maxWidth))
            uiState.offsetList.forEach {
                Icon(
                    painter = painterResource(
                        when(it.star) {
                            1 -> R.drawable.star3
                            2 -> R.drawable.star4
                            3 -> R.drawable.star5
                            4 -> R.drawable.star6
                            else -> R.drawable.star7
                        }
                    ),
                    contentDescription = "star",
                    modifier = Modifier.offset(it.x, it.y),
                    tint = {
                        when (it.color) {
                            1 -> Color(0xffffff33)
                            2 -> Color(0xffffd700)
                            3 -> Color(0xffffc107)
                            4 -> Color(0xfffff176)
                            5 -> Color(0xffe6c200)
                            else -> Color(0xffa6201a)
                        }
                    }
                )
            }
        }
    }
}