package com.gabrielfreire.fitplanner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.models.WorkoutPlan

class WorkoutPlansListAdapter(
    private val workoutPlansList: ArrayList<WorkoutPlan>,
    private val openWorkoutPlanButtonClickEvent: (WorkoutPlan) -> Unit
) : RecyclerView.Adapter<WorkoutPlansListAdapter.WorkoutListViewHolder>() {

    class WorkoutListViewHolder(view: View, openWorkoutPlanButtonClickEvent: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {

        private val tvWorkoutTitle: TextView = view.findViewById(R.id.tv_workout_title)
        private val tvWorkoutDescription: TextView =
            view.findViewById(R.id.tv_workout_description)
        private val btnOpenTraining: Button = view.findViewById(R.id.btn_open_workout)

        init {
            btnOpenTraining.setOnClickListener {
                openWorkoutPlanButtonClickEvent(adapterPosition)
            }
        }

        fun bind(workoutPlan: WorkoutPlan) {
            tvWorkoutTitle.text = workoutPlan.title
            tvWorkoutDescription.text = workoutPlan.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutListViewHolder =
        WorkoutListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.workout_list_item, parent, false)
        ) { index ->
            openWorkoutPlanButtonClickEvent(workoutPlansList[index])
        }

    override fun getItemCount(): Int = workoutPlansList.size

    override fun onBindViewHolder(holder: WorkoutListViewHolder, position: Int) {
        holder.bind(workoutPlansList[position])
    }
}