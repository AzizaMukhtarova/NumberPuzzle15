package uz.gita.azipro.numberpuzzle15.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.gita.azipro.numberpuzzle15.R
import uz.gita.azipro.numberpuzzle15.data.database.AppDatabase
import uz.gita.azipro.numberpuzzle15.ui.adapters.HistoryAdapter
import uz.gita.azipro.numberpuzzle15.data.database.LocalStorage

class HistoryActivity : AppCompatActivity() {

    private lateinit var adapter: HistoryAdapter
    private lateinit var localStorage: LocalStorage
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        localStorage = LocalStorage(this)
        recyclerView = findViewById(R.id.list)

        adapter = HistoryAdapter(
            AppDatabase.getAppDatabase(this).gameDao()
                .getHistory()
        )
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true)

        findViewById<FrameLayout>(R.id.back).setOnClickListener {
            finish()
        }

    }

}