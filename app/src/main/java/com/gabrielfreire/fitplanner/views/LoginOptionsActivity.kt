package com.gabrielfreire.fitplanner.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gabrielfreire.fitplanner.Constants
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.UserType
import com.gabrielfreire.fitplanner.databinding.ActivityLoginOptionsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginOptionsActivity : AppCompatActivity() {

    private lateinit var loginOptionsBinding: ActivityLoginOptionsBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginOptionsBinding = ActivityLoginOptionsBinding.inflate(layoutInflater)
        setContentView(loginOptionsBinding.root)

        initViewsAndSetListeners()
    }

    override fun onStart() {
        super.onStart()
        checkIsUserSignedIn()
    }

    private fun initViewsAndSetListeners() {
        with(loginOptionsBinding) {
            firebaseAuth = Firebase.auth

            btnStudentLoginOption.setOnClickListener {
                goToLogin(UserType.STUDENT.type)
            }

            btnPersonalTrainerLoginOption.setOnClickListener {
                goToLogin(UserType.PERSONAL_TRAINER.type)
            }

            btnLoginOptionTermsOfUse.setOnClickListener {
                goToTermsOfUse()
            }
        }
    }

    private fun checkIsUserSignedIn() {
        if (firebaseAuth.currentUser == null) {
            userAnonymousSignIn()
        }
    }

    private fun goToLogin(userType: String) {
        val intent = Intent(this@LoginOptionsActivity, LoginActivity::class.java)
        intent.putExtra(Constants.USER_TYPE, userType)
        startActivity(intent)
    }

    private fun goToTermsOfUse() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.TERMS_OF_USE_URL))
        startActivity(browserIntent)
    }

    private fun userAnonymousSignIn() {
        firebaseAuth.signInAnonymously().addOnCompleteListener(this@LoginOptionsActivity) { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser

                Log.d("FirebaseAuth", "signInAnonymously:success $user")
            } else {
                Toast.makeText(
                    this@LoginOptionsActivity,
                    getString(R.string.authentication_failed_message),
                    Toast.LENGTH_SHORT
                ).show()

                Log.w("FirebaseAuth", "signInAnonymously:failure", task.exception)
            }
        }
    }
}