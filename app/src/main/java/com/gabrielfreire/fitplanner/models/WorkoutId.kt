package com.gabrielfreire.fitplanner.models

data class WorkoutId(
    val workoutId: String = ""
) {

    fun isNotEmpty(): Boolean {
        return workoutId.isNotEmpty()
    }
}