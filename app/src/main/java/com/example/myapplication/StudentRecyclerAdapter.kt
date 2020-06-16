package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import kotlinx.android.synthetic.main.recyclertemplate.*
import kotlinx.android.synthetic.main.recyclertemplate.view.*
import com.example.myapplication.onTouchListner as onTouchListner1

class StudentRecyclerAdapter(students:List<Student>, context: onTouchListner1) : Adapter<StudentRecyclerAdapter.ViewHolder>() {
val TAG="StudentRecyclerAdapter"
    private lateinit var students:List<Student>
    lateinit var context:onTouchListner1
    init {
   this.students=students
   this.context =  context

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
   var view:View= LayoutInflater.from(parent.context).inflate(R.layout.recyclertemplate,parent,false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setTag(position)
  val currentItem =students[position]
        holder.nameid.text =currentItem.studentName
        holder. ageid.text =currentItem.studentAge
        holder.genderid.text =currentItem.studentGender
        holder.dobid.text =currentItem.studentDOB
        holder.classid.text =currentItem.studentClass
    }
    override fun getItemCount(): Int {
   return students.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnLongClickListener {
init {
    itemView.setOnLongClickListener(this)
    itemView.setOnClickListener(View.OnClickListener {
        context.onClickListener(it?.getTag() as Int)
        Log.d(TAG, "onShortClick: "+(it?.getTag() as Int))
    })
}

    val imageId: ImageView = itemView.imageId
    val nameid: TextView = itemView.nameid
    val ageid: TextView = itemView.ageid
    val genderid: TextView = itemView.genderid
    val dobid: TextView = itemView.dobid
    val classid: TextView = itemView.classid


        override fun onLongClick(v: View?): Boolean {
            var student=students.get(v?.getTag() as Int)
            Log.d(TAG, "onLongClick: "+ student)
            context.onLongClickListener(student)
            return true


        }
    }
}
interface onTouchListner{

        fun onClickListener(posistion: Int)
        fun onLongClickListener(student: Student)

}