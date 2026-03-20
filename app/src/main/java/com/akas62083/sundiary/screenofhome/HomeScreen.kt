package com.akas62083.sundiary.screenofhome

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.akas62083.sundiary.Route
import com.akas62083.sundiary.screenofhome.screen.HomeScreenTab
import com.akas62083.sundiary.screenofhome.screen.SearchScreenTab
import kotlinx.coroutines.launch
import java.time.LocalDate

data class IndexClass(
    val index: Int,
    val date: Int
)
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val indexList = remember(uiState.diaries) {
        val list = mutableListOf<IndexClass>()
        uiState.diaries.forEachIndexed { index, diary ->
            val currentYM = diary.date / 100
            val prevYM = if(index > 0) uiState.diaries[index - 1].date / 100 else -1
            if(currentYM != prevYM) {
                list.add(IndexClass(date = currentYM, index = index))
            }
        }
        list
    }
    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }
    BackHandler(enabled = uiState.screen == Screens.Search) {
        viewModel.onEvent(HomeEvent.TabChange(Screens.Home))
    }
    BackHandler(enabled = uiState.screen == Screens.Home) {

    }
    ModalNavigationDrawer(
        gesturesEnabled = uiState.screen == Screens.Home,
        drawerContent = {
            ModalDrawerSheet {
                if(uiState.screen == Screens.Home) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "総日記",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleLarge
                        )
                        HorizontalDivider()
                        indexList.forEach {
                            NavigationDrawerItem(
                                label = {
                                    Text(
                                        it.date.toString()
                                            .substring(0, 4) + "年" + it.date.toString()
                                            .substring(4, 6) + "月"
                                    )
                                },
                                onClick = {
                                    scope.launch {
                                        listState.animateScrollToItem(it.index)
                                        drawerState.close()
                                    }
                                },
                                selected = false
                            )
                        }
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        with(animatedVisibilityScope) {
            with(sharedTransitionScope) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            modifier = Modifier
                                .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                                .animateEnterExit(
                                    enter = fadeIn() + slideInVertically { it },
                                    exit = fadeOut() + slideOutVertically { it }
                                ),
                            title = {
                                AnimatedContent(
                                    targetState = uiState.screen,
                                    transitionSpec = {
                                        if (targetState == Screens.Home) {
                                            slideInHorizontally { -it } with slideOutHorizontally { it }
                                        } else {
                                            slideInHorizontally { it } with slideOutHorizontally { -it }
                                        }
                                    },
                                    label = "topbar"
                                ) { state ->
                                    when (state) {
                                        Screens.Home -> {
                                            Row(
                                                modifier = Modifier.fillMaxWidth()
                                                    .height(IntrinsicSize.Min)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Menu,
                                                    contentDescription = null,
                                                    modifier = Modifier.clickable {
                                                        scope.launch {
                                                            if (drawerState.isClosed) drawerState.open()
                                                            else drawerState.close()
                                                        }
                                                    },
                                                )
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text(LocalDate.now().toString())
                                                Spacer(modifier = Modifier.width(5.dp))
                                                VerticalDivider()
                                                Spacer(modifier = Modifier.width(5.dp))
                                                Text("総日記数：" + uiState.diaries.size.toString())
                                                Spacer(modifier = Modifier.weight(1f))
                                                Icon(
                                                    imageVector = Icons.Default.Star,
                                                    contentDescription = "star",
                                                    modifier = Modifier.clickable(
                                                        interactionSource = null,
                                                        indication = null
                                                    ) {
                                                        navController.navigate(
                                                            Route.StarScreen
                                                        )
                                                    }
                                                        .fillMaxHeight(),
                                                    tint = Color(0xffa6201a)
                                                )
                                                Spacer(modifier = Modifier.width(10.dp))
                                                Icon(
                                                    imageVector = Icons.Default.Settings,
                                                    contentDescription = "settings",
                                                    modifier = Modifier.clickable(
                                                        interactionSource = null,
                                                        indication = null
                                                    ) {

                                                    }
                                                        .fillMaxHeight(),
                                                    tint = Color(0xffa6201a)
                                                )
                                                Spacer(modifier = Modifier.width(20.dp))
                                            }
                                        }

                                        Screens.Search -> {
                                            Text("検索")
                                        }
                                    }
                                }

                            }
                        )
                    },
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
                                .animateEnterExit(
                                    enter = fadeIn() + slideInVertically { it },
                                    exit = fadeOut() + slideOutVertically { it }
                                )
                                .height(100.dp)
                                .height(IntrinsicSize.Min)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Icon(
                                    modifier = Modifier.padding(15.dp)
                                        .aspectRatio(1f)
                                        .fillMaxSize()
                                        .clickable(
                                            interactionSource = null,
                                            indication = null
                                        ) { viewModel.onEvent(HomeEvent.TabChange(Screens.Home)) },
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "home",
                                    tint = if (uiState.screen == Screens.Home) Color(0xffa6201a) else Color(
                                        0xff000000
                                    )
                                )
                                Icon(
                                    modifier = Modifier.padding(15.dp)
                                        .aspectRatio(1f)
                                        .fillMaxSize()
                                        .clickable(
                                            interactionSource = null,
                                            indication = null
                                        ) { viewModel.onEvent(HomeEvent.TabChange(Screens.Search)) },
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search",
                                    tint = if (uiState.screen == Screens.Search) Color(0xffa6201a) else Color(
                                        0xff000000
                                    )
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        AnimatedContent(
                            targetState = uiState.screen,
                            transitionSpec = {
                                if (targetState == Screens.Home) {
                                    slideInHorizontally { -it } with slideOutHorizontally { it }
                                } else {
                                    slideInHorizontally { it } with slideOutHorizontally { -it }
                                }
                            },
                            label = "screen"
                        ) { state ->
                            when (state) {
                                Screens.Home -> {
                                    HomeScreenTab(
                                        uiState = uiState,
                                        navigateToWriteScreen = {
                                            navController.navigate(
                                                Route.WriteScreen(
                                                    null
                                                )
                                            )
                                        },
                                        modifier = Modifier,
                                        listState = listState,
                                        isLikeClick = { viewModel.isLikeClick(it) },
                                        editClick = { navController.navigate(Route.WriteScreen(it)) },
                                        navigateToDetailScreen = {
                                            navController.navigate(
                                                Route.DetailScreen(
                                                    it
                                                )
                                            )
                                        },
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionScope = sharedTransitionScope
                                    )
                                }

                                Screens.Search -> {
                                    SearchScreenTab(
                                        uiState = uiState,
                                        clickTitleCheckBox = { viewModel.onEvent(HomeEvent.ClickTitleCheckBox) },
                                        clickContentCheckBox = { viewModel.onEvent(HomeEvent.ClickContentCheckBox) },
                                        clickCommentCheckBox = { viewModel.onEvent(HomeEvent.ClickCommentCheckBox) },
                                        search = { word, from, to ->
                                            viewModel.onEvent(HomeEvent.Search(word, from, to))
                                        },
                                        isLikeClick = { viewModel.isLikeClick(it) },
                                        editClick = { navController.navigate(Route.WriteScreen(it)) },
                                        navigateToDetailScreen = {
                                            navController.navigate(
                                                Route.DetailScreen(
                                                    it
                                                )
                                            )
                                        },
                                        clickIsLikeCheckBox = { viewModel.onEvent(HomeEvent.ClickIsLikeCheckBox) },
                                        clickNotEditCheckBox = { viewModel.onEvent(HomeEvent.ClickNotEditCheckBox) },
                                        clickEditCheckBox = { viewModel.onEvent(HomeEvent.ClickEditCheckBox) },
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        sharedTransitionScope = sharedTransitionScope
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}