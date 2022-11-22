package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> ColumnListWithDividers(
    modifier: Modifier = Modifier,
    items: List<T>,
    key: (T) -> Any,
    itemContent: @Composable LazyItemScope.(T) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items, key) { item ->
            itemContent(item)
            Divider(thickness = 0.5.dp)
        }
    }
}