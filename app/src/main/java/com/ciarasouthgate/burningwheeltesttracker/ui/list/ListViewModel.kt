package com.ciarasouthgate.burningwheeltesttracker.ui.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class ListViewModel<ItemType> : ViewModel() {
    abstract val items: Flow<List<ItemType>>

    abstract fun getAll(): Flow<List<ItemType>>
    abstract fun filterList(searchText: String)

    fun onSearchTextChanged(text: String) = filterList(text)
}