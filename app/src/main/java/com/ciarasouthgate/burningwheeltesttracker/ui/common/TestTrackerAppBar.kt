package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TestTrackerAppBar(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
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
        actions = actions,
        navigationIcon = navigationIcon,
        modifier = modifier
    )
}