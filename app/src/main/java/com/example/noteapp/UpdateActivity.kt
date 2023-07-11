package com.example.noteapp

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.noteapp.databinding.UpdateactivityBinding
//import android.support.v7.app.AlertDialog
import com.example.noteapp.db.Tasks
import com.example.noteapp.db.TasksDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.appcompat.app.AlertDialog

class UpdateActivity :AppCompatActivity(){
    private lateinit var  viewModel:TaskViewModel
    private lateinit var updatetxtfield:EditText
    private lateinit var binding:UpdateactivityBinding
    private var taskid=0
    private var taskname=""
    private var taskstatus=false



   override fun onCreate(savedInstanceState: Bundle?){
       super.onCreate(savedInstanceState)
       setContentView(R.layout.updateactivity)
       binding=DataBindingUtil.setContentView(this,R.layout.updateactivity)

       updatetxtfield=findViewById(R.id.updatetv)
       val updatebtn=findViewById<FloatingActionButton>(R.id.updatebtn)

       val myIntent = intent
       val derivedObject = myIntent.getSerializableExtra("tasks") as MyCustomObject

       taskid=derivedObject.id
       taskname=derivedObject.taskname
       taskstatus=derivedObject.status

       updatetxtfield.setText(derivedObject.taskname)

       val doa= TasksDatabase.getInstance(application).tasksDao

       val factory=TaskViewModelFactory(doa)

       viewModel= ViewModelProvider(this,factory).get(TaskViewModel::class.java)


       updatebtn.setOnClickListener{
           update(derivedObject.id,derivedObject.status)
       }


       binding.upactbar.backbtn.setOnClickListener {
           val intent=Intent(this,MainActivity::class.java)
           startActivity(intent)
           finish()
       }
       binding.upactbar.delbtn.setOnClickListener {
           showcustomdilogbox()
       }



   }

    private fun showcustomdilogbox(){
        val dialog=Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_box)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val ysbtn : Button = dialog.findViewById(R.id.ysbtn)
        val nobtn : Button = dialog.findViewById(R.id.nobtn)

        ysbtn.setOnClickListener {
            taskdelete()
        }
        nobtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun onBackPressed() {

        val i=Intent(this,MainActivity::class.java)
        startActivity(i)
        finish()
    }

  private  fun taskdelete(){

      viewModel.deleteTask(
               Tasks(
                   taskid,
                   taskname,
                   taskstatus
               )
           )
           Toast.makeText(this,"Task deleted",Toast.LENGTH_SHORT).show()
           val intent=Intent(this,MainActivity::class.java)
           startActivity(intent)
           finish()
    }



    private fun update(id:Int,status:Boolean){
        viewModel.updateTask(
            Tasks(
                id,
                updatetxtfield.text.toString(),
                status
            )
        )
        Toast.makeText(this,"Task Updated", Toast.LENGTH_SHORT).show()
        val intent=Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()



    }

}