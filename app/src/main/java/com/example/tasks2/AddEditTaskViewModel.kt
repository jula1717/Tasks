package com.example.tasks2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskDao: TaskDao,
) : ViewModel() {



    private  val addEditTaskEventChannel = Channel<AddEditTasksEvent>()
    val addEditTasksEvent = addEditTaskEventChannel.receiveAsFlow()

    fun onSaveClick(name: String, importance: Boolean, task: Task?) {
        if (name.isBlank()) {
            showInvalidInputMessage("Name cannot be empty")
            return
        }

        if (task != null) {
            val updatedTask = task.copy(name = name, important = importance)
            addUpdateTask(updatedTask, UPDATE_TASK_RESULT_OK)
        } else {
            val newTask = Task(name, importance)
            addUpdateTask(newTask, ADD_TASK_RESULT_OK)
        }
    }

    private fun showInvalidInputMessage(message: String) =viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTasksEvent.ShowInvalidInput(message))
    }

    private fun addUpdateTask(updatedTask: Task,result:Int) = viewModelScope.launch {
        taskDao.upsert(updatedTask)
        addEditTaskEventChannel.send(AddEditTasksEvent.NavigateBackWithResult(result))
    }

    sealed class AddEditTasksEvent {
        data class ShowInvalidInput(val message:String) : AddEditTasksEvent()
        data class NavigateBackWithResult(val result:Int) : AddEditTasksEvent()
    }

}
