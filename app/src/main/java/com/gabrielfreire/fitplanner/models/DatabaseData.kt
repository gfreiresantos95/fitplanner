package com.gabrielfreire.fitplanner.models

import com.google.firebase.database.DataSnapshot

data class DatabaseData(
    var success: DataSnapshot? = null,
    var failure: Exception? = null
)
