package uz.gita.azipro.numberpuzzle15.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_table")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var score: String,
    var time: String
)
