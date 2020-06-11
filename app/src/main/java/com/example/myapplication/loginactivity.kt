package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class loginactivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginactivity)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    val logo_image: ImageView = findViewById(R.id.logo_image)
        val logo_name:TextView = findViewById(R.id.logo_name)
        val slogan_name:TextView = findViewById(R.id.slogan_name)
        val userName: TextInputLayout = findViewById(R.id.userName)
        val password: TextInputLayout = findViewById(R.id.password)
        val foregtPasswordBtn:Button = findViewById(R.id.foregtPasswordBtn)
        val sumibtBtn:Button = findViewById(R.id.submitBtn)
        val registerBtn:Button = findViewById(R.id.registerBtn)

        sumibtBtn.setOnClickListener(View.OnClickListener {

        val intent = Intent(this@loginactivity, studentactivity::class.java )
            startActivity(intent)

        })

        registerBtn.setOnClickListener(View.OnClickListener {

            val intent = Intent(this@loginactivity, registerActivity::class.java )
            startActivity(intent)

        })


        }
    }
