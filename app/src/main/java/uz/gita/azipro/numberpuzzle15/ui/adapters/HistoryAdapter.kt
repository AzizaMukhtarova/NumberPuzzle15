package uz.gita.azipro.numberpuzzle15.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uz.gita.azipro.numberpuzzle15.R
import uz.gita.azipro.numberpuzzle15.data.entities.GameEntity

class HistoryAdapter(
    private val data: List<GameEntity>
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val score: TextView = view.findViewById(R.id.score_result)
        val time: TextView = view.findViewById(R.id.time_result)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = data[position]
        holder.apply {
            score.text = history.score
            time.text = history.time
        }
    }

    override fun getItemCount() = data.size
}