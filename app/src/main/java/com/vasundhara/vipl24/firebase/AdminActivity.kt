package com.vasundhara.vipl24.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {



    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)


        firebaseAuth=FirebaseAuth.getInstance()

        btn_admin_login.setOnClickListener {
            if (et_admin_email.text.isNotEmpty()&&et_admin_password.text.isNotEmpty()){

                adminlogin()
            }else{
                Toast.makeText(this, "Please Enter Email And Password", Toast.LENGTH_SHORT).show()
            }

        }
        login_as_user.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        btn_tv_forgot_admin.setOnClickListener {
            Toast.makeText(this, "You Can Not Use this For Admin", Toast.LENGTH_SHORT).show()
            Toast.makeText(this, "Admin has Fixed Password", Toast.LENGTH_SHORT).show()
        }
        et_admin_email.setOnClickListener {

            et_admin_email.setText("Admin@gmail.com").toString()

        }


    }

    private fun adminlogin() {

val progressdialog=ProgressDialog(this)
        progressdialog.setMessage("Loading....Please wait")
        progressdialog.show()

        val adminemail:String="Admin@gmail.com"
        val adminpass:String="admin@123"

        val a1=et_admin_email.text.toString()
        val a2=et_admin_password.text.toString()

        if (a1!=adminemail&&a2!=adminpass){

            Toast.makeText(this, "Please Enter Valid Email or Password", Toast.LENGTH_SHORT).show()
        }else{




        firebaseAuth.signInWithEmailAndPassword(et_admin_email.text.toString(),et_admin_password.text.toString())

            .addOnCompleteListener {

                if (it.isSuccessful){

                    Toast.makeText(this, "Admin Login Success", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this,MainActivity::class.java))
                    finish()

                    if (progressdialog.isShowing)progressdialog.dismiss()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Check Email Or Password", Toast.LENGTH_SHORT).show()

                if (progressdialog.isShowing)progressdialog.dismiss()
            }
    }

    }
}