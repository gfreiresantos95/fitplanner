package com.gabrielfreire.fitplanner

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
    }
}