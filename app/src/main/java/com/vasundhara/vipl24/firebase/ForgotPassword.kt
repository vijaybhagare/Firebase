package com.vasundhara.vipl24.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPassword : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        firebaseAuth=FirebaseAuth.getInstance()

        btn_forgot.setOnClickListener {

            if (et_forgot_email.text.isNotEmpty()){
                forgotpassword()
            }else{

                Toast.makeText(this, "Enter Your Email", Toast.LENGTH_SHORT).show()
            }

        }

        back_to_login.setOnClickListener {

            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }


    }

    private fun forgotpassword() {

        val progressdialog=ProgressDialog(this)
        progressdialog.setMessage("Sending Link to Email...")
        progressdialog.setCancelable(true)
        progressdialog.show()




        firebaseAuth.sendPasswordResetEmail(et_forgot_email.text.toString())

            .addOnCompleteListener {

                if (it.isSuccessful){


                    Toast.makeText(this, "Link send to your Email", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "please check your Email..!", Toast.LENGTH_SHORT).show()
                    if (progressdialog.isShowing)progressdialog.dismiss()
                }
            }
    }
}