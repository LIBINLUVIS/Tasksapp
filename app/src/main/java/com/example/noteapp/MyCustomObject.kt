package com.example.noteapp

import java.io.Serializable

class MyCustomObject : Serializable {
    var taskname = ""
    var id=0
    var status=false


    constructor(tName: String,tid:Int,tstatus:Boolean){
        taskname = tName
        id=tid
        status=tstatus

    }

    constructor()
}