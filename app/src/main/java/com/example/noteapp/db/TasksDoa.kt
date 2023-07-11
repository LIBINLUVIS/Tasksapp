package com.example.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface TasksDoa {
    @Insert
    suspend fun insertTask(task: Tasks)

    @Update
    suspend fun updateTask(task: Tasks)

    @Delete
    suspend fun deleteTask(task: Tasks)

    @Query("SELECT * FROM todo_data_table")
    fun getAllTasks():LiveData<List<Tasks>>

}