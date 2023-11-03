package com.example.tasks2

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentTasks : Fragment(R.layout.fragment_tasks) {

    private val viewModel: TasksViewModel by viewModels()
}