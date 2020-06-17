package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_add_notes.view.*
import kotlinx.android.synthetic.main.fragment_add_notes.view.logo_name
import kotlinx.android.synthetic.main.fragment_view_notes.view.*


class ViewNotes : Fragment() {
    val myDB = FirebaseFirestore.getInstance()
    lateinit var  student:Student
    var TAG ="ViewNotes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        student= arguments?.getSerializable("student") as Student
        // Inflate the layout for this fragment
        var view:View = inflater.inflate(R.layout.fragment_view_notes, container, false)
        var logo_name:TextView = view.logo_name
        logo_name.setText("Notes of ${student.studentName}")
        var progressBarNotesView: ProgressBar = view.progressBarNotesView
        progressBarNotesView.visibility = View.VISIBLE
        var viewNotes:TextView = view.viewNotes
        var notes:String=""
        notes+="${student.studentNotes} \n"
        viewNotes.setText(notes)
        progressBarNotesView.visibility = View.GONE
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewNotes().apply {

            }
    }
}