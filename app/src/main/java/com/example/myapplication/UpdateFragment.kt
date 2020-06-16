package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DateFormat
import java.text.Format
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.Formatter


class UpdateFragment : Fragment() {
    val myDB = FirebaseFirestore.getInstance()
    lateinit var studentId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        var view:View = inflater.inflate(R.layout.fragment_update, container, false)
        var studentName: TextInputEditText = view.findViewById(R.id.studentName)
        var studentAge: TextInputEditText = view.findViewById(R.id.studentAge)
        var studentGender: TextInputEditText = view.findViewById(R.id.studentGender)
        var studentDOB: TextInputEditText = view.findViewById(R.id.studentDOB)
        var studentClass: TextInputEditText = view.findViewById(R.id.studentClass)
        var addStudent: Button = view.findViewById(R.id.addStudent)
        var progressBarUpdate: ProgressBar
        progressBarUpdate=view.findViewById(R.id.progressBarUpdate)
        progressBarUpdate.visibility = View.INVISIBLE
        addStudent.setOnClickListener(View.OnClickListener {
            progressBarUpdate.visibility = View.VISIBLE
            /*if (studentName.editableText.trim().length == 0) {
                studentName.setError("Please Enter Username")
                studentName.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            } else if(studentAge.editableText.trim().length == 0) {
                studentAge.setError("Please Enter Username")
                studentAge.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if(studentGender.editableText.trim().length == 0) {
                studentGender.setError("Please Enter Username")
                studentGender.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if(studentDOB.editableText.trim().length == 0) {
                studentDOB.setError("Please Enter Username")
                studentDOB.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else if(studentClass.editableText.trim().length == 0) {
                studentClass.setError("Please Enter Username")
                studentClass.requestFocus()
                progressBarUpdate.visibility = View.INVISIBLE
                Toast.makeText(activity, "Cant get Value", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }else{*/
                myDB.collection("tution_students")
                    .orderBy("studentName")
                    .get().addOnSuccessListener {
                        it.forEach {
                            println(it.get("studentName"))
                        }
                    }
            //}
        })

        var  getStudent: Button = view.findViewById(R.id.getStudent)
        getStudent.setOnClickListener(View.OnClickListener {

            myDB.collection("tution_students")
                .whereEqualTo("studentName", studentName.editableText.toString())
                .get().addOnSuccessListener {
                    it.forEach {
                        println(
                            "Gravity of ${it.get("studentAge")} is ${it.get("studentGender")} m/s/s"
                        )
                        studentName.setText("${it.get("studentName")}")
                        studentAge.setText("${it.get("studentAge")}")
                        studentGender.setText("${it.get("studentGender")}")
                        studentDOB.setText("${it.get("studentDOB")}")
                        studentId=it.id


                    }
                }

        })

        var AddNotes: Button = view.findViewById(R.id.AddNotes)
        AddNotes.setOnClickListener(View.OnClickListener {
            Toast.makeText(activity, "Id of the selected student is "+studentId, Toast.LENGTH_SHORT).show()
             var calendar:Calendar = Calendar.getInstance()
            var time:String = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.time)
           var notes= myDB.collection("tution_students").document(studentId).collection(studentId+"-sep-"+time.toString())
            notes.add(mapOf(
                "addedNotes" to studentClass.editableText.toString() + "Notes Added Time - "+time.toString()

                //https://code.tutsplus.com/tutorials/getting-started-with-cloud-firestore-for-android--cms-30382
            ))
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
         * @return A new instance of fragment UpdateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateFragment().apply {

            }
    }
}