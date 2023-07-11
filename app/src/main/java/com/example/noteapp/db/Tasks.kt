package com.example.noteapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_data_table")
data class Tasks(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    var id: Int,

    @ColumnInfo(name = "task")
    var task: String,

    @ColumnInfo(name = "status")
    var status:Boolean
)