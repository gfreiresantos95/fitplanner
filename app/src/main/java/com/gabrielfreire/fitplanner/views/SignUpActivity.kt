package com.gabrielfreire.fitplanner.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabrielfreire.fitplanner.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpBinding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signUpBinding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(signUpBinding.root)

        initViewsAndSetListeners()
    }

    private fun initViewsAndSetListeners() {
        with(signUpBinding) {
            fabSignUpBack.setOnClickListener {
                finish()
            }
        }
    }
}