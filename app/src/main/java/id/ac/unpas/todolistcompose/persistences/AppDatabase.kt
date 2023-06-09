package id.ac.unpas.todolistcompose.persistences

import androidx.room.Database
import androidx.room.RoomDatabase
import id.ac.unpas.todolistcompose.model.Todo

@Database(entities = [Todo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}