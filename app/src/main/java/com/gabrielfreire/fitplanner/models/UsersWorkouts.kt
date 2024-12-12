package com.gabrielfreire.fitplanner.models

data class UsersWorkouts(
    val id: String = "",
    val userId: String = "",
    val activeWorkout: Boolean = false,
    val workoutsIdsList: List<WorkoutId> = listOf()
) {

    fun isNotEmpty(): Boolean {
        return id.isNotEmpty() && userId.isNotEmpty() && workoutsIdsList.isNotEmpty()
    }
}