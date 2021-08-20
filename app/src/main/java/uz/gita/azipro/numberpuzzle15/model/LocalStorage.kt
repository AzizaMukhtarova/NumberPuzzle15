package uz.gita.azipro.numberpuzzle15.model

import android.content.Context
import android.content.SharedPreferences

class LocalStorage(context: Context) {

    private val preferences = context.getSharedPreferences("LocalStorage", Context.MODE_PRIVATE)


    var score: String?
        set(value) = preferences.edit().putString("SCORE", value).apply()
        get() = preferences.getString("SCORE", null)

    var audioPlay: Boolean
        set(value) = preferences.edit().putBoolean("PLAY", value).apply()
        get() = preferences.getBoolean("PLAY", false)

    var audioSound: Boolean
        set(value) = preferences.edit().putBoolean("SOUND", value).apply()
        get() = preferences.getBoolean("SOUND", false)
}