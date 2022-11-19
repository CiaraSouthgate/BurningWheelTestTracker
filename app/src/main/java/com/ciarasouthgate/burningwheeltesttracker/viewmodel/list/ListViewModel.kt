package com.ciarasouthgate.burningwheeltesttracker.viewmodel.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

interface ListViewModel<ItemType> {
    val items: Flow<List<ItemType>>
    fun getAll(): Flow<List<ItemType>>
    fun filterList(searchText: String)
    fun onSearchTextChanged(text: String)
    fun deleteListItem(item: ItemType)
}

abstract class ListViewModelImpl<ItemType> : ListViewModel<ItemType>, ViewModel() {
    override fun onSearchTextChanged(text: String) = filterList(text)
}