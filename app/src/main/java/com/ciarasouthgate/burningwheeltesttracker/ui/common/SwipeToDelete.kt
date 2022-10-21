package com.ciarasouthgate.burningwheeltesttracker.ui.common

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ciarasouthgate.burningwheeltesttracker.R

private const val THRESHOLD = 0.3f

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDelete(
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    val dismissState = rememberDismissState()

    SwipeToDismiss(
        state = dismissState,
        modifier = modifier,
        dismissThresholds = { FractionalThreshold(THRESHOLD) },
        directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss

            val backgroundColour by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    DismissValue.DismissedToStart -> Color.Red
                    DismissValue.DismissedToEnd -> Color.Green
                    DismissValue.Default -> Color.LightGray
                }
            )

            val icon: ImageVector
            val alignment: Alignment
            val contentDescriptionRes: Int

            when (direction) {
                DismissDirection.EndToStart -> {
                    icon = Icons.Filled.Delete
                    alignment = Alignment.CenterEnd
                    contentDescriptionRes = R.string.delete
                }
                DismissDirection.StartToEnd -> {
                    icon = Icons.Filled.Edit
                    alignment = Alignment.CenterStart
                    contentDescriptionRes = R.string.edit
                }

            }

            val progress = dismissState.progress.fraction
            val alpha by animateFloatAsState(if (progress < THRESHOLD * .8) progress * 2 else 1.0f)
            val scale by animateFloatAsState(progress * 0.4F + 0.8F)

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backgroundColour)
                    .padding(horizontal = 16.dp),
                contentAlignment = alignment
            ) {
                Icon(
                    icon,
                    stringResource(contentDescriptionRes),
                    modifier = Modifier
                        .scale(scale)
                        .alpha(alpha),
                    tint = Color.White
                )
            }
        },
        dismissContent = content
    )

    when (dismissState.targetValue) {
        DismissValue.DismissedToStart -> onDelete()
        DismissValue.DismissedToEnd -> onEdit()
        else -> {}
    }

    if (dismissState.currentValue != DismissValue.Default) {
        LaunchedEffect(Unit) {
            dismissState.reset()
        }
    }
}