package com.example.safarifoods

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registration.*

class registration : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

     var user_name:String = ""
    var user_Email: String = ""
    var user_phone:String = ""
    var user_password:String=""
    var user_conPassword:String = ""


    private var imageuri:Uri? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        // getting instance of firebase authentication
        firebaseAuth = FirebaseAuth.getInstance()

        loginhere.setOnClickListener {
            startActivity(Intent(this@registration,login::class.java))
        }

        profilepic.setOnClickListener {
            pickImage()
        }
        //setting the onclick listener for reg button.
        register.setOnClickListener {
//            StoreToDB()
            getUserData()

        }
    }


    private fun getUserData() {
        user_name = username.text.toString()
        user_Email= email.text.toString()
         user_phone = phone.text.toString()
        user_password= password.text.toString()
        user_conPassword= conpassword.text.toString()

        // validate the users input
        if (user_name.isEmpty()){
            Toast.makeText(applicationContext,"user name cannot be empty!!", Toast.LENGTH_LONG).show()
        }
        else if (user_Email.isEmpty()){
            Toast.makeText(applicationContext,"email cannot be empty!!", Toast.LENGTH_LONG).show()
        }
        else if (user_phone.isEmpty()){
            Toast.makeText(applicationContext,"phone  cannot be empty!!", Toast.LENGTH_LONG).show()
        }
        else if (user_password.isEmpty()){
            Toast.makeText(applicationContext,"password cannot be empty!!", Toast.LENGTH_LONG).show()
        }
        else if (user_conPassword.isEmpty()){
            Toast.makeText(applicationContext,"confirm password first", Toast.LENGTH_LONG).show()
        }
        else if (user_password.length<8){
            Toast.makeText(applicationContext,"provide a strong password of more than 8 characters", Toast.LENGTH_LONG).show()
        }
        else if (!user_password.equals(user_conPassword)){
            Toast.makeText(applicationContext,"passwords do not match", Toast.LENGTH_LONG).show()
        }
        else{
            registerToFirebase(user_Email,user_password)
        }



    }

    private fun registerToFirebase( email: String,  password: String) {
     firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
         if (it.isSuccessful){
             Toast.makeText(applicationContext,"Account created successfully", Toast.LENGTH_LONG).show()

             // log to check we can get the users credentials.
             Log.d("firebaseAuth","details are" + it.result)
             updateUi()
         }
         else
         {
             Toast.makeText(applicationContext,"error,Account Not created" +"/n" +"try again", Toast.LENGTH_LONG).show()
             Log.d("firebaseAuth","details are" +it.exception)
         }
     }
    }

//    private fun StoreToDB() {
//        //getting the database reference so as to getthe root node
//        dbReference = FirebaseDatabase.getInstance().getReference("passengers")
//
//
//
//        val passengers = passenger(
//            username = String(),
//            email = String(),
//            phone = String(),
//            profilepic = String()
//        )
//        dbReference.child(user_name!!.toString()).setValue(passengers).addOnSuccessListener {
//          username.text.clear()
//            email.text.clear()
//            phone.text.clear()
//            password.text.clear()
//            conpassword.text.clear()
//            Toast.makeText(applicationContext,"data captured successfully",Toast.LENGTH_LONG).show()
//
//        }.addOnFailureListener {
//
//            Log.d("writing to database","details" + it.suppressedExceptions)
//            Toast.makeText(applicationContext,"error" + "/n" +"please try again",Toast.LENGTH_LONG).show()
//        }
//    }

    private fun updateUi() {
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        // the authenntication part to login after creating the account.
//        firebaseAuth = FirebaseAuth.getInstance()
//        val userId = firebaseAuth.currentUser?.uid
//        intent.putExtra("user_id",userId)
//        //intent.putExtra("user_email",email)

        startActivity(intent)
        finish()
    }

    //function to pick the profile picture.
    private fun pickImage() {
        //using image picker library from github
        ImagePicker.with(this)
            .crop()
            .maxResultSize(300,300)
            .compress(1024)
            .start()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //picking path  of image seleceted and saving it to the database
            imageuri = data?.data!!

            //set the image selected to the imageview
            profilepic.setImageURI(imageuri)


        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this,ImagePicker.getError(data), Toast.LENGTH_LONG).show()

        } else {
            Toast.makeText(this,"process cancelled",Toast.LENGTH_LONG).show()
        }
    }
}