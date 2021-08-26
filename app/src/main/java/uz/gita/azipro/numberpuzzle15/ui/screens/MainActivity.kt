package uz.gita.azipro.numberpuzzle15.ui.screens

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.ViewGroup
import android.widget.*
import uz.gita.azipro.numberpuzzle15.R
import uz.gita.azipro.numberpuzzle15.data.database.AppDatabase
import uz.gita.azipro.numberpuzzle15.data.database.LocalStorage
import uz.gita.azipro.numberpuzzle15.data.entities.GameEntity
import uz.gita.azipro.numberpuzzle15.data.model.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var buttons: Array<Array<Button?>>
    private lateinit var empty: Coordinate
    private lateinit var numbers: ArrayList<String>
    private lateinit var textScore: TextView
    private lateinit var chronometer: Chronometer
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var buttonSound: MediaPlayer
    private lateinit var localStorage: LocalStorage
    private lateinit var homeButton: LinearLayout
    private lateinit var restart: LinearLayout
    private lateinit var bgMusic: ImageButton
    private lateinit var btnSound: ImageButton
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        localStorage = LocalStorage(this)

        initButtons()
        initNumbers()
        loadNumbers()
        restart.setOnClickListener { clickRestart() }
        homeButton.setOnClickListener { clickBackToHome() }

        findViewById<ImageButton>(R.id.completePuzzle).setOnClickListener {
            completePuzzle()
        }
        bgMusic.setOnClickListener { onOffMusic() }
        btnSound.setOnClickListener { onOffSound() }
    }

    private fun onOffSound() {
        if (localStorage.audioSound) {
            localStorage.audioSound = false
            btnSound.setImageResource(R.drawable.ic_mute_volume)
        } else {
            localStorage.audioSound = true
            btnSound.setImageResource(R.drawable.ic_volume)
        }
    }

    private fun onOffMusic() {
        if (localStorage.audioPlay) {
            mediaPlayer.stop()
            bgMusic.setImageResource(R.drawable.ic_muted_note)
            localStorage.audioPlay = false

        } else {
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.isLooping = true
            bgMusic.setImageResource(R.drawable.ic_music_note)
            localStorage.audioPlay = true
        }
    }

    private fun initButtons() {
        bgMusic = findViewById(R.id.bgMusic)
        btnSound = findViewById(R.id.btnSound)
        mediaPlayer = MediaPlayer.create(this, R.raw.audio)
        buttonSound = MediaPlayer.create(this, R.raw.button_audio)
        homeButton = findViewById(R.id.backToHome)
        restart = findViewById(R.id.restart)
        buttons = Array(4) { arrayOfNulls(4) }
        textScore = findViewById(R.id.textScore)
        chronometer = findViewById(R.id.chronometer)
        val group = findViewById<ViewGroup>(R.id.container)

        if (localStorage.audioSound) {
            btnSound.setImageResource(R.drawable.ic_volume)
        } else btnSound.setImageResource(R.drawable.ic_mute_volume)

        if (localStorage.audioPlay) {
            bgMusic.setImageResource(R.drawable.ic_music_note)
        } else bgMusic.setImageResource(R.drawable.ic_muted_note)

        val count = group.childCount
        empty = Coordinate(3, 3)
        for (i in 0 until count) {
            val button = group.getChildAt(i) as Button
            val x = i / 4
            val y = i % 4
            buttons[x][y] = button
            val coordinate = Coordinate(x, y)
            button.tag = coordinate
            button.setOnClickListener {
                clickGameItem(button)
            }
        }
    }

    private fun initNumbers() {
        numbers = ArrayList()
        for (i in 1..15) {
            numbers.add(i.toString())
        }
    }

    private fun loadNumbers() {
        numbers.shuffle()
        for (i in 0..14) {
            val x = i / 4
            val y = i % 4
            val button = buttons[x][y]
            if (button != null) {
                button.text = numbers[i]
            }
            button?.setBackgroundResource(R.drawable.ic_game_item)
        }
        setDefaultValue()
    }

    private fun completePuzzle() {
        initNumbers()
        for (i in 0..14) {
            val x = i / 4
            val y = i % 4
            val button = buttons[x][y]
            if (button != null) {
                button.text = numbers[i]
            }
            button?.setBackgroundResource(R.drawable.ic_game_item)
        }
        empty.x = 3
        empty.y = 3
        buttons[3][3]!!.text = ""
        buttons[3][3]!!.setBackgroundResource(R.drawable.empty_item)
    }

    private fun setDefaultValue() {
        empty.x = 3
        empty.y = 3
        buttons[3][3]!!.text = ""
        buttons[3][3]!!.setBackgroundResource(R.drawable.empty_item)
        textScore.text = "0"
        score = 0
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
    }

    private fun clickGameItem(button: Button) {
        if (localStorage.audioSound) {
            buttonSound.start()
        }
        val c = button.tag as Coordinate
        val dx: Int = kotlin.math.abs(empty.x - c.x)
        val dy: Int = kotlin.math.abs(empty.y - c.y)
        if (dx + dy == 1) {
            score++
            val emptyButton = buttons[empty.x][empty.y]
            if (emptyButton != null) {
                emptyButton.text = button.text
            }
            emptyButton?.setBackgroundResource(R.drawable.ic_game_item)
            button.setBackgroundResource(R.drawable.empty_item)
            button.text = ""
            empty.x = c.x
            empty.y = c.y
            textScore.text = score.toString()
            win()
        }
    }

    private fun isWin(): Boolean {
        var t = true
        for (i in 0..14) {
            val x = i / 4
            val y = i % 4
            val button = buttons[x][y]
            val s = button?.text.toString()
            t = t and (s == (i + 1).toString())
        }
        return t
    }

    private fun win() {
        if (isWin()) {
            val intent = Intent(this, WinActivity::class.java)
            intent.putExtra("SCORE", textScore.text.toString())
            intent.putExtra("TIME", chronometer.text.toString())
            startActivity(intent)
            saveHistory(textScore.text.toString(), chronometer.text.toString())
            finish()
            loadNumbers()
        }
    }

    private fun saveHistory(score: String, time: String) {
        val history = GameEntity(0, score, time)
        AppDatabase.getAppDatabase(this).gameDao().addHistory(history)
    }

    private var pauseTime: Long = 0

    override fun onStart() {
        super.onStart()
        if (localStorage.audioPlay) {
            mediaPlayer.start()
            mediaPlayer.isLooping = true
        } else mediaPlayer.stop()

        if (pauseTime != 0L) {
            chronometer.base = (SystemClock.elapsedRealtime() + pauseTime)
            chronometer.start()
        }
    }


    override fun onStop() {
        mediaPlayer.pause()
        super.onStop()
        pauseTime = chronometer.base - SystemClock.elapsedRealtime()
        chronometer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }

    private fun clickRestart() {
        loadNumbers()
    }

    private fun clickBackToHome() {
        val intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
        finish()
    }


}