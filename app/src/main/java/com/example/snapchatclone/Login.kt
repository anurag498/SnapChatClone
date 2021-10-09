package com.example.snapchatclone

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    var login: Button? = null
    var email: TextView? = null
    var password: TextView? = null
    var SignUpp: TextView? = null
    lateinit var remember: CheckBox
    var isRemembered = false
    lateinit var sharedPreferences: SharedPreferences

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        remember = findViewById(R.id.checkBox)

        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)

        if(isRemembered){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        SignUpp = findViewById(R.id.signup)
        SignUpp?.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }
    }


    fun login(view: android.view.View) {
        if (email?.text.toString().isEmpty()) {
            email?.error = "Please Enter Email"
            email?.requestFocus()
            return
        }
        if (password?.text.toString().isEmpty()) {
            password?.error = "Please Enter password"
            password?.requestFocus()
            return
        }
        mAuth.signInWithEmailAndPassword(email?.text.toString(), password?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val emailNew: String = email?.text.toString()
                    val passwordNew: String = password?.text.toString()
                    val checked:Boolean = remember?.isChecked

                    val editor : SharedPreferences.Editor = sharedPreferences.edit()
                    editor.putString("EMAIL",emailNew)
                    editor.putString("PASSWORD",passwordNew)
                    editor.putBoolean("CHECKBOX",checked)
                    editor.apply()
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser




                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Invalid Credentials.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}