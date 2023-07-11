package com.example.noteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.db.Tasks
import com.example.noteapp.db.TasksDoa
import kotlinx.coroutines.launch

class TaskViewModel(private val dao:TasksDoa) : ViewModel() {

    val tasks = dao.getAllTasks()
//      val tasks= listOf<String>("heyy","hello","test")

    fun insertTask(task: Tasks)=viewModelScope.launch {
        dao.insertTask(task)
    }

    fun updateTask(task: Tasks)=viewModelScope.launch {
        dao.updateTask(task)
    }

    fun deleteTask(task: Tasks)=viewModelScope.launch {
        dao.deleteTask(task)
    }

}