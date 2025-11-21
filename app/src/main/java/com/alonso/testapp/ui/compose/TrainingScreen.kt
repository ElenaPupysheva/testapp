package com.alonso.testapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.alonso.testapp.domain.models.TrainingUiState
import com.alonso.testapp.presentation.TrainingTab
import com.alonso.testapp.presentation.TrainingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrainingScreen(
    navController: NavHostController,
    viewModel: TrainingViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Интервальная тренировка",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(16.dp))

        IntervalsBar(uiState = uiState)

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Общая длительность: ${formatSeconds(uiState.totalDurationSec)}",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(16.dp))

        TrainingTabs(
            selectedTab = uiState.selectedTab,
            onTabSelected = viewModel::onTabSelected
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when (uiState.selectedTab) {
                TrainingTab.TIMER -> TimerTabContent(
                    uiState = uiState,
                    onStartStopClick = viewModel::onStartStopClick
                )

                TrainingTab.MAP -> MapTabContent()
            }
        }

        Spacer(Modifier.height(16.dp))

        IntervalsList(uiState = uiState)
    }
}

@Composable
private fun IntervalsBar(uiState: TrainingUiState) {
    if (uiState.intervals.isEmpty()) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    ) {
        uiState.intervals.forEachIndexed { index, interval ->
            val isCurrent = index == uiState.currentIntervalIndex
            Box(
                modifier = Modifier
                    .weight(interval.durationSec.coerceAtLeast(1).toFloat())
                    .fillMaxHeight()
                    .padding(horizontal = 1.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        if (isCurrent)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.primaryContainer
                    )
            )
        }
    }
}

@Composable
private fun TrainingTabs(
    selectedTab: TrainingTab,
    onTabSelected: (TrainingTab) -> Unit
) {
    val tabs = listOf(
        TrainingTab.TIMER to "Таймер",
        TrainingTab.MAP to "Карта"
    )

    TabRow(
        selectedTabIndex = tabs.indexOfFirst { it.first == selectedTab }.coerceAtLeast(0)
    ) {
        tabs.forEachIndexed { index, (tab, title) ->
            Tab(
                selected = tab == selectedTab,
                onClick = { onTabSelected(tab) },
                text = { Text(title) }
            )
        }
    }
}
@Composable
private fun TimerTabContent(
    uiState: TrainingUiState,
    onStartStopClick: () -> Unit
) {
    val currentIndex =
        if (uiState.currentIntervalIndex < 0) 0 else uiState.currentIntervalIndex
    val currentInterval = uiState.intervals.getOrNull(currentIndex)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = currentInterval?.title ?: if (uiState.isFinished) "Тренировка завершена" else "Готово к старту",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Интервал: ${formatSeconds(uiState.elapsedInCurrentSec)} / ${
                formatSeconds(
                    currentInterval?.durationSec ?: 0
                )
            }"
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = "Всего: ${formatSeconds(uiState.elapsedTotalSec)} / ${
                formatSeconds(
                    uiState.totalDurationSec
                )
            }"
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onStartStopClick,
            enabled = uiState.intervals.isNotEmpty()
        ) {
            Text(if (uiState.isRunning) "Стоп" else "Старт")
        }
    }
}

@Composable
private fun MapTabContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text("Здесь будет карта и GPS-трек")
    }
}

@Composable
private fun IntervalsList(uiState: TrainingUiState) {
    if (uiState.intervals.isEmpty()) {
        Text(
            text = "Интервалы не загружены",
            style = MaterialTheme.typography.bodyMedium
        )
        return
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        uiState.intervals.forEachIndexed { index, interval ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${index + 1}. ${interval.title}")
                Text(text = formatSeconds(interval.durationSec))
            }
        }
    }
}

private fun formatSeconds(totalSec: Int): String {
    val sec = totalSec.coerceAtLeast(0)
    val m = sec / 60
    val s = sec % 60
    return "%02d:%02d".format(m, s)
}
