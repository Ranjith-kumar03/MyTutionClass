package com.example.myapplication

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Pair
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
       val image:ImageView  = findViewById(R.id.logo_image)
        val logo:TextView = findViewById(R.id.logo)
       val slogan:TextView = findViewById(R.id.slogan)

        val  topAnim:Animation = AnimationUtils.loadAnimation(this, R.anim.topanimation)
        val bottomAnim:Animation = AnimationUtils.loadAnimation(this, R.anim.bottomanimation)

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);
   Handler().postDelayed( Runnable {
       val  intent = Intent(this@MainActivity,loginactivity::class.java)
       val pairs: Array<Pair<View, String>?> = arrayOfNulls<android.util.Pair<View, String>>(2)
       pairs[0] = android.util.Pair(image, "logo_image")
       pairs[1] = android.util.Pair(logo, "logo_text")
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
           val options = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity, *pairs)
           startActivity(intent, options.toBundle())
           finish()
       }



   },5000)



    }


}