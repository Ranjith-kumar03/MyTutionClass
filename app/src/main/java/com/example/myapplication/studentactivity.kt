package com.example.myapplication

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView

class studentactivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_studentactivity)
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        var toolbar: Toolbar

        var actionBarDrawerToggle: ActionBarDrawerToggle
        toolbar= findViewById(R.id.tool_bar)
        setSupportActionBar(toolbar);
        var navigationView: NavigationView


        drawerLayout=findViewById(R.id.drawer_layout);

        navigationView= findViewById<View>(R.id.navigationView) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.OpenDrawer,R.string.CloseDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        var manager: FragmentManager
        var transaction: FragmentTransaction
        manager=this.getSupportFragmentManager();
        transaction=manager.beginTransaction();
        transaction.replace(R.id.Container, ViewAllFragment()).commit();

        }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START);
        when (item.itemId) {
            R.id.addStudent -> {
                var manager: FragmentManager
                var transaction: FragmentTransaction
                manager=this.getSupportFragmentManager();
                transaction=manager.beginTransaction();
                transaction.replace(R.id.Container, AddFragment()).commit();

        }

            R.id.viewAllStudents -> {
                var manager: FragmentManager
                var transaction: FragmentTransaction
                manager=this.getSupportFragmentManager();
                transaction=manager.beginTransaction();
                transaction.replace(R.id.Container, ViewAllFragment()).commit();

            }
            else ->{

            }
        }
        //drawerLayout.closeDrawers()
        return true
    }


}

