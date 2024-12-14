package com.gabrielfreire.fitplanner.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielfreire.fitplanner.Constants
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.databinding.ActivityExercisesBinding
import com.gabrielfreire.fitplanner.models.Workout
import com.google.gson.Gson

class ExercisesActivity : AppCompatActivity() {

    private lateinit var exercisesBinding: ActivityExercisesBinding

    private var workout = Workout()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercisesBinding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(exercisesBinding.root)

        getWorkoutExtra()
        initViewsAndSetListeners()
    }

    private fun getWorkoutExtra() {
        if (intent.hasExtra(Constants.WORKOUT_KEY)) {
            val extra = intent.getStringExtra(Constants.WORKOUT_KEY)

            workout = Gson().fromJson(extra, Workout::class.java)
        }
    }

    private fun initViewsAndSetListeners() {
        with(exercisesBinding) {
            mtExercisesToolbar.title = workout.title

            mtExercisesToolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }
}