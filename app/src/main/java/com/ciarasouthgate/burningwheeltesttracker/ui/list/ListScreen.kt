package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.ui.common.AppBarWithSearch
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.ListViewModel

@Composable
fun <ItemType> ListScreen(
    title: String,
    onAddClicked: () -> Unit,
    emptyText: String,
    viewModel: ListViewModel<ItemType>,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val items by viewModel.items.collectAsState(emptyList())
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
        if (items.isEmpty()) {
            Text(
                emptyText,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .alpha(0.75f),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body1
            )
        } else {
            content(paddingValues)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onSearchTextChanged("")
    }
}