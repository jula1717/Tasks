package com.example.tasks2

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.tasks2.databinding.FragmentAddEditTaskBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddEditTaskFragment : Fragment(R.layout.fragment_add_edit_task) {

    private val args by navArgs<AddEditTaskFragmentArgs>()

    private val viewModel:AddEditTaskViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentAddEditTaskBinding.bind(view)
        binding.apply {
            val task = args.task

            edittextTask.setText(task?.name ?: "")
            checkboxImportant.isChecked = task?.important ?: false
            checkboxImportant.jumpDrawablesToCurrentState()
            textviewDate.isVisible = task != null
            textviewDate.text = "Created: ${task?.dateFormated}"
        }

    }
}