package com.gabrielfreire.fitplanner.models

enum class DatabasePaths(val path: String) {
    USERS("users"),
    TRAINERS("trainers"),
    WORKOUT_PLANS("workoutPlans"),
    USER_WORKOUT_PLANS("userWorkoutPlans")
}