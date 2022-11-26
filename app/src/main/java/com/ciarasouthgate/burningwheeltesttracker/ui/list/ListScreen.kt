package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.ui.common.AppBarWithSearch
import com.ciarasouthgate.burningwheeltesttracker.ui.common.LoadingPage
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.ListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <ItemType> ListScreen(
    title: String,
    onAddClicked: () -> Unit,
    emptyText: String,
    viewModel: ListViewModel<ItemType>,
    navigationIcon: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    val items by viewModel.items.collectAsState(null)
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
                Icon(Icons.Default.Add, stringResource(R.string.add))
            }
        }
    ) { paddingValues ->
        if (items != null) {
            if (items!!.isEmpty()) {
                Text(
                    emptyText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                        .padding(top = 20.dp)
                        .alpha(0.75f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            } else {
                content(paddingValues)
            }
        } else {
            LoadingPage()
        }
    }
}