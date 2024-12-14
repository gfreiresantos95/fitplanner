package com.gabrielfreire.fitplanner.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.databinding.ActivityExercisesBinding

class ExercisesActivity : AppCompatActivity() {

    private lateinit var exercisesBinding: ActivityExercisesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercisesBinding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(exercisesBinding.root)
    }
}