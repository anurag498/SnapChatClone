package com.example.snapchatclone

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    var signUp : Button? = null
    var email : TextView? = null
    var password : TextView? = null
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance();
        signUp = findViewById(R.id.signUp)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        signUp?.setOnClickListener {
            signUp()
        }
    }

    fun signUp(){
        if(email?.text.toString().isEmpty()){
            email?.error = "Please Enter Email"
            email?.requestFocus()
            return
        }
        if(password?.text.toString().isEmpty()){
            password?.error = "Please Enter Password"
            password?.requestFocus()
            return
        }
        mAuth.createUserWithEmailAndPassword(email?.text.toString(), password?.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    task.result?.user?.let {
                        FirebaseDatabase.getInstance().reference.child("users").child(
                            it?.uid).child("email").setValue(email?.text.toString())
                    }
                    // Sign in success, update UI with the signed-in user's information
                        startActivity(Intent(this,Login::class.java))
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = mAuth.currentUser

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "SignUp failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
    }
}