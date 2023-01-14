package com.vasundhara.vipl24.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class GallaryActivity : AppCompatActivity() {

    var firebaseStorage:FirebaseStorage?=null
    var firebaseDatabase:FirebaseDatabase?=null
    var storageRef:StorageReference?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallary)


        firebaseStorage=FirebaseStorage.getInstance()
        storageRef= firebaseStorage!!.getReference("MyPhotos")
        val reference=firebaseDatabase?.getReference("Employee")
    }
}