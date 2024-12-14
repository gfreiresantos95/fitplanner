package com.gabrielfreire.fitplanner.views.fragments

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.adapters.WorkoutsListAdapter
import com.gabrielfreire.fitplanner.databinding.FragmentWorkoutsBinding
import com.gabrielfreire.fitplanner.models.DatabaseData
import com.gabrielfreire.fitplanner.models.DatabasePaths
import com.gabrielfreire.fitplanner.models.UsersWorkouts
import com.gabrielfreire.fitplanner.models.Workout
import com.gabrielfreire.fitplanner.models.WorkoutId
import com.gabrielfreire.fitplanner.models.WorkoutsUiStates
import com.gabrielfreire.fitplanner.views.ExercisesActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class WorkoutsFragment : Fragment() {

    private lateinit var workoutsBinding: FragmentWorkoutsBinding
    private lateinit var activity: FragmentActivity

    private val databaseReference: DatabaseReference = Firebase.database.reference
    private val workoutsIdsList = arrayListOf<WorkoutId>()

    private val workoutsUiState: MutableLiveData<WorkoutsUiStates> by lazy {
        MutableLiveData<WorkoutsUiStates>()
    }

    private val userWorkoutsData: MutableLiveData<DatabaseData> by lazy {
        MutableLiveData<DatabaseData>()
    }

    private val workoutsData: MutableLiveData<DatabaseData> by lazy {
        MutableLiveData<DatabaseData>()
    }

    private val workoutsList: MutableLiveData<ArrayList<Workout>> by lazy {
        MutableLiveData<ArrayList<Workout>>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        workoutsBinding = FragmentWorkoutsBinding.inflate(inflater, container, false)
        return workoutsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity = requireActivity()

        setObservables()
        getUserWorkoutsFromDatabase()
    }

    private fun setObservables() {
        workoutsUiState.observe(activity) {
            updateWorkoutsUi()
        }

        userWorkoutsData.observe(activity) { data ->
            if (data.success != null) {
                val snapshot = data.success

                if (snapshot?.hasChildren() == true) {
                    getUserWorkoutsList(snapshot)
                } else {
                    workoutsUiState.value = WorkoutsUiStates.EMPTY
                }
            } else {
                workoutsUiState.value = WorkoutsUiStates.ERROR
            }
        }

        workoutsData.observe(activity) { data ->
            if (data.success != null) {
                val snapshot = data.success

                if (snapshot?.hasChildren() == true) {
                    getWorkoutsList(snapshot)
                } else {
                    workoutsUiState.value = WorkoutsUiStates.ERROR
                }
            } else {
                workoutsUiState.value = WorkoutsUiStates.ERROR
            }
        }

        workoutsList.observe(activity) {
            workoutsUiState.value = WorkoutsUiStates.LOADED
        }
    }

    private fun getUserWorkoutsFromDatabase() {
        workoutsUiState.value = WorkoutsUiStates.LOADING

        databaseReference.child(DatabasePaths.USERS_WORKOUTS.path).get()
            .addOnSuccessListener { snapshot ->
                userWorkoutsData.value = DatabaseData(success = snapshot)
            }.addOnFailureListener { exception ->
                userWorkoutsData.value = DatabaseData(failure = exception)
            }
    }

    private fun getUserWorkoutsList(snapshot: DataSnapshot) {
        val loggedUserId = "c7745eb1-7db7-4afd-9003-104758b6f32d"

        snapshot.children.forEach { child ->
            val userWorkoutData = child.getValue(UsersWorkouts::class.java)

            userWorkoutData?.let { data ->
                if (data.isNotEmpty() && data.activeWorkout && data.userId == loggedUserId) {
                    workoutsIdsList.addAll(data.workoutsIdsList)
                    getWorkoutsListFromDatabase()
                }
            }
        }
    }

    private fun getWorkoutsListFromDatabase() {
        databaseReference.child(DatabasePaths.WORKOUTS.path).get()
            .addOnSuccessListener { snapshot ->
                workoutsData.value = DatabaseData(success = snapshot)
            }.addOnFailureListener { exception ->
                workoutsData.value = DatabaseData(failure = exception)
            }
    }

    private fun getWorkoutsList(snapshot: DataSnapshot) {
        val workouts = arrayListOf<Workout>()

        snapshot.children.forEach { child ->
            val workoutData = child.getValue(Workout::class.java)

            workoutData?.let { data ->
                if (data.isNotEmpty() && workoutsIdsList.contains(WorkoutId(data.id))) {
                    workouts.add(data)
                }
            }
        }

        workouts.sortBy { workout -> workout.priorityOrder }

        workoutsList.value = workouts
    }

    private fun updateWorkoutsUi() {
        when (workoutsUiState.value?.state) {
            WorkoutsUiStates.LOADING.state -> {
                with(workoutsBinding) {
                    llWorkoutsListContainer.visibility = View.GONE
                    llWorkoutsErrorContainer.visibility = View.GONE
                    llWorkoutsLoadingContainer.visibility = View.VISIBLE
                }
            }

            WorkoutsUiStates.LOADED.state -> {
                with(workoutsBinding) {
                    llWorkoutsLoadingContainer.visibility = View.GONE
                    llWorkoutsErrorContainer.visibility = View.GONE
                    llWorkoutsListContainer.visibility = View.VISIBLE

                    rvWorkoutsList.layoutManager = LinearLayoutManager(activity)
                    rvWorkoutsList.adapter = workoutsList.value?.let { list ->
                        WorkoutsListAdapter(list) {
                            val intent = Intent(activity, ExercisesActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }

            WorkoutsUiStates.EMPTY.state -> {
                val icon = ContextCompat.getDrawable(activity, R.drawable.ic_cloud)
                val title = getString(R.string.workouts_empty_title)
                val message = getString(R.string.workouts_empty_message)
                val buttonText = getString(R.string.refresh)

                setEmptyOrErrorUi(icon!!, title, message, buttonText)
            }

            else -> {
                val icon = ContextCompat.getDrawable(activity, R.drawable.ic_error)
                val title = getString(R.string.workouts_error_title)
                val message = getString(R.string.workouts_error_message)
                val buttonText = getString(R.string.try_again)

                setEmptyOrErrorUi(icon!!, title, message, buttonText)
            }
        }
    }

    private fun setEmptyOrErrorUi(
        icon: Drawable,
        title: String,
        message: String,
        buttonText: String
    ) {
        with(workoutsBinding) {
            llWorkoutsLoadingContainer.visibility = View.GONE
            llWorkoutsListContainer.visibility = View.GONE
            llWorkoutsErrorContainer.visibility = View.VISIBLE

            ivWorkoutsErrorIcon.setImageDrawable(icon)
            tvWorkoutsErrorTitle.text = title
            tvWorkoutsErrorMessage.text = message
            btnWorkoutsErrorAction.text = buttonText

            btnWorkoutsErrorAction.setOnClickListener {
                getUserWorkoutsFromDatabase()
            }
        }
    }
}