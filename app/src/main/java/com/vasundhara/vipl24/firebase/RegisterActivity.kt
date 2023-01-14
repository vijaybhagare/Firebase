package com.vasundhara.vipl24.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    
    lateinit var firebaseAuth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        
        
        firebaseAuth=FirebaseAuth.getInstance()
        
        btn_login_new.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))

            finish()
        }
        
        btn_register_new.setOnClickListener {
            if (et_email_register.text.isNotEmpty()&&et_password_register.text.isNotEmpty()&&et_confirm_password_register.text.isNotEmpty()){

                creatEmpLogin()
            }
            else{
                Toast.makeText(this, "Please Enter Email And Password...", Toast.LENGTH_SHORT).show()
            }
            }
    }

    private fun creatEmpLogin() {

        val progrssDialog=ProgressDialog(this)
        progrssDialog.setMessage("Please wait....")
        progrssDialog.setCancelable(false)
        progrssDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(et_email_register.text.toString(),et_password_register.text.toString()).addOnCompleteListener {
            
            if (it.isSuccessful){
                Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()

                et_confirm_password_register.setText("")
                et_email_register.setText("")
                et_confirm_password_register.setText("")
                if (progrssDialog.isShowing)progrssDialog.dismiss()
                startActivity(Intent(this,LoginActivity::class.java))


            }

            }.addOnFailureListener {
                Exception ->
            Toast.makeText(this, "Failed to register"+Exception.message, Toast.LENGTH_SHORT).show()

            if (progrssDialog.isShowing)progrssDialog.dismiss()
        }

    }
}