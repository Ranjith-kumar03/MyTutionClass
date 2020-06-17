package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore


class AddFragment : Fragment() {
    val myDB = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
      val view:View = inflater.inflate(R.layout.fragment_add, container, false)
       val studentName: TextInputEditText = view.findViewById(R.id.studentName)
        
        val studentAge:TextInputEditText = view.findViewById(R.id.studentAge)
        val studentGender:TextInputEditText = view.findViewById(R.id.studentGender)
        val studentDOB:TextInputEditText= view.findViewById(R.id.studentDOB)
        val studentClass:TextInputEditText = view.findViewById(R.id.studentClass)
        val addStudent:Button = view.findViewById(R.id.addStudent)
        val progressBarAddStudent: ProgressBar
        progressBarAddStudent=view.findViewById(R.id.progressBarAddStudent)
        progressBarAddStudent.visibility = View.INVISIBLE
        addStudent.setOnClickListener(View.OnClickListener {
            progressBarAddStudent.visibility = View.VISIBLE
            if (studentName.editableText.trim().length == 0) {
                studentName.setError("Please Enter Username")
                studentName.requestFocus()
                progressBarAddStudent.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else if(studentAge.editableText.trim().length == 0) {
                studentAge.setError("Please Enter Username")
                studentAge.requestFocus()
                progressBarAddStudent.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if(studentGender.editableText.trim().length == 0) {
                studentGender.setError("Please Enter Username")
                studentGender.requestFocus()
                progressBarAddStudent.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if(studentDOB.editableText.trim().length == 0) {
                studentDOB.setError("Please Enter Username")
                studentDOB.requestFocus()
                progressBarAddStudent.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if(studentClass.editableText.trim().length == 0) {
                studentClass.setError("Please Enter Username")
                studentClass.requestFocus()
                progressBarAddStudent.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else{
                val students = myDB.collection("tution_students")
                students.add(mapOf(
                    "studentName" to studentName.editableText.toString(),
                    "studentAge" to studentAge.editableText.toString(),
                    "studentGender" to studentGender.editableText.toString(),
                    "studentDOB" to studentDOB.editableText.toString(),
                    "studentClass" to studentClass.editableText.toString()
                //https://code.tutsplus.com/tutorials/getting-started-with-cloud-firestore-for-android--cms-30382
                )).addOnCompleteListener(OnCompleteListener {
                    progressBarAddStudent.visibility = View.INVISIBLE
                    Toast.makeText(activity, "Student Added to Database", Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.Container, ViewAllFragment())?.commit()
                }).addOnFailureListener(OnFailureListener {
                    Toast.makeText(activity, "Experience Problem Try back later heheheh", Toast.LENGTH_SHORT).show()
                })

            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}