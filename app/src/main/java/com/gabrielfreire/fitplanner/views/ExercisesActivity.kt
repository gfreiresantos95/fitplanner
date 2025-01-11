package com.gabrielfreire.fitplanner.views

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielfreire.fitplanner.Constants
import com.gabrielfreire.fitplanner.adapters.ExerciseListAdapter
import com.gabrielfreire.fitplanner.databinding.ActivityExercisesBinding
import com.gabrielfreire.fitplanner.models.UiStates
import com.gabrielfreire.fitplanner.models.WorkoutPlan
import com.google.gson.Gson

class ExercisesActivity : AppCompatActivity() {

    private lateinit var exercisesBinding: ActivityExercisesBinding

    private val exercisesUiState: MutableLiveData<UiStates> by lazy {
        MutableLiveData<UiStates>()
    }

    private val workoutPlan: MutableLiveData<WorkoutPlan> by lazy {
        MutableLiveData<WorkoutPlan>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exercisesBinding = ActivityExercisesBinding.inflate(layoutInflater)
        setContentView(exercisesBinding.root)

        setObservables()
        getWorkoutPlan()
        initViewsAndSetListeners()
    }

    private fun setObservables() {
        exercisesUiState.observe(this@ExercisesActivity) {
            updateExercisesUi()
        }

        workoutPlan.observe(this@ExercisesActivity) {
            exercisesUiState.value = UiStates.LOADED
        }
    }

    private fun getWorkoutPlan() {
        exercisesUiState.value = UiStates.LOADING

        if (intent.hasExtra(Constants.WORKOUT_PLAN_KEY)) {
            val stringExtra = intent.getStringExtra(Constants.WORKOUT_PLAN_KEY)

            workoutPlan.value = Gson().fromJson(stringExtra, WorkoutPlan::class.java)
        }
    }

    private fun initViewsAndSetListeners() {
        with(exercisesBinding) {
            mtExercisesToolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun updateExercisesUi() {
        when (exercisesUiState.value?.state) {
            UiStates.LOADING.state -> {
                with(exercisesBinding) {
                    llExercisesListContainer.visibility = View.GONE
                    llExercisesLoadingContainer.visibility = View.VISIBLE
                }
            }

            UiStates.LOADED.state -> {
                with(exercisesBinding) {
                    workoutPlan.value?.let { workoutPlan ->
                        mtExercisesToolbar.title = workoutPlan.title

                        rvExercisesList.layoutManager = LinearLayoutManager(this@ExercisesActivity)
                        rvExercisesList.adapter = ExerciseListAdapter(workoutPlan.exerciseList)
                    }

                    llExercisesLoadingContainer.visibility = View.GONE
                    llExercisesListContainer.visibility = View.VISIBLE
                }
            }

            UiStates.EMPTY.state -> {

            }

            else -> {

            }
        }
    }
}