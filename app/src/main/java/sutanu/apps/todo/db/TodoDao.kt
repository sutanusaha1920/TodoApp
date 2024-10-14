package sutanu.apps.todo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sutanu.apps.todo.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getAllTodo() : LiveData<List<Todo>>

    @Insert
    fun addTodo(todo: Todo)

    @Query("DELETE FROM Todo WHERE id = :id")
    fun deleteTodo(id : Int)

}