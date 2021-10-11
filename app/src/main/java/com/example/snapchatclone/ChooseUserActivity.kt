package com.example.snapchatclone


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ChooseUserActivity : AppCompatActivity() {
    private lateinit var dbref : DatabaseReference
    var chooseUserRecyclerView: RecyclerView? = null
    private lateinit var userArrayList:ArrayList<Emails>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_user)
//        actionbar
        val actionbar = supportActionBar
        actionbar!!.title = "New Activity"
        actionbar.setDisplayHomeAsUpEnabled(true)

       chooseUserRecyclerView = findViewById(R.id.chooseUserRecyclerView)
        chooseUserRecyclerView?.layoutManager = LinearLayoutManager(this)
        chooseUserRecyclerView?.setHasFixedSize(true)

        userArrayList = arrayListOf()
        getUserData()

    }

    private fun getUserData(){
        dbref = FirebaseDatabase.getInstance().getReference("users")
        dbref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    for (userSnapshot in snapshot.children){

                        val user = userSnapshot.getValue(Emails::class.java)
                        userArrayList.add((user!!))
                    }

                    chooseUserRecyclerView?.adapter = EmailAdapter(userArrayList)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()

        return true
    }
}


