package com.example.safarifoods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_login.*

class login : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    var  Email_login:String = ""
    var Password_login:String  = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //create an instance of the firebase authentication class
        FirebaseApp.initializeApp(this)
        firebaseAuth = FirebaseAuth.getInstance()


        signuphere.setOnClickListener{
            startActivity(Intent(this@login,registration::class.java))
        }

        loginButton.setOnClickListener {
            GetUserInput()
        }
    }

    private fun GetUserInput() {
         Email_login = LuserEmail.text.toString()
        Password_login = loginpass.text.toString()
        if (Email_login.isEmpty()){
            Toast.makeText(applicationContext,"enter the registered Email",Toast.LENGTH_LONG).show()
        }
        else if (Password_login.isEmpty()){
            Toast.makeText(applicationContext,"enter password",Toast.LENGTH_LONG).show()
        }
        else{
            LoginUser(Email_login,Password_login)
        }
    }

    private fun LoginUser(Email_Login: String, Password_login: String) {
        firebaseAuth.signInWithEmailAndPassword(Email_Login, Password_login).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    applicationContext,
                    "login verified:" + it.exception,
                    Toast.LENGTH_LONG
                ).show()
                updateUi()

            } else {
                Toast.makeText(
                    applicationContext,
                    "login failure:" + it.exception,
                    Toast.LENGTH_LONG
                ).show()
                Log.d("auth", "details are" + it.exception)
            }

        }
    }

    private fun updateUi() {
        val LoginIntent = Intent(this@login, MainActivity::class.java)
        LoginIntent.flags= Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        firebaseAuth = FirebaseAuth.getInstance()
        val userId = firebaseAuth.currentUser?.uid
        LoginIntent.putExtra("user_id",userId)
        //intent.putExtra("user_email",email)

        startActivity(LoginIntent)

        finish()

    }

    override fun onStart() {
        super.onStart()
        //check if user is logged in
        val currentUser = firebaseAuth.currentUser
        if (currentUser!=null){
            updateUi()
        }
    }
}