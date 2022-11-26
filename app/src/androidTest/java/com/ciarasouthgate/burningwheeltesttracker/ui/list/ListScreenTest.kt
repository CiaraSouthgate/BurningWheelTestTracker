package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.material3.Text
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.ciarasouthgate.burningwheeltesttracker.R
import com.ciarasouthgate.burningwheeltesttracker.ui.common.CIRCULAR_PROGRESS_INDICATOR_TAG
import com.ciarasouthgate.burningwheeltesttracker.util.getString
import com.ciarasouthgate.burningwheeltesttracker.viewmodel.list.ListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test

class ListScreenTest {
    @get:Rule
    val testRule = createComposeRule()

    private val title = "Test Title"
    private val emptyText = "No items found"
    private val contentText = "Content!"

    @Test
    fun testDisplaysElements_loading() {
        val flow = flow<List<String>> {}
        val viewModel = createViewModel(flow)

        testRule.setContent {
            ListScreen(
                title = title,
                onAddClicked = {},
                emptyText = emptyText,
                viewModel = viewModel
            ) {
                Text(contentText)
            }
        }

        testRule.onNodeWithText(title)
            .assertIsDisplayed()

        testRule.onNodeWithContentDescription(getString(R.string.add))
            .assertIsDisplayed()

        testRule.onNodeWithTag(CIRCULAR_PROGRESS_INDICATOR_TAG)
            .assertIsDisplayed()

        testRule.onNodeWithText(emptyText)
            .assertDoesNotExist()

        testRule.onNodeWithText(contentText)
            .assertDoesNotExist()
    }

    @Test
    fun testDisplaysElements_empty() {
        val flow = flowOf(emptyList<String>())
        val viewModel = createViewModel(flow)

        testRule.setContent {
            ListScreen(
                title = title,
                onAddClicked = {},
                emptyText = emptyText,
                viewModel = viewModel
            ) {
                Text(contentText)
            }
        }

        testRule.onNodeWithText(title)
            .assertIsDisplayed()

        testRule.onNodeWithContentDescription(getString(R.string.add))
            .assertIsDisplayed()

        testRule.onNodeWithTag(CIRCULAR_PROGRESS_INDICATOR_TAG)
            .assertDoesNotExist()

        testRule.onNodeWithText(emptyText)
            .assertIsDisplayed()

        testRule.onNodeWithText(contentText)
            .assertDoesNotExist()
    }

    @Test
    fun testDisplaysElements_notEmpty() {
        val flow = flowOf(listOf(contentText))
        val viewModel = createViewModel(flow)

        testRule.setContent {
            ListScreen(
                title = title,
                onAddClicked = {},
                emptyText = emptyText,
                viewModel = viewModel
            ) {
                Text(contentText)
            }
        }

        testRule.onNodeWithText(title)
            .assertIsDisplayed()

        testRule.onNodeWithContentDescription(getString(R.string.add))
            .assertIsDisplayed()

        testRule.onNodeWithTag(CIRCULAR_PROGRESS_INDICATOR_TAG)
            .assertDoesNotExist()

        testRule.onNodeWithText(emptyText)
            .assertDoesNotExist()

        testRule.onNodeWithText(contentText)
            .assertIsDisplayed()
    }

    private fun createViewModel(flow: Flow<List<String>>) =
        object : ListViewModel<String> {
            override val items = getAll()
            override fun getAll() = flow
            override fun filterList(searchText: String) {}
            override fun onSearchTextChanged(text: String) {}
            override fun deleteListItem(item: String) {}
        }
}