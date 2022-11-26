package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T, U> ListSectionWithHeader(
    grouped: Map<T, List<U>>,
    key: (U) -> Any,
    modifier: Modifier = Modifier,
    labelMap: Map<T, String>? = null,
    spaceAtBottom: Boolean = false,
    itemContent: @Composable LazyItemScope.(U) -> Unit
) {
    LazyColumn(modifier = modifier) {
        grouped.forEach { (category, content) ->
            stickyHeader {
                Surface(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    tonalElevation = 1.dp,
                    shadowElevation = 1.dp
                ) {
                    Text(
                        labelMap?.get(category) ?: category.toString(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            itemsIndexed(content, { _, item -> key(item) }) { index, item ->
                itemContent(item)
                if (index < content.lastIndex) {
                    Divider(thickness = 0.5.dp)
                }
            }
        }
        if (spaceAtBottom) {
            item(-1) {
                // empty item to keep the FAB from blocking the bottom
                Box(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Preview
@Composable
private fun ListSectionWithHeaderPreview() {
    val grouped = List(100) { it }.groupBy { it.toString()[0] }
    AppTheme {
        ListSectionWithHeader(grouped = grouped, key = { it }) {
            Text(it.toString())
        }
    }
}