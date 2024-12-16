package com.gabrielfreire.fitplanner.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielfreire.fitplanner.R
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