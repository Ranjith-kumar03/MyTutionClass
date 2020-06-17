package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.fragment_add_notes.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import java.text.DateFormat
import java.text.Format
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Formatter


class UpdateFragment : Fragment() {
    val TAG ="UpdateFragment"
    val myDB = FirebaseFirestore.getInstance()

    lateinit var  student:Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var studentId:String =""
        student= arguments?.getSerializable("student") as Student
        var view:View = inflater.inflate(R.layout.fragment_update, container, false)
        var studentName: TextInputEditText = view.findViewById(R.id.studentName)
        studentName.setEnabled(false)
        var studentAge: TextInputEditText = view.findViewById(R.id.studentAge)
        var studentGender: TextInputEditText = view.findViewById(R.id.studentGender)
        var studentDOB: TextInputEditText = view.findViewById(R.id.studentDOB)
        var studentClass: TextInputEditText = view.findViewById(R.id.studentClass)
        var updateStudent: Button = view.findViewById(R.id.updateStudent)
        var logo_name: TextView = view.logo_name
        logo_name.setText("Updating Details for ${student.studentName}")
        var progressBarUpdate: ProgressBar
        progressBarUpdate=view.findViewById(R.id.progressBarUpdate)
        progressBarUpdate.visibility = View.INVISIBLE
        myDB.collection("tution_students")
            .whereEqualTo("studentName",student.studentName)
            .get().addOnSuccessListener {
                it.forEach {
                    studentName.setText(student.studentName.toString())
                    studentAge.setText(student.studentAge.toString())
                    studentGender.setText(student.studentGender.toString())
                    studentDOB.setText(student.studentDOB.toString())
                    studentClass.setText(student.studentClass.toString())
                }
            }



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateStudent.setOnClickListener(View.OnClickListener {
            progressBarUpdate.visibility = View.VISIBLE
            if (studentName.editableText.trim().length == 0) {
                studentName.setError("Please Enter Username")
                studentName.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE

                return@OnClickListener
            } else if(studentAge.editableText.trim().length == 0) {
                studentAge.setError("Please Enter Username")
                studentAge.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE

                return@OnClickListener
            }else if(studentGender.editableText.trim().length == 0) {
                studentGender.setError("Please Enter Username")
                studentGender.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE

                return@OnClickListener
            }else if(studentDOB.editableText.trim().length == 0) {
                studentDOB.setError("Please Enter Username")
                studentDOB.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE

                return@OnClickListener
            }else if(studentClass.editableText.trim().length == 0) {
                studentClass.setError("Please Enter Username")
                studentClass.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE

                return@OnClickListener
            }else{
                myDB.collection("tution_students")
                    .whereEqualTo("studentName",student.studentName)
                    .get().addOnSuccessListener {
                        it.forEach {
                          var  studentId:String = it.id
                            student.studentAge = studentAge.text.toString()
                            student.studentGender = studentGender.text.toString()
                            student.studentDOB = studentDOB.text.toString()
                            student.studentClass = studentClass.text.toString()
                            Log.d(TAG, "onCreateView: " +student)
                            myDB.collection("tution_students").document(studentId).set(student, SetOptions.merge())
                            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.Container, ViewAllFragment())?.commit()

                        }
                    }


            }
        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateFragment().apply {

            }
    }
}