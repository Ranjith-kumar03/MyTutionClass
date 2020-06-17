package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_add_notes.*
import kotlinx.android.synthetic.main.fragment_add_notes.view.*
import kotlinx.android.synthetic.main.fragment_add_notes.view.addNotesLayout
import kotlinx.android.synthetic.main.fragment_update.*
import java.text.DateFormat
import java.text.Format
import java.util.*


class AddNotesFragment : Fragment() {
    val myDB = FirebaseFirestore.getInstance()
    lateinit var  student:Student
   var TAG ="AddNotesFragment"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        student= arguments?.getSerializable("student") as Student
       var view:View = inflater.inflate(R.layout.fragment_add_notes, container, false)
        var logo_name: TextView = view.logo_name
        var progressBarNotes: ProgressBar = view.progressBarNotes
        progressBarNotes.visibility = View.INVISIBLE
        var studentNotes: TextInputEditText = view.studentNotes
        logo_name.setText("Adding Notes for ${student.studentName}")
        logo_name.setOnClickListener(View.OnClickListener {
            val viewallFragment = ViewAllFragment::class.java.name
            val viewNotes = ViewNotes()

            var bundle:Bundle =Bundle()

            bundle.putSerializable("student",student)
            viewNotes.arguments=bundle
            activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(viewallFragment)?.replace(R.id.Container, viewNotes)?.commit()
        })
        var addNotes: Button = view.addNotes

        addNotes.setOnClickListener(View.OnClickListener {
            progressBarNotes.visibility = View.VISIBLE
            myDB.collection("tution_students")
                .whereEqualTo("studentName",student.studentName)
                .get().addOnSuccessListener {
                    it.forEach {
                        var  studentId:String = it.id
                       var calendar:Calendar = Calendar.getInstance()
                        var time:String =DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
                        var notes:String=""
                        notes="${student.studentNotes} \n"
                        student.studentNotes = notes + " \n Date and Time : ${time} \n" + "Notes : ${studentNotes.text.toString()} \n"
                        Log.d(TAG, "onCreateView: " +student)
                        myDB.collection("tution_students").document(studentId).set(student, SetOptions.merge())
                        progressBarNotes.visibility = View.GONE
                        val viewallFragment = ViewAllFragment::class.java.name
                        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(viewallFragment)?.replace(R.id.Container, ViewAllFragment())?.commit()

                    }
                }
        })
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddNotesFragment().apply {

            }
    }


}