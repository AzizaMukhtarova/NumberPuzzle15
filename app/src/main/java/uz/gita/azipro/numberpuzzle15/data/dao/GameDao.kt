package uz.gita.azipro.numberpuzzle15.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import uz.gita.azipro.numberpuzzle15.data.entities.GameEntity


@Dao
interface GameDao {

    @Query("SELECT * FROM game_table")
    fun getHistory(): List<GameEntity>

    @Insert
    fun addHistory(subject: GameEntity)

    @Delete
    fun removeHistory(subject: GameEntity)

    @Query("DELETE FROM game_table")
    fun deleteAll()
}