package com.gabrielfreire.fitplanner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.models.Exercise

class ExerciseListAdapter(
    private val exerciseList: ArrayList<Exercise>
) : RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder>() {

    class ExerciseListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvExerciseTitle: TextView = view.findViewById(R.id.tv_exercise_title)
        private val tvExerciseSetsAndReps: TextView =
            view.findViewById(R.id.tv_exercise_sets_and_reps)
        private val tvExerciseWeight: TextView = view.findViewById(R.id.tv_exercise_weight)

        fun bind(exercise: Exercise) {
            tvExerciseTitle.text = exercise.name
            tvExerciseSetsAndReps.text = itemView.resources.getString(
                R.string.exercise_series_number,
                exercise.sets,
                exercise.reps
            )
            tvExerciseWeight.text =
                itemView.resources.getString(R.string.exercise_weight, exercise.weight)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder =
        ExerciseListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.exercise_list_item, parent, false)
        )

    override fun getItemCount(): Int = exerciseList.size

    override fun onBindViewHolder(holder: ExerciseListViewHolder, position: Int) {
        holder.bind(exerciseList[position])
    }
}