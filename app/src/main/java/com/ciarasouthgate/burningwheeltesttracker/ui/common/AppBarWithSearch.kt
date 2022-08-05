package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.ui.theme.TestTrackerTheme

@Composable
fun AppBarWithSearch(
    title: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {}
) {
    var searchValue by remember { mutableStateOf("") }
    var isSearch by remember { mutableStateOf(false) }

    AnimatedVisibility(
        visible = isSearch,
        enter = slideInHorizontally { it },
        exit = slideOutHorizontally { it }
    ) {
        SearchAppBar(
            searchText = searchValue,
            onChanged = {
                searchValue = it
                onChange(it)
            },
            onBackPressed = {
                isSearch = false
                searchValue = ""
                onChange("")
            },
            modifier = modifier
        )
    }
    AnimatedVisibility(
        visible = !isSearch,
        enter = slideInHorizontally { -it },
        exit = slideOutHorizontally { -it } + fadeOut()
    ) {
        DefaultAppBar(
            title = title,
            onSearchPressed = { isSearch = true },
            modifier = modifier,
            navigationIcon = navigationIcon
        )
    }
}

@Composable
private fun DefaultAppBar(
    title: String,
    onSearchPressed: () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                title,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(onClick = onSearchPressed) {
                Icon(
                    Icons.Default.Search,
                    stringResource(R.string.search)
                )
            }
        },
        navigationIcon = navigationIcon,
        modifier = modifier
    )
}

@Composable
private fun SearchAppBar(
    searchText: String,
    onChanged: (String) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    SmallTopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(
                    Icons.Default.Cancel,
                    stringResource(R.string.cancel)
                )
            }
        },
        title = {
            TextField(
                value = searchText,
                onValueChange = onChanged,
                placeholder = { Text(stringResource(R.string.search)) },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.focusRequester(focusRequester)
            )
        },
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
fun SearchBarPreview() {
    TestTrackerTheme {
        AppBarWithSearch("Test", {})
    }
}