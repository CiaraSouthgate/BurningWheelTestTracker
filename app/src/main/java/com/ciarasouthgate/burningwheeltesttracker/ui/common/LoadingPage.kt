package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag

const val CIRCULAR_PROGRESS_INDICATOR_TAG = "circularProgressIndicator"

@Composable
fun LoadingPage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = BiasAlignment(0f, -0.3f)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics { testTag = CIRCULAR_PROGRESS_INDICATOR_TAG }
        )
    }
}