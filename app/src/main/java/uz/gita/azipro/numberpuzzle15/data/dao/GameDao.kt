package uz.gita.azipro.numberpuzzle15.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uz.gita.azipro.numberpuzzle15.data.entities.GameEntity


@Dao
interface GameDao {

    @Query("SELECT * FROM GameEntity")
    fun getHistory(): List<GameEntity>

    @Insert
    fun addHistory(subject: GameEntity)

    @Delete
    fun removeHistory(subject: GameEntity)
}