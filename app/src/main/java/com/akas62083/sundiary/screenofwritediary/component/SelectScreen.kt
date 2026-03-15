package com.akas62083.sundiary.screenofwritediary.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akas62083.sundiary.R
import com.akas62083.sundiary.screenofwritediary.Wether
import com.akas62083.sundiary.screenofwritediary.WriteEvent
import com.akas62083.sundiary.screenofwritediary.WriteUiState

@Composable
fun SelectScreen(
    select: Wether,
    onEvent: (WriteEvent) -> Unit,
    uiState: WriteUiState
) {
    Card(
        modifier = Modifier.border (
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.outline,
            width = if(uiState.selected == select) 3.dp else 1.dp,
        )
            .background(
                color = if(uiState.selected == select) MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onEvent(WriteEvent.UpdateSelected(select))}
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.background(
                color = if(uiState.selected == select) MaterialTheme.colorScheme.primaryContainer
                else Color.Transparent
            ),
            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.background(
                    color = if(uiState.selected == select) MaterialTheme.colorScheme.primaryContainer
                    else Color.Transparent
                ),
                painter = painterResource(
                    if(select == Wether.Cloudy) R.drawable.sun_and_cloud
                    else if(select == Wether.Sunny) R.drawable.sunny
                    else R.drawable.cloudy
                ),
                contentDescription = select.toString()
            )
            Text(
                text = if(select == Wether.Sunny) "良く見えた" else if(select == Wether.Cloudy) "雲が邪魔だった" else "見えなかった",
                modifier = Modifier.background(
                    color = if(uiState.selected == select) MaterialTheme.colorScheme.primaryContainer
                    else Color.Transparent
                ),
                maxLines = 1
            )
        }
    }
}