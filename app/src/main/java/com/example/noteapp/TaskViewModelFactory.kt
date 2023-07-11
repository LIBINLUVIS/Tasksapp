package com.example.noteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.db.TasksDoa
import java.lang.IllegalArgumentException

class TaskViewModelFactory(
    private val dao: TasksDoa
):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskViewModel::class.java)){
            return TaskViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}