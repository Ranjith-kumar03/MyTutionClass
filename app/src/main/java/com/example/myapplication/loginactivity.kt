package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.set
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class loginactivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    val TAG ="loginActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)
        mAuth = FirebaseAuth.getInstance()
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    var logo_image: ImageView = findViewById(R.id.logo_image)
        var logo_name:TextView = findViewById(R.id.logo_name)
        var slogan_name:TextView = findViewById(R.id.slogan_name)
        var userName: TextInputEditText = findViewById(R.id.userName)

        var password: TextInputEditText= findViewById(R.id.password)

        var foregtPasswordBtn:Button = findViewById(R.id.foregtPasswordBtn)
        var sumibtBtn:Button = findViewById(R.id.submitBtn)
        var registerBtn:Button = findViewById(R.id.registerBtn)
        var progressBarlogin: ProgressBar
        progressBarlogin=findViewById(R.id.progressBarlogin)
        progressBarlogin.visibility = View.INVISIBLE
        sumibtBtn.setOnClickListener(View.OnClickListener {
            progressBarlogin.visibility = View.VISIBLE
            if (userName.editableText.trim().length == 0) {
                userName.setError("Please Enter Username")
                userName.requestFocus()
                progressBarlogin.visibility = View.INVISIBLE
               return@OnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userName.editableText.toString()).matches()) {
                userName.setError("Please Enter a valid Email Address as Username")
                userName.requestFocus()
                progressBarlogin.visibility = View.INVISIBLE
                return@OnClickListener
            } else if (password.editableText.trim().toString().isEmpty())
            {
                password.setError("Please Enter the Password")
                password.requestFocus()
                progressBarlogin.visibility = View.INVISIBLE
                return@OnClickListener
            } else {

                mAuth.signInWithEmailAndPassword(userName.editableText.toString(),password.editableText.toString())  //
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = mAuth.currentUser
                            if(user!=null) {
                                if(user.isEmailVerified) {
                                    //Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show()
                                    val intent =
                                        Intent(this@loginactivity, studentactivity::class.java)
                                    progressBarlogin.visibility = View.INVISIBLE
                                    startActivity(intent)
                                    finish()
                                }else
                                {

                                    showalertDialog()
                                   Toast.makeText( this, "Please Verify the Email then we shall proceed", Toast.LENGTH_SHORT).show()
                                    progressBarlogin.visibility = View.INVISIBLE
                                }
                            }
                           // updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this, "Authentication failed.",Toast.LENGTH_SHORT).show()
                           // updateUI(null)
                            // ...
                        }

                        // ...
                    }

/////////////////////////////////////////////////////////////////////////////////////////////////////


            }
        })

        registerBtn.setOnClickListener(View.OnClickListener {

            val intent = Intent(this@loginactivity, registerActivity::class.java )
            startActivity(intent)

        })


        foregtPasswordBtn.setOnClickListener(View.OnClickListener {

            passwordResetDialog()

        })



    }
    private fun  showalertDialog()
    {
        var alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(this)

        alertDialogBuilder.setTitle("Please Verify Your Email")
        //set message for alert dialog
        alertDialogBuilder.setMessage("For proceeding Further please verify your email")
        alertDialogBuilder.setIcon(R.drawable.verifyemail)

        //performing positive action
        alertDialogBuilder.setPositiveButton("Yes"){dialogInterface, which ->
           sendVerificationMail()
        }
       /* //performing cancel action
        alertDialogBuilder.setNeutralButton("Cancel"){dialogInterface , which ->
            Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
        }*/
        //performing negative action
        alertDialogBuilder.setNegativeButton("No"){dialogInterface, which ->
            Toast.makeText(applicationContext,"User Cancelled Verification",Toast.LENGTH_LONG).show()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun sendVerificationMail() {
        val user: FirebaseUser? = mAuth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    Toast.makeText(this, "Verification Email Sent", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun  passwordResetDialog()
    {
        var alertDialogBuilder: AlertDialog.Builder =
            AlertDialog.Builder(this)
    var inflater:LayoutInflater = LayoutInflater.from(this)
      var  dialogView:View =  inflater.inflate(R.layout.activity_re_authenticate, null)
        alertDialogBuilder.setView(dialogView)
        var userName: TextInputEditText =dialogView.findViewById(R.id.userName)
        var submitBtn:Button =dialogView.findViewById(R.id.submitBtn)
        alertDialogBuilder.setTitle("Reset Your Password Here")
        //set message for alert dialog
        alertDialogBuilder.setMessage("For proceeding Further please enter Username which is an Email address")
        alertDialogBuilder.setIcon(R.drawable.verifyemail)
                      // Create the AlertDialog
        val alertDialog: AlertDialog = alertDialogBuilder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
        submitBtn.setOnClickListener(View.OnClickListener {
            mAuth.sendPasswordResetEmail(userName.text.toString().trim()).addOnCompleteListener(
                OnCompleteListener {
                    Toast.makeText(this, "Password Reset Mail is send", Toast.LENGTH_SHORT).show()
                    alertDialog.dismiss()
                }
            )
        })
    }


}
