package uz.gita.azipro.numberpuzzle15.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import uz.gita.azipro.numberpuzzle15.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        findViewById<FrameLayout>(R.id.back).setOnClickListener{
            finish()
        }
    }
}