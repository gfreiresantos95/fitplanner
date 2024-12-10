package com.gabrielfreire.fitplanner.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.gabrielfreire.fitplanner.Constants
import com.gabrielfreire.fitplanner.Workout
import com.gabrielfreire.fitplanner.WorkoutsListAdapter
import com.gabrielfreire.fitplanner.databinding.FragmentWorkoutsBinding
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class WorkoutsFragment : Fragment() {

    private lateinit var workoutsBinding: FragmentWorkoutsBinding
    private lateinit var databaseReference: DatabaseReference

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

        databaseReference = Firebase.database.reference

        setObservables()
        getWorkoutsFromDatabase()
    }

    private fun setObservables() {
        workoutsList.observe(requireActivity()) {
            showWorkoutsList()
        }
    }

    private fun getWorkoutsFromDatabase() {
        databaseReference.child(Constants.WORKOUTS_STRING_PATH).get()
            .addOnSuccessListener { snapshot ->
                val workouts = arrayListOf<Workout>()

                snapshot.children.forEach { child ->
                    val workoutData = child.getValue(Workout::class.java)

                    workoutData?.let { workout ->
                        if (workout.isNotEmpty()) {
                            workouts.add(workout)
                        }
                    }
                }

                workouts.sortBy { workout -> workout.orderNumber }

                workoutsList.value = workouts
            }.addOnFailureListener { exception ->
                Log.e("firebase", "Error getting data", exception)
            }
    }

    private fun showWorkoutsList() {
        with(workoutsBinding) {
            llWorkoutsLoadingContainer.visibility = View.GONE
            llWorkoutsContentContainer.visibility = View.VISIBLE

            rvWorkoutsList.layoutManager = LinearLayoutManager(requireActivity())
            rvWorkoutsList.adapter = workoutsList.value?.let { list ->
                WorkoutsListAdapter(list) {
                    Toast.makeText(
                        requireActivity(),
                        "Ainda não podemos abrir esse treino!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}