package com.akas62083.sundiary.screenofdetail

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.akas62083.sundiary.screenofdetail.component.DiaryContent
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.roundToInt

enum class RL {
    Right,
    Left
}
@SuppressLint("ConfigurationScreenWidthHeight")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current
    val screenWidthPx = with(density) { LocalConfiguration.current.screenWidthDp.dp.toPx() }
    val slideRL = remember { mutableStateOf(RL.Right) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                            modifier = Modifier.clickable { navController.popBackStack() }
                        )
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(LocalDate.now().toString())
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if(slideRL.value == RL.Left) {
                DiaryContent(
                    diary =  uiState.nextDiary,
                    commentAi = { viewModel.onEvent(DetailEvent.CommentAi) },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                DiaryContent(
                    diary = uiState.backDiary,
                    commentAi = { viewModel.onEvent(DetailEvent.CommentAi) },
                    modifier = Modifier.fillMaxSize()
                )
            }
            DiaryContent(
                diary = uiState.diary,
                modifier = Modifier.offset { IntOffset(offsetX.value.roundToInt(), 0) }
                    .pointerInput(screenWidthPx) {
                        detectDragGestures(
                            onDrag = { change, dragAmount ->
                                change.consume()
                                scope.launch {
                                    offsetX.snapTo(offsetX.value + dragAmount.x)
                                    if(slideRL.value == RL.Right && offsetX.value < 0) {
                                        slideRL.value = RL.Left
                                    } else if(slideRL.value == RL.Left && offsetX.value > 0){
                                        slideRL.value = RL.Right
                                    }
                                }
                            },
                            onDragEnd = {
                                scope.launch {
                                    if(offsetX.value < -screenWidthPx * 0.3f) {
                                        offsetX.animateTo(-screenWidthPx)
                                        viewModel.onEvent(DetailEvent.OnNext)
                                        offsetX.snapTo(0f)
                                    } else if(offsetX.value > screenWidthPx * 0.3f) {
                                        offsetX.animateTo(screenWidthPx)
                                        viewModel.onEvent(DetailEvent.OnBack)
                                        offsetX.snapTo(0f)
                                    } else {
                                        offsetX.animateTo(0f)
                                    }
                                }
                            },
                        )
                    }
                    .rotate(offsetX.value / 50f),
                commentAi = { viewModel.onEvent(DetailEvent.CommentAi) }
            )
        }
    }
}
