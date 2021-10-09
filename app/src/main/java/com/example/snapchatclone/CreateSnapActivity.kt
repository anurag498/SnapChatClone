package com.example.snapchatclone

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button

import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.net.URI
import java.util.*



class CreateSnapActivity : AppCompatActivity() {
     var chooseImage : Button?= null
    var nextClicked : Button?= null
    var createSnapImageView : ImageView? = null
    lateinit var imageUrl: Uri
    val imageName = UUID.randomUUID().toString() + ".jpg"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_snap)

        createSnapImageView = findViewById(R.id.createSnapImageView)
        chooseImage = findViewById(R.id.chooseImage)
        nextClicked = findViewById(R.id.nextButton)
        nextClicked?.setOnClickListener {
            if(imageUrl!=null){
                var pd = ProgressDialog(this)
                pd.setTitle("Uploading")
                pd.show()

                var imageRef = FirebaseStorage.getInstance().reference.child("images").child(imageName)
                imageRef.putFile(imageUrl)
                    .addOnFailureListener(OnFailureListener {
//                Handle unsuccessful uploads
                Toast.makeText(this,"UploadFailed",Toast.LENGTH_SHORT).show()
    })          .addOnSuccessListener(OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                val downloadUrl = taskSnapshot.storage.downloadUrl
                        Log.i("URL", downloadUrl.toString())


                startActivity(Intent(this,ChooseUserActivity::class.java))
    })
            }
        }
    }
//
    @RequiresApi(Build.VERSION_CODES.M)
    fun chooseImageClicked(view: View) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            getPhoto()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto()
            }
        }
    }

fun getPhoto() {
    startForResult.launch(
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
    )
}


    @RequiresApi(Build.VERSION_CODES.P)
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            imageUrl = intent?.data!!
            var bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver,imageUrl)
            createSnapImageView?.setImageBitmap(bitmap)


        }
    }


}