package com.example.snapchatclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class ChooseUserActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    var chooseUserRecyclerView: RecyclerView? = null
    var emails:ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
//        actionbar
        val actionbar = supportActionBar
        actionbar!!.title = "New Activity"
        actionbar.setDisplayHomeAsUpEnabled(true)

        chooseUserRecyclerView = findViewById(R.id.chooseUserRecyclerView)

      val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        chooseUserRecyclerView?.layoutManager = layoutManager

        val adapter = EmailAdapter(this,Supplier.emailIds)
        chooseUserRecyclerView?.adapter = adapter

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }
}


