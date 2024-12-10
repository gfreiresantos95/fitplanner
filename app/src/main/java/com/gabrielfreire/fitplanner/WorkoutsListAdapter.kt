package com.gabrielfreire.fitplanner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutsListAdapter(
    private val workoutList: ArrayList<Workout>,
    private val openWorkoutButtonClickEvent: (Workout) -> Unit
) : RecyclerView.Adapter<WorkoutsListAdapter.WorkoutListViewHolder>() {

    class WorkoutListViewHolder(view: View, openWorkoutButtonClickEvent: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val tvWorkoutTitle: TextView = view.findViewById(R.id.tv_workout_title)
        private val tvWorkoutDescription: TextView =
            view.findViewById(R.id.tv_workout_description)
        private val btnOpenTraining: Button = view.findViewById(R.id.btn_open_workout)

        init {
            btnOpenTraining.setOnClickListener {
                openWorkoutButtonClickEvent(adapterPosition)
            }
        }

        fun bind(workout: Workout) {
            tvWorkoutTitle.text = workout.title
            tvWorkoutDescription.text = workout.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutListViewHolder =
        WorkoutListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.workout_list_item, parent, false)
        ) { index ->
            openWorkoutButtonClickEvent(workoutList[index])
        }

    override fun getItemCount(): Int = workoutList.size

    override fun onBindViewHolder(holder: WorkoutListViewHolder, position: Int) {
        holder.bind(workoutList[position])
    }
}