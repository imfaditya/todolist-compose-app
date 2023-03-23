package id.ac.unpas.todolistcompose.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey val id: String,
    val date: String,
    val task: String,
    val description: String,
    val priority: String,
);