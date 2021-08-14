package uz.gita.azipro.numberpuzzle15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

class WinActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)
        val bundle = intent.extras
        val moves = findViewById<TextView>(R.id.score_result)
        val time = findViewById<TextView>(R.id.time_result)

        if (bundle != null) {
            moves.text=bundle.getString("SCORE","0").toString()
            time.text=bundle.getString("TIME","--:--")
        }

        findViewById<LinearLayout>(R.id.playAgain).setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<LinearLayout>(R.id.backToHome).setOnClickListener{
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
            finish()
        }
        findViewById<LinearLayout>(R.id.finish).setOnClickListener{
            finishAffinity()
        }

    }
}