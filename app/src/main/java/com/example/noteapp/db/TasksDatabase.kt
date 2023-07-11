package com.example.noteapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Tasks::class], version = 1, exportSchema = false)
abstract class TasksDatabase : RoomDatabase() {
    abstract val tasksDao: TasksDoa

    companion object {
        @Volatile
        private var INSTANCE: TasksDatabase? = null
        fun getInstance(context: Context): TasksDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TasksDatabase::class.java,
                        "todo_data_database"
                    ).build()
                }
                return instance
            }
        }
    }
}