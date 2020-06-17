package com.example.myapplication

import java.io.Serializable

data class Student(var id:String, var studentName:String, var studentAge:String, var studentGender:String, var studentDOB:String, var studentClass:String, var studentNotes:String):Serializable
{
    constructor():this("","","","","","","")
    {

    }
}
