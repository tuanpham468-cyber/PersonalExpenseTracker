package com.example.personalexpensetracker.presentation.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryModelOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Income/expense chart by month", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (state.labels.isNotEmpty()) {
            IncomeExpenseBarChart(
                income = state.incomePerMonth,
                expense = state.expensePerMonth,
                labels = state.labels
            )
        } else {
            Text("No data available")
        }
    }
}

@Composable
fun IncomeExpenseBarChart(
    income: List<Float>,
    expense: List<Float>,
    labels: List<String>
) {
    val entries = income.indices.flatMap { index ->
        listOf(
            FloatEntry(x = index.toFloat(), y = income[index]),
            FloatEntry(x = index.toFloat() + 0.3f, y = expense[index])
        )
    }

    val incomeEntries = income.mapIndexed { index, value -> FloatEntry(index.toFloat(), value) }
    val expenseEntries = expense.mapIndexed { index, value -> FloatEntry(index.toFloat(), value) }

    val model = entryModelOf(incomeEntries + expenseEntries)

    val colors = listOf(Color(0xFF4CAF50), Color(0xFFF44336)) // Thu (xanh), Chi (đỏ)

    Chart(
        chart = columnChart(
            spacing = 20.dp,
            columns = listOf(

            )
        ),
        model = model,
        startAxis = rememberStartAxis(),
        bottomAxis = rememberBottomAxis(
            valueFormatter = { value, _ ->
                labels.getOrNull(value.toInt()) ?: ""
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row {

    }
}