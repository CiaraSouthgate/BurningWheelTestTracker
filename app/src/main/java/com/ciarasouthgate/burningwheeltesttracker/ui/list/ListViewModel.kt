package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

abstract class ListViewModel<ItemType> : ViewModel() {
    var items by mutableStateOf<List<ItemType>>(emptyList())

    abstract suspend fun getUpdatedList(searchText: String): List<ItemType>

    fun onSearchTextChanged(text: String) {
        viewModelScope.launch {
            val updatedList = getUpdatedList(text)
            items = updatedList
        }
    }
}