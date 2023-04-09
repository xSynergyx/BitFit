package com.example.bitfit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HealthEntryAdapter(private val context: Context, private val entries: List<HealthEntry>) :
    RecyclerView.Adapter<HealthEntryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_health_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.bind(entry)
    }

    override fun getItemCount() = entries.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val dateTextView = itemView.findViewById<TextView>(R.id.date_tv)
        private val sleepTextView = itemView.findViewById<TextView>(R.id.sleep_tv)
        private val notesTextView = itemView.findViewById<TextView>(R.id.notes_tv)
        private val feelingTextView = itemView.findViewById<TextView>(R.id.mood_tv)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(entry: HealthEntry) {
            dateTextView.text = entry.date
            sleepTextView.text = "Slept ${entry.sleepHours.toString()} hours"
            notesTextView.text = entry.notes
            feelingTextView.text = "Feeling ${entry.mood.toString()}/10"

            /*
            Glide.with(context)
                .load(article.mediaImageUrl)
                .into(mediaImageView)
             */
        }

        override fun onClick(v: View?) {
            // TODO: Get selected article
            val entry = entries[adapterPosition]

            /*
            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(ARTICLE_EXTRA, article)
            context.startActivity(intent)
             */
        }
    }
}