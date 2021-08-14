package uz.gita.azipro.numberpuzzle15

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import uz.gita.azipro.numberpuzzle15.model.LocalStorage

class SettingActivity : AppCompatActivity() {
    private var localStorage: LocalStorage? = null
    private lateinit var switchCompat: SwitchCompat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        findViewById<ImageView>(R.id.back).setOnClickListener{
            finish()
        }
        localStorage = LocalStorage(this)
        switchCompat = findViewById(R.id.buttonSwitch)
        loadData()
        switchCompat.setOnClickListener { saveData() }




    }

    private fun saveData() {
       localStorage!!.audioPlay=switchCompat.isChecked
    }

    private fun loadData() {
        switchCompat.isChecked = localStorage!!.audioPlay
    }
}