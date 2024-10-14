package sutanu.apps.todo

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import sutanu.apps.todo.db.TodoDatabase

class MainApplication : Application() {

    companion object{
        lateinit var todoDatabase: TodoDatabase
    }

    override fun onCreate() {
        super.onCreate()
        todoDatabase = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java,
            TodoDatabase.NAME
        ).build()
    }
}