package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.rangeTo
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_view_all.*
import kotlinx.android.synthetic.main.fragment_view_all.view.*
import kotlinx.android.synthetic.main.recyclertemplate.*

class ViewAllFragment : Fragment(),onTouchListner {
    val TAG="ViewAllFragment"

    val myDB = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view:View = inflater.inflate(R.layout.fragment_view_all, container, false)
        val progressBarView: ProgressBar =view.progressBarView
        val recyclerview:RecyclerView  =view.recyclerview
        progressBarView.visibility = View.VISIBLE
        var studentListRef:CollectionReference = myDB.collection("tution_students")
        studentListRef.orderBy("studentName")
            .addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "listen:error", e)
                    Toast.makeText(activity, "Cannot Read", Toast.LENGTH_SHORT).show()

                    return@EventListener
                }
                val studentList = mutableListOf<Student>()
                //
                if (snapshots != null) {

                    for (doc in snapshots) {
                        var student = doc.toObject(Student::class.java)
                        student.id = doc.id
                        studentList.add(student)
                        recyclerview.adapter= StudentRecyclerAdapter(studentList, this )
                        recyclerview.setHasFixedSize(true)
                        recyclerview.layoutManager = LinearLayoutManager(activity)


                    }
                }
                progressBarView.visibility = View.GONE
            })



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)
        /*for(i in 0..100000000000000)
        {
            var sum=0L
            sum +=i
        }*/

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ViewAllFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ViewAllFragment().apply {


            }
    }

    override fun onClickListener(student: Student) {
       // Toast.makeText(activity, "Iam clickled short", Toast.LENGTH_SHORT).show()
        val viewallFragment = ViewAllFragment::class.java.name
        val updateFragment = UpdateFragment()
        val addNotesFragment = AddNotesFragment()
       var bundle:Bundle =Bundle()

        bundle.putSerializable("student",student)
        updateFragment.arguments=bundle
        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(viewallFragment)?.replace(R.id.Container, updateFragment)?.commit()
    }

    override fun onLongClickListener(student: Student) {
        val addNotesFragment = AddNotesFragment()
        var bundle:Bundle =Bundle()

        bundle.putSerializable("student",student)
        addNotesFragment.arguments=bundle
        val viewallFragment = ViewAllFragment::class.java.name
        activity?.supportFragmentManager?.beginTransaction()?.addToBackStack(viewallFragment)?.replace(R.id.Container,addNotesFragment)?.commit()
    }
}


