package com.example.tasks2

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.tasks2.databinding.FragmentAddEditTaskBinding
import com.google.android.material.snackbar.Snackbar
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

            fabSaveTask.setOnClickListener{
                val name = edittextTask.text.toString()
                val importance = checkboxImportant.isChecked
                viewModel.onSaveClick(name,importance,task)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addEditTasksEvent.collect { event ->
                when (event) {
                    is AddEditTaskViewModel.AddEditTasksEvent.NavigateBackWithResult -> {
                        binding.edittextTask.clearFocus()
                        setFragmentResult(
                            "add_edit_request", bundleOf("add_edit_result" to event.result)
                        )
                        findNavController().popBackStack()
                    }
                    is AddEditTaskViewModel.AddEditTasksEvent.ShowInvalidInput -> {
                        Snackbar.make(requireView(),event.message,Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}