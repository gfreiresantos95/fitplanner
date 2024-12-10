package com.gabrielfreire.fitplanner

data class Workout(
    val description: String = "",
    val exerciseListId: String = "",
    val id: String = "",
    val title: String = "",
    val orderNumber: Int = 0
) {

    fun isNotEmpty(): Boolean {
        return id.isNotEmpty() && title.isNotEmpty() && exerciseListId.isNotEmpty()
    }
}