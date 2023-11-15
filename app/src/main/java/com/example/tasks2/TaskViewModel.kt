package com.example.tasks2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {

   val searchQuery = MutableStateFlow("")

    val sortType = MutableStateFlow(SortType.BY_DATE)
    val hideCompleted = MutableStateFlow(false)

    private val tasksFlow = combine(
        searchQuery,
        sortType,
        hideCompleted
    ) { query, sortType, hideCompleted ->
        Triple(query, sortType, hideCompleted)
    }.flatMapLatest { (query, sortType, hideCompleted) ->
        taskDao.getTasks(query, sortType, hideCompleted)
    }

    val tasks = tasksFlow.asLiveData()
}