package uz.gita.azipro.numberpuzzle15.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.azipro.numberpuzzle15.data.dao.GameDao
import uz.gita.azipro.numberpuzzle15.data.entities.GameEntity

@Database(entities = [GameEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {

    abstract fun gameDao():GameDao

    companion object {
        private var appDatabase: AppDatabase? = null
        fun getAppDatabase(context: Context) = appDatabase ?: let {
            val temp = Room
                .databaseBuilder(context, AppDatabase::class.java, "np")
                .allowMainThreadQueries()
                .build()
            appDatabase = temp
            temp
        }
    }
}