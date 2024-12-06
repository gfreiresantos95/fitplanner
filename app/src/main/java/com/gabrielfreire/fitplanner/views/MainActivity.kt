package com.gabrielfreire.fitplanner.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initViews()
    }

    private fun initViews() {
        with(mainBinding) {
            tvMainUserName.text = getUserName()
            tvMainLastWorkoutPerformed.text = getLastWorkout()
        }
    }

    private fun getUserName(): String {
        val username = getString(R.string.user)

        return getString(R.string.greeting, username)
    }

    private fun getLastWorkout(): String {
        val lastWorkout = getString(R.string.last_workout_example)

        return getString(R.string.last_workout_performed, lastWorkout)
    }
}