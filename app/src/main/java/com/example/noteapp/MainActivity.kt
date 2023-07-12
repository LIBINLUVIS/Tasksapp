package com.example.noteapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

import com.example.noteapp.db.Tasks
import com.example.noteapp.db.TasksDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var taskview:EditText
    private lateinit var addbutton:ImageButton
    private lateinit var viewModel:TaskViewModel
    private lateinit var selectedtasks: Tasks
    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var adapter: TasksRecyclerViewAdapter
    private lateinit var actionbar:ActionBar
    private lateinit var restimg:ImageView
    private lateinit var nowork:TextView
    private lateinit var colorDrawable: ColorDrawable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskview=findViewById(R.id.tasktv1)
        addbutton=findViewById(R.id.addbtn)
        taskRecyclerView = findViewById(R.id.rvtasks)
        restimg=findViewById(R.id.restimg)
        nowork=findViewById(R.id.nowork)

        colorDrawable= ColorDrawable(Color.parseColor("#2196F3"))

        actionbar= supportActionBar!!
        actionbar.setBackgroundDrawable(colorDrawable)
        val doa=TasksDatabase.getInstance(application).tasksDao

        val factory=TaskViewModelFactory(doa)

        viewModel= ViewModelProvider(this,factory).get(TaskViewModel::class.java)

        addbutton.setOnClickListener{
            addtask()
            clearinput()
        }

        taskview.addTextChangedListener {
            var txtlen=taskview.text.length
            if(txtlen>=1){
                addbutton.isVisible=true
            }else{
                addbutton.isVisible=false
            }

        }


        initRecyclerView()
    }


    private fun initRecyclerView(){
        taskRecyclerView.layoutManager=LinearLayoutManager(this)
        adapter= TasksRecyclerViewAdapter { selectedItem: Tasks -> listItemChecked(selectedItem) }

        taskRecyclerView.adapter = adapter

        displayTasksList()

    }

    private fun displayTasksList(){

        viewModel.tasks.observe(this,{
           adapter.setList(it)
            adapter.notifyDataSetChanged()
            val length= viewModel.tasks.value?.size
            if (length != null) {
                if(length >=1){
                    restimg.isVisible=false
                    nowork.isVisible=false

                }
            }

        })
    }

    private fun listItemChecked(task: Tasks){

        selectedtasks=task
        if(task.status){
            viewModel.deleteTask(
                Tasks(
                    selectedtasks.id,
                    selectedtasks.task,
                    selectedtasks.status
                )
            )
            viewModel.tasks.observe(this,{
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                val length= viewModel.tasks.value?.size
                if (length != null) {
                    if(length >=1){
                        restimg.isVisible=false
                        nowork.isVisible=false

                    }else{
                        restimg.isVisible=true
                        nowork.isVisible=true
                    }
                }

            })
            Toast.makeText(this,"Task Finished",Toast.LENGTH_SHORT).show()
        }else{
            val intent=Intent(this,UpdateActivity::class.java)
            val passingObject = MyCustomObject()
            passingObject.taskname = selectedtasks.task
            passingObject.id=selectedtasks.id
            passingObject.status=selectedtasks.status
            intent.putExtra("tasks",passingObject)
            startActivity(intent)
            finish()

        }
    }


    private fun addtask(){
        viewModel.insertTask(
            Tasks(
              0,
              taskview.text.toString(),
                false
           )
        )
        Toast.makeText(this,"Task added",Toast.LENGTH_SHORT).show()
    }

    private fun clearinput(){
        taskview.setText("")
    }
}