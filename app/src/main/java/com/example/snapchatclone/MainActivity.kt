package com.example.snapchatclone

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var preferences: SharedPreferences
    var email1 : TextView? = null;
    var password1 : TextView? = null;

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        email1 = findViewById(R.id.email1)
        password1 = findViewById(R.id.password1)
        preferences  = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        val emailID = preferences.getString("EMAIL","")
        email1?.text = emailID
        val passwordID = preferences.getString("PASSWORD","")
        password1?.text = passwordID

        mAuth = FirebaseAuth.getInstance();
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.snaps,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.createSnap){

            startActivity(Intent(this, CreateSnapActivity::class.java))
        }else if(item?.itemId == R.id.logout){

            mAuth?.signOut()
            startActivity(Intent(this,Login::class.java))
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.currentUser
        if(currentUser != null){

        }else{
            login()
            finish()
        }
    }
    fun login(){
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
}



}