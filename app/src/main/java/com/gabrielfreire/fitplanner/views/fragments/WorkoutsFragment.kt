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
import com.gabrielfreire.fitplanner.Constants
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.adapters.WorkoutPlansListAdapter
import com.gabrielfreire.fitplanner.databinding.FragmentWorkoutsBinding
import com.gabrielfreire.fitplanner.models.DatabaseData
import com.gabrielfreire.fitplanner.models.DatabasePaths
import com.gabrielfreire.fitplanner.models.UiStates
import com.gabrielfreire.fitplanner.models.UserWorkoutPlan
import com.gabrielfreire.fitplanner.models.WorkoutPlan
import com.gabrielfreire.fitplanner.views.ExercisesActivity
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import com.google.gson.Gson

class WorkoutsFragment : Fragment() {

    private lateinit var workoutsBinding: FragmentWorkoutsBinding
    private lateinit var activity: FragmentActivity

    private val databaseReference: DatabaseReference = Firebase.database.reference

    private val workoutsUiState: MutableLiveData<UiStates> by lazy {
        MutableLiveData<UiStates>()
    }

    private val userWorkoutPlansData: MutableLiveData<DatabaseData> by lazy {
        MutableLiveData<DatabaseData>()
    }

    private val workoutPlansIdsList: ArrayList<String> = arrayListOf()

    private val workoutPlansData: MutableLiveData<DatabaseData> by lazy {
        MutableLiveData<DatabaseData>()
    }

    private val workoutPlansList: MutableLiveData<ArrayList<WorkoutPlan>> by lazy {
        MutableLiveData<ArrayList<WorkoutPlan>>()
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
        getUserWorkoutPlansFromDatabase()
    }

    private fun setObservables() {
        workoutsUiState.observe(activity) {
            updateWorkoutsUi()
        }

        userWorkoutPlansData.observe(activity) { data ->
            if (data.success != null) {
                val snapshot = data.success

                if (snapshot?.hasChildren() == true) {
                    getLoggedUserActiveWorkoutPlan(snapshot)
                } else {
                    workoutsUiState.value = UiStates.EMPTY
                }
            } else {
                workoutsUiState.value = UiStates.ERROR
            }
        }

        workoutPlansData.observe(activity) { data ->
            if (data.success != null) {
                val snapshot = data.success

                if (snapshot?.hasChildren() == true) {
                    getWorkoutPlans(snapshot)
                } else {
                    workoutsUiState.value = UiStates.EMPTY
                }
            } else {
                workoutsUiState.value = UiStates.ERROR
            }
        }

        workoutPlansList.observe(activity) { list ->
            list.sortBy { item -> item.priorityOrder }
            workoutsUiState.value = UiStates.LOADED
        }
    }

    private fun getUserWorkoutPlansFromDatabase() {
        workoutsUiState.value = UiStates.LOADING

        databaseReference.child(DatabasePaths.USER_WORKOUT_PLANS.path).get()
            .addOnSuccessListener { snapshot ->
                userWorkoutPlansData.value = DatabaseData(success = snapshot)
            }.addOnFailureListener { exception ->
                userWorkoutPlansData.value = DatabaseData(failure = exception)
            }
    }

    private fun getLoggedUserActiveWorkoutPlan(snapshot: DataSnapshot) {
        val loggedUserId = "c7745eb1-7db7-4afd-9003-104758b6f32d"

        snapshot.children.forEach { child ->
            val userWorkoutPlan = child.getValue(UserWorkoutPlan::class.java)

            userWorkoutPlan?.let { plan ->
                if (plan.userId == loggedUserId && plan.workoutPlanIsActive) {
                    workoutPlansIdsList.addAll(plan.listOfWorkoutPlanIds)
                    getWorkoutPlansFromDatabase()
                }
            }
        }
    }

    private fun getWorkoutPlansFromDatabase() {
        databaseReference.child(DatabasePaths.WORKOUT_PLANS.path).get()
            .addOnSuccessListener { snapshot ->
                workoutPlansData.value = DatabaseData(success = snapshot)
            }.addOnFailureListener { exception ->
                workoutPlansData.value = DatabaseData(failure = exception)
            }
    }

    private fun getWorkoutPlans(snapshot: DataSnapshot) {
        val workoutPlans = arrayListOf<WorkoutPlan>()

        snapshot.children.forEach { child ->
            if (workoutPlansIdsList.contains(child.key)) {
                val workoutPlan = child.getValue(WorkoutPlan::class.java)

                workoutPlan?.let { plan ->
                    workoutPlans.add(plan)
                }
            }
        }

        workoutPlansList.value = workoutPlans
    }

    private fun updateWorkoutsUi() {
        when (workoutsUiState.value?.state) {
            UiStates.LOADING.state -> {
                with(workoutsBinding) {
                    llWorkoutsListContainer.visibility = View.GONE
                    llWorkoutsErrorContainer.visibility = View.GONE
                    llWorkoutsLoadingContainer.visibility = View.VISIBLE
                }
            }

            UiStates.LOADED.state -> {
                with(workoutsBinding) {
                    rvWorkoutsList.layoutManager = LinearLayoutManager(activity)
                    rvWorkoutsList.adapter = workoutPlansList.value?.let { list ->
                        WorkoutPlansListAdapter(list) { workoutPlan ->
                            val intent = Intent(activity, ExercisesActivity::class.java)
                            val workoutPlanJson = Gson().toJson(workoutPlan)

                            intent.putExtra(Constants.WORKOUT_PLAN_KEY, workoutPlanJson)

                            startActivity(intent)
                        }
                    }

                    llWorkoutsLoadingContainer.visibility = View.GONE
                    llWorkoutsErrorContainer.visibility = View.GONE
                    llWorkoutsListContainer.visibility = View.VISIBLE
                }
            }

            UiStates.EMPTY.state -> {
                val icon = ContextCompat.getDrawable(activity, R.drawable.ic_cloud)
                val iconContentDescription =
                    getString(R.string.no_workouts_found_content_description)
                val title = getString(R.string.workouts_empty_title)
                val message = getString(R.string.workouts_empty_message)
                val buttonText = getString(R.string.refresh)

                setEmptyOrErrorUi(icon!!, iconContentDescription, title, message, buttonText)
            }

            else -> {
                val icon = ContextCompat.getDrawable(activity, R.drawable.ic_error)
                val iconContentDescription =
                    getString(R.string.error_loading_workouts_content_description)
                val title = getString(R.string.workouts_error_title)
                val message = getString(R.string.workouts_error_message)
                val buttonText = getString(R.string.try_again)

                setEmptyOrErrorUi(icon!!, iconContentDescription, title, message, buttonText)
            }
        }
    }

    private fun setEmptyOrErrorUi(
        icon: Drawable,
        iconContentDescription: String,
        title: String,
        message: String,
        buttonText: String
    ) {
        with(workoutsBinding) {
            llWorkoutsLoadingContainer.visibility = View.GONE
            llWorkoutsListContainer.visibility = View.GONE
            llWorkoutsErrorContainer.visibility = View.VISIBLE

            ivWorkoutsErrorIcon.setImageDrawable(icon)
            ivWorkoutsErrorIcon.contentDescription = iconContentDescription
            tvWorkoutsErrorTitle.text = title
            tvWorkoutsErrorMessage.text = message
            btnWorkoutsErrorAction.text = buttonText

            btnWorkoutsErrorAction.setOnClickListener {
                getUserWorkoutPlansFromDatabase()
            }
        }
    }
}