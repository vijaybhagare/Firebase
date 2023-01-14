package com.vasundhara.vipl24.firebase

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.Formatter
import java.util.logging.SimpleFormatter

class MainActivity : AppCompatActivity(){

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
   private var imageLink:String?=null
lateinit var bitmap:Bitmap
    private var firebaseStore: FirebaseStorage? = null
    private var storageReference: StorageReference? = null

    private var  selectedPhotoUri:Uri? = null

    private var mFirebaseDatabaseInstances: FirebaseDatabase?=null
    private var mFirebaseDatabase: DatabaseReference?=null

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+"
    private val bloodpattern = " A[+-], B[+-], AB[+-] , O[+-]"
    private val phonepattern= "[0-9]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseDatabaseInstances= FirebaseDatabase.getInstance()




        btn_save.setOnClickListener {


                validation()

//            if (validation()==true){
//                validation()
//            }else{
//
//                Toast.makeText(this, "Invalid Information", Toast.LENGTH_SHORT).show()
//            }
          



//
//if (et_enter_Empname.text.isNotEmpty()&&et_enter_EmpMobile.text.isNotEmpty()&&et_enter_EmpAddress.text.isNotEmpty()){
//    InsertValue(et_enter_Empname.text.toString(),et_enter_EmpMobile.text.toString(),et_enter_EmpAddress.text.toString(),
//    et_enter_Department.text.toString(),et_enter_Email.text.toString(),et_enter_Bloodroup.text.toString())
//
//
//
//}else{
//    Toast.makeText(this, "All Fields Are Required...", Toast.LENGTH_SHORT).show()
//
//}
       }

        img_preview.setOnClickListener { launchGallery()
            }
      btn_View_info.setOnClickListener {
          startActivity(Intent(this,Select_Your_Card::class.java)) }

      //  btn_upload_image.setOnClickListener { uploadImage() }

    }





    @SuppressLint("SuspiciousIndentation")
    private fun uploadImage() {
        val progressDialog=ProgressDialog(this)
        progressDialog.setMessage("Uploading File...")
        progressDialog.setCancelable(true)
        progressDialog.show()

        val formater=SimpleDateFormat("yyyy_MM_dd_HH_mm_ss",Locale.getDefault())
        val now=Date()
        val fileName=formater.format(now)
        val storage=FirebaseStorage.getInstance().getReference("image/$fileName")

            storage.putFile(selectedPhotoUri!!)
                .addOnSuccessListener { it->
                    img_preview.setImageURI(null)

                    val result = it.metadata!!.reference!!.downloadUrl;
                    result.addOnSuccessListener {

                        imageLink = it.toString()
                    }
                    Toast.makeText(this, "Successfuly Uploaded", Toast.LENGTH_SHORT).show()

                    
                    if (progressDialog.isShowing)progressDialog.dismiss()
                }.addOnFailureListener{

                    if (progressDialog.isShowing)progressDialog.dismiss()
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
        }




    private fun launchGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == 100){
            //bitmap.setImageURI(data?.data) // handle chosen image
            selectedPhotoUri=data?.data!!

            bitmap=MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)
        }
//            selectedPhotoUri = data?.data!!
//
//
//              bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            img_preview.setImageBitmap(bitmap)

        uploadImage()
//      val bitmapDrawable = BitmapDrawable(bitmap)
//      selectphoto_button_register.setBackgroundDrawable(bitmapDrawable)
        }
fun validation() {

    val email: String = et_enter_Email.text.toString().trim()
    val phone: String = et_enter_EmpMobile.text.toString()
    val blood: String = et_enter_Bloodroup.text.toString()
    val name: String = et_enter_Empname.text.toString()
    val department: String = et_enter_Department.text.toString()
    val address: String = et_enter_EmpAddress.text.toString()


    if (et_enter_Empname.text.isNotEmpty() && et_enter_EmpMobile.text.isNotEmpty() && et_enter_EmpAddress.text.isNotEmpty() && et_enter_Department.text.isNotEmpty() && et_enter_Bloodroup.text.isNotEmpty() && et_enter_Email.text.isNotEmpty()) {


        val n1:String="1,2,3,4,5,6,7,8,9,0"
        if (name!=n1){

            if (email.matches(emailPattern.toRegex())){

                if (department!=n1){


                        if (phone.length==10){

                            if (address.length<50){

                                if (blood.length!=2||blood.length!=3){






                                mFirebaseDatabase=mFirebaseDatabaseInstances!!.getReference("Employee")

        val data=Employee(empName = name, empMobile = phone, empAddress = address, empdepartment = department, empEmail = email, empBloodGroup = blood,imageLink)
       mFirebaseDatabase?.push()?.setValue(data)

        if (mFirebaseDatabase!=null){

            Toast.makeText(this, "Record Inserted", Toast.LENGTH_SHORT).show()
        }else{

            Toast.makeText(this, "Something was wrong... ", Toast.LENGTH_SHORT).show()
        }

        et_enter_Bloodroup.setText("")
        et_enter_Department.setText("")
        et_enter_Email.setText("")
        et_enter_Department.setText("")
        et_enter_Empname.setText("")
        et_enter_EmpMobile.setText("")
        et_enter_EmpAddress.setText("")
        img_preview.setImageResource(R.drawable.profile)

                                }else{

                                    Toast.makeText(this, "Blood type is A+ A- B+ B- O+ O- AB+ AB- ", Toast.LENGTH_SHORT).show()
                                }


                            }else{

                                Toast.makeText(this, "Address reach Max Size ", Toast.LENGTH_SHORT).show()
                            }


                        }else{

                            Toast.makeText(this, "Mobile Should be 10 Digits ", Toast.LENGTH_SHORT).show()
                        }



                }else{

                    Toast.makeText(this, "Invalid Department type", Toast.LENGTH_SHORT).show()

                }

            }else{

                Toast.makeText(this, "Please Enter Valid Email", Toast.LENGTH_SHORT).show()
            }

        }else{
            Toast.makeText(this, "Invalid Name type", Toast.LENGTH_SHORT).show()
        }


    }else{

        Toast.makeText(this, "All Fields Are required", Toast.LENGTH_SHORT).show()

    }

//    fun InsertValue(name:String,mobile:String,address:String,department:String,email:String,bloodgroup:String){


//        mFirebaseDatabase=mFirebaseDatabaseInstances!!.getReference("Employee")
//
//        val data=Employee(empName = name, empMobile = mobile, empAddress = address, empdepartment = department, empEmail = email, empBloodGroup = bloodgroup,imageLink)
//       mFirebaseDatabase?.push()?.setValue(data)
//
//        if (mFirebaseDatabase!=null){
//
//            Toast.makeText(this, "Record Inserted", Toast.LENGTH_SHORT).show()
//        }else{
//
//            Toast.makeText(this, "Something was wrong... ", Toast.LENGTH_SHORT).show()
//        }
//
//        et_enter_Bloodroup.setText("")
//        et_enter_Department.setText("")
//        et_enter_Email.setText("")
//        et_enter_Department.setText("")
//        et_enter_Empname.setText("")
//        et_enter_EmpMobile.setText("")
//        et_enter_EmpAddress.setText("")

//    }


}
}

