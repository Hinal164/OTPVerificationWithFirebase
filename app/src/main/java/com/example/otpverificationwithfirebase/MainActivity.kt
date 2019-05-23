package com.example.otpverificationwithfirebase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import android.content.Intent



class MainActivity : AppCompatActivity() {

    private val phoneNum = "+1 650-555-3434"
    private var testVerificationCode :String = "654321"
    private lateinit var auth: FirebaseAuth
    private lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFirebaseLogin()
        auth = FirebaseAuth.getInstance()
        val firebaseAuthSettings = auth.firebaseAuthSettings


        btn_generate_otp.setOnClickListener {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 30L, TimeUnit.SECONDS, this,mCallbacks)
        }

        btn_sign_in.setOnClickListener {
            val credential = PhoneAuthProvider.getCredential(testVerificationCode,testVerificationCode)
            auth.signInWithCredential(credential).addOnCompleteListener { task->
                if(task.isSuccessful){
                    startActivity(Intent(this@MainActivity, SignedInActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@MainActivity, "Incorrect OTP", Toast.LENGTH_SHORT).show()

                }

            }
        }
    }

    private fun startFirebaseLogin() {
        mCallbacks=object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(verificationId, token)
               // testVerificationCode=verificationId!!
                Log.d("TAG","Verification onCodeSent: $verificationId")

            }

            override fun onCodeAutoRetrievalTimeOut(p0: String?) {
                super.onCodeAutoRetrievalTimeOut(p0)
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential?) {
                Log.d("TAG","Verification Completed : ${phoneAuthCredential!!.smsCode}")
                //et_otp.setText(phoneAuthCredential.smsCode)
            }

            override fun onVerificationFailed(e: FirebaseException?) {
                Log.d("TAG","Verification failed ${e!!.message}")

            }
        }
    }
}

