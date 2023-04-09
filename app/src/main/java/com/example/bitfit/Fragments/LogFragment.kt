package com.example.bitfit.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.Slider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LogFragment : Fragment() {

    private val healthEntries = mutableListOf<HealthEntry>()
    private lateinit var healthEntriesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_log, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        healthEntriesRecyclerView = view.findViewById(R.id.health_entries_rv)
        val healthEntryAdapter = HealthEntryAdapter(requireContext(), healthEntries)
        healthEntriesRecyclerView.adapter = healthEntryAdapter

        healthEntriesRecyclerView.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            healthEntriesRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        // Getting all entries from DB
        lifecycleScope.launch {
            (requireActivity().application as HealthEntryApplication).db.healthEntryDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    HealthEntry(
                        entity.sleepHours,
                        entity.mood,
                        entity.notes,
                        entity.date
                    )
                }.also { mappedList ->
                    healthEntries.clear()
                    healthEntries.addAll(mappedList)
                    healthEntryAdapter.notifyDataSetChanged()
                }
            }
        }

        val entryFab: FloatingActionButton = view.findViewById(R.id.add_entry_fab)
        val newEntryLayout: LinearLayout = view.findViewById(R.id.health_entry_ll)
        val saveEntryButton: Button = view.findViewById(R.id.save_entry_button)

        // Assigning variables for new-entry fields
        val hoursSleptSlider: Slider = view.findViewById(R.id.sleep_slider)
        val moodSlider: Slider = view.findViewById(R.id.mood_slider)
        val notesEt: EditText = view.findViewById(R.id.notes_et)

        entryFab.setOnClickListener {
            newEntryLayout.visibility = View.VISIBLE
        }

        saveEntryButton.setOnClickListener {
            val hoursSlept = hoursSleptSlider.value
            val mood = moodSlider.value
            val notes = notesEt.text.toString()

            var date = LocalDate.now()
            var formatter = DateTimeFormatter.ofPattern("MMM dd yyyy")
            var formattedDate = date.format(formatter)

            //Create new entry and add to RecyclerView
            val newEntry = HealthEntry(hoursSlept, mood, notes, formattedDate)
            healthEntries.add(newEntry)

            // Reset the notes field and sliders
            notesEt.text.clear()
            hoursSleptSlider.value = 0.0.toFloat()
            moodSlider.value = 0.0.toFloat()

            newEntryLayout.visibility = View.GONE

            lifecycleScope.launch(Dispatchers.IO) {
                //(application as HealthEntryApplication).db.healthEntryDao().deleteAll()
                (requireActivity().application as HealthEntryApplication).db.healthEntryDao().insert(
                    HealthEntryEntity(
                        sleepHours = newEntry.sleepHours,
                        mood = newEntry.mood,
                        notes = newEntry.notes,
                        date = newEntry.date
                    )
                )
            }
        }
    }

    companion object {

    }
}