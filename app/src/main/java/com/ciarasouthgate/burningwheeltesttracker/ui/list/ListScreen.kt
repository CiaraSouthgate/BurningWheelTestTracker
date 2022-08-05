package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.ui.common.AppBarWithSearch

@Composable
fun <ItemType> ListScreen(
    title: String,
    onAddClicked: () -> Unit,
    emptyText: String,
    viewModel: ListViewModel<ItemType>,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            AppBarWithSearch(
                title = title,
                onChange = viewModel::onSearchTextChanged,
                navigationIcon = navigationIcon,
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClicked) {
                Icon(Icons.Default.Add, "Add")
            }
        }
    ) { paddingValues ->
        if (viewModel.items.isEmpty()) {
            Text(
                emptyText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                textAlign = TextAlign.Center
            )
        } else {
            content(paddingValues)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onSearchTextChanged("")
    }
}