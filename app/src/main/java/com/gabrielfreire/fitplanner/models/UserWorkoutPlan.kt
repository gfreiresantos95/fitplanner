package com.gabrielfreire.fitplanner.models

data class UserWorkoutPlan(
    val listOfWorkoutPlanIds: ArrayList<String> = arrayListOf(),
    val userId: String = "",
    val workoutPlanIsActive: Boolean = false
)