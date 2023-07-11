package com.example.noteapp

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.db.Tasks

class TasksRecyclerViewAdapter( private val clickListener:(Tasks)->Unit

) : RecyclerView.Adapter<TaskViewHolder>() {
    private val taskList = ArrayList<Tasks>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutinflator=LayoutInflater.from(parent.context)
        val listitem=layoutinflator.inflate(R.layout.list_item,parent,false)
        return TaskViewHolder(listitem)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

       holder.bind(taskList[position],clickListener)
    }

    override fun getItemCount(): Int {
       return taskList.size
    }

    fun setList(tasks: List<Tasks>) {
        taskList.clear()
        taskList.addAll(tasks)
    }



}

class TaskViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
    private lateinit var checkbox: CheckBox

   fun bind(tasks: Tasks, clickListener: (Tasks) -> Unit){
       checkbox=view.findViewById(R.id.finishbox)
       checkbox.isChecked=false
       val taskname=view.findViewById<TextView>(R.id.taskstv)
       taskname.text=tasks.task
       checkbox.setOnClickListener {
           checkbox.isChecked=true
           tasks.status=true
           clickListener(tasks)


       }
       view.setOnClickListener {

        tasks.status=false
           clickListener(tasks)

       }
   }

}