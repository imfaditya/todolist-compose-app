package id.ac.unpas.todolistcompose.persistences

import androidx.lifecycle.LiveData
import androidx.room.*
import id.ac.unpas.todolistcompose.model.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun loadAll(): LiveData<List<Todo>>

    @Query("SELECT * FROM Todo WHERE id = :id")
    fun find(id: String): Todo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg items: Todo)

    @Delete
    fun delete(item: Todo)
}