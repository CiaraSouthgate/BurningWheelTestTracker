package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
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
        itemsIndexed(
            items,
            { _, item -> key(item) }
        ) { index, item ->
            itemContent(item)
            if (index < items.lastIndex) {
                Divider(thickness = 0.5.dp)
            }
        }
    }
}