package com.vasundhara.vipl24.firebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient:GoogleSignInClient
    val Req_Code: Int = 123
    lateinit var firebaseAuth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        firebaseAuth=FirebaseAuth.getInstance()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        btn_google_login.setOnClickListener { view: View? ->
            Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
            signInGoogle()
        }

        btn_tv_forgot.setOnClickListener {
            startActivity(Intent(this,ForgotPassword::class.java))
            finish()
        }

        
        btn_login.setOnClickListener {

         if (et_email.text.isBlank() || et_password.text.isBlank()){
             Toast.makeText(this, "All Fields Are Required... ", Toast.LENGTH_SHORT).show()

            }else{

             LoginEmployee()
             et_email.setText("")
             et_password.setText("")

            }
           

      }
        
        btn_register.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))

            finish()
        }

        login_as_admin.setOnClickListener {
            startActivity(Intent(this,AdminActivity::class.java))
            finish()
        }

    }

    fun LoginEmployee() {

        val progressdialog=ProgressDialog(this)
        progressdialog.setMessage("Logging in Pleas Wait..")
        progressdialog.setCancelable(false)
       progressdialog.show()
        
        val email:String=et_email.text.toString()
        val pass:String=et_password.text.toString()
        
        
        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {
            
            if (it.isSuccessful){

                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()

                if (progressdialog.isShowing)progressdialog.dismiss()
                startActivity(Intent(this,Select_Your_Card::class.java) )
                finish()
            }else{
                Toast.makeText(this, "login Failed", Toast.LENGTH_SHORT).show()
                if (progressdialog.isShowing)progressdialog.dismiss()
            }
        }

    }



fun signInGoogle(){

    val signInIntent: Intent = mGoogleSignInClient.signInIntent
    startActivityForResult(signInIntent, Req_Code)
}
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Req_Code) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                SavedPreference.setEmail(this, account.email.toString())
//                SavedPreference.setUsername(this, account.displayName.toString())
                val intent = Intent(this, Select_Your_Card::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            startActivity(
                Intent(
                    this, MainActivity
                    ::class.java
                )
            )
            finish()
        }
    }
}
//}