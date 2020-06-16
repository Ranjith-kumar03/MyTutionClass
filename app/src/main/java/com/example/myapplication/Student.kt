package com.example.myapplication

data class Student(var id:String, var studentName:String, var studentAge:String, var studentGender:String, var studentDOB:String, var studentClass:String)
{
    constructor():this("","","","","","")
    {

    }
}
