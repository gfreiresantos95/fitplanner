package com.gabrielfreire.fitplanner.models

data class Workout(
    val description: String = "",
    val exerciseListId: String = "",
    val id: String = "",
    val title: String = "",
    val priorityOrder: Int = 0
) {

    fun isNotEmpty(): Boolean {
        return id.isNotEmpty() && title.isNotEmpty() && exerciseListId.isNotEmpty()
    }
}