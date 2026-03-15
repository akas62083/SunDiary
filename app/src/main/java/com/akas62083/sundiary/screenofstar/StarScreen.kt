package com.akas62083.sundiary.screenofstar

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.akas62083.sundiary.R
import kotlinx.coroutines.delay

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
                colors = topAppBarColors(
                    containerColor = Color(0xff070a38),
                ),
                title = {
                    Row {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "arrowback",
                            tint = Color.LightGray,
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "refresh",
                            tint = Color.LightGray,
                            modifier = Modifier.clickable{ viewModel.onEvent(StarEvent.Refresh) }
                                .size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.height(0.00001.dp)
            ){}
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xff070A38))
        ) {
            LaunchedEffect(maxHeight, maxWidth) {
                viewModel.onEvent(StarEvent.ChengeMaxHeight(maxHeight, maxWidth))
            }
            uiState.offsetList.forEachIndexed { index, it ->
                var isVisible by remember(it) { mutableStateOf(false) }
                LaunchedEffect(it) {
                    delay(index * 100L)
                    isVisible = true
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(500)),
                    exit = fadeOut(),
                    modifier = Modifier.offset(it.x, it.y)
                ) {
                    Icon(
                        painter = painterResource(
                            when (it.star) {
                                1 -> R.drawable.star3
                                2 -> R.drawable.star4
                                3 -> R.drawable.star5
                                4 -> R.drawable.star6
                                else -> R.drawable.star7
                            }
                        ),
                        contentDescription = "star",
                        tint = when (it.color) {
                            1 -> Color(0xffffff33)
                            2 -> Color(0xffffd700)
                            3 -> Color(0xffffc107)
                            4 -> Color(0xfffff176)
                            5 -> Color(0xffe6c200)
                            else -> Color(0xffa6201a)
                        }
                    )
                }
            }
        }
    }
}