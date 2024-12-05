package com.gabrielfreire.fitplanner.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabrielfreire.fitplanner.Constants
import com.gabrielfreire.fitplanner.UserType
import com.gabrielfreire.fitplanner.databinding.ActivityLoginOptionsBinding

class LoginOptionsActivity : AppCompatActivity() {

    private lateinit var loginOptionsBinding: ActivityLoginOptionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginOptionsBinding = ActivityLoginOptionsBinding.inflate(layoutInflater)
        setContentView(loginOptionsBinding.root)

        initViewsAndSetListeners()
    }

    private fun initViewsAndSetListeners() {
        with(loginOptionsBinding) {
            btnStudentLoginOption.setOnClickListener {
                goToLogin(UserType.STUDENT.type)
            }

            btnPersonalTrainerLoginOption.setOnClickListener {
                goToLogin(UserType.PERSONAL_TRAINER.type)
            }
        }
    }

    private fun goToLogin(userType: String) {
        val intent = Intent(this@LoginOptionsActivity, LoginActivity::class.java)
        intent.putExtra(Constants.USER_TYPE, userType)
        startActivity(intent)
    }
}