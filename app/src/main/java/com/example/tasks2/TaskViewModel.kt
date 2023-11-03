package com.example.tasks2

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {
}