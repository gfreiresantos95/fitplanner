package com.gabrielfreire.fitplanner.models

data class WorkoutPlan(
    val createdAt: String = "",
    val description: String = "",
    val exerciseList: ArrayList<Exercise> = arrayListOf(),
    val priorityOrder: Int = 0,
    val title: String = "",
    val type: String = ""
)