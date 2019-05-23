package com.example.otpverificationwithfirebase

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_signed_in.*

class SignedInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signed_in)

        val user=FirebaseAuth.getInstance().currentUser
        tv_phone_number.text=user!!.phoneNumber

        btn_sign_out.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@SignedInActivity,MainActivity::class.java))
            finish()
        }
    }
}