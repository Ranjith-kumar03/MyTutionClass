package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class registerActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    val TAG ="registerActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        mAuth = FirebaseAuth.getInstance()
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        var userName: TextInputEditText
        var password: TextInputEditText
        var confirmpassword: TextInputEditText
         var registerBtn:Button
        var progressBarRegr:ProgressBar
        userName =findViewById(R.id.userName)
        password =findViewById(R.id.password)
        confirmpassword =findViewById(R.id.confirmpassword)
        registerBtn=findViewById(R.id.registerBtn)
        progressBarRegr=findViewById(R.id.progressBarRegr)
        progressBarRegr.visibility = View.INVISIBLE
        registerBtn.setOnClickListener(View.OnClickListener {
           // Toast.makeText(this, userName.editableText, Toast.LENGTH_SHORT).show()
            progressBarRegr.visibility = View.VISIBLE
            if(userName.editableText.trim().length==0)
            {
                userName.setError("Please Enter Username")
                userName.requestFocus()
                progressBarRegr.visibility = View.INVISIBLE
                return@OnClickListener
            }else if(!Patterns.EMAIL_ADDRESS.matcher(userName.editableText.toString()).matches())
            {
                userName.setError("Please Enter a valid Email Address as Username")
                userName.requestFocus()
                progressBarRegr.visibility = View.INVISIBLE
                return@OnClickListener
            }

            else if(password.editableText.trim().toString()!=confirmpassword.editableText.trim().toString())
            {
                confirmpassword.setError("Please Enter the Text Entered in Password again in Confirm Password")
                confirmpassword.requestFocus()
                progressBarRegr.visibility = View.INVISIBLE
                return@OnClickListener
            }else{
                mAuth.createUserWithEmailAndPassword(userName.editableText.toString(), confirmpassword.editableText.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user:FirebaseUser? = mAuth.currentUser
                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d(TAG, "Email sent.")
                                        Log.d(TAG, "createUserWithEmail:success")
                                        var intent= Intent(this@registerActivity,loginactivity::class.java)
                                        startActivity(intent)
                                        progressBarRegr.visibility = View.INVISIBLE
                                        finish()
                                        Toast.makeText(this, "Please Verify the Email for Proceeding Further", Toast.LENGTH_SHORT).show()
                                    }
                                }


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "ASign Up Failed Try after some time.",
                                Toast.LENGTH_SHORT).show()

                        }

                        // ...
                    }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
       // updateUI(currentUser)

    }
}