package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun <T> MultiCounterRow(
    titleRes: Int,
    fields: Array<T>,
    getLabel: (T) -> Int,
    values: SnapshotStateMap<T, Int>,
    modifier: Modifier = Modifier,
    maxValue: Int = Int.MAX_VALUE,
    minValue: Int = 0
) {
    FormSection(
        titleRes = titleRes,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            fields.forEach {
                FormCounter(
                    label = stringResource(getLabel(it)),
                    value = values[it]!!,
                    onIncrement = {
                        val value = values[it]!!
                        if (value < maxValue) values[it] = value + 1
                    },
                    onDecrement = {
                        val value = values[it]!!
                        if (value > minValue) values[it] = value - 1
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

