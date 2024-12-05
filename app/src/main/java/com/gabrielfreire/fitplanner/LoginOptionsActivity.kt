package com.gabrielfreire.fitplanner

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielfreire.fitplanner.databinding.ActivityLoginOptionsBinding

class LoginOptionsActivity : AppCompatActivity() {

    private lateinit var loginOptionsBinding: ActivityLoginOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginOptionsBinding = ActivityLoginOptionsBinding.inflate(layoutInflater)
        setContentView(loginOptionsBinding.root)

        setListeners()
    }

    private fun setListeners() {
        with(loginOptionsBinding) {
            btnStudentLoginOption.setOnClickListener {
                goToLogin()
            }

            btnPersonalTrainerLoginOption.setOnClickListener {
                goToLogin()
            }
        }
    }

    private fun goToLogin() {
        val intent = Intent(this@LoginOptionsActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}