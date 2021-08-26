package uz.gita.azipro.numberpuzzle15.ui.screens

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import uz.gita.azipro.numberpuzzle15.R
import uz.gita.azipro.numberpuzzle15.data.database.AppDatabase
import uz.gita.azipro.numberpuzzle15.ui.adapters.HistoryAdapter
import uz.gita.azipro.numberpuzzle15.data.database.LocalStorage
import uz.gita.azipro.numberpuzzle15.data.entities.GameEntity
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var adapter: HistoryAdapter
    private lateinit var localStorage: LocalStorage
    private lateinit var recyclerView: RecyclerView
    private lateinit var deleteDialog: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        localStorage = LocalStorage(this)
        recyclerView = findViewById(R.id.list)
        deleteDialog = findViewById(R.id.fl_delete_dialog)
        updateRecyclerView()

        findViewById<FrameLayout>(R.id.back).setOnClickListener {
            finish()
        }

        findViewById<FrameLayout>(R.id.clear).setOnClickListener {
            val list = AppDatabase.getAppDatabase(this).gameDao().getHistory()
            if (list.isNotEmpty()) {
                deleteDialog.visibility = View.VISIBLE
                findViewById<TextView>(R.id.tv_no).setOnClickListener {
                    deleteDialog.visibility = View.GONE
                }

                findViewById<TextView>(R.id.tv_yes).setOnClickListener {
                    deleteDialog.visibility = View.GONE
                    AppDatabase.getAppDatabase(this).gameDao().deleteAll()
                    updateRecyclerView()
                }
            } else Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateRecyclerView() {
        val list = AppDatabase.getAppDatabase(this).gameDao().getHistory()
        adapter = HistoryAdapter(list.asReversed())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }
}