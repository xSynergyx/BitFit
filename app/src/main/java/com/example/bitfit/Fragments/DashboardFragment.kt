package com.example.bitfit.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.bitfit.HealthEntry
import com.example.bitfit.HealthEntryApplication
import com.example.bitfit.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private val healthEntries = mutableListOf<HealthEntry>()
    lateinit var chart: LineChart

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
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val averageSleepTv: TextView = view.findViewById(R.id.average_sleep_tv)
        val averageMoodTv: TextView = view.findViewById(R.id.average_feeling_tv)
        chart = view.findViewById(R.id.line_chart)

        // Get average sleep hours
        lifecycleScope.launch(Dispatchers.IO) {
            val averageSleep = (requireActivity().application as HealthEntryApplication).db.healthEntryDao().getAverageSleep()
            val averageSleepText = "Average Sleep: $averageSleep"
            averageSleepTv.text = averageSleepText
        }

        // Get average mood hours
        lifecycleScope.launch(Dispatchers.IO) {
            val averageMood = (requireActivity().application as HealthEntryApplication).db.healthEntryDao().getAverageMood()
            val averageMoodText = "Average Feeling: $averageMood/10"
            averageMoodTv.text = averageMoodText
        }

        populateLineChart()
    }

    private fun populateLineChart() {

        val chartSleepEntries: ArrayList<Entry> = ArrayList()
        val chartMoodEntries: ArrayList<Entry> = ArrayList()

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
                    healthEntries.addAll(mappedList)
                    healthEntries.forEachIndexed { index, it ->
                        chartSleepEntries.add(Entry(index.toFloat() +1,  it.sleepHours!!))
                        chartMoodEntries.add(Entry(index.toFloat() +1,  it.mood!!))
                    }

                    val sleepDataSet = LineDataSet(chartSleepEntries, "Hours Slept") // add entries to dataset
                    val moodDataSet = LineDataSet(chartMoodEntries, "Mood")
                    sleepDataSet.setColor(R.color.starca_green_analogous_variant)
                    sleepDataSet.valueTextColor = R.color.black

                    //moodDataSet.color = R.color.purple_500
                    moodDataSet.valueTextColor = R.color.black

                    var lineData = LineData(sleepDataSet, moodDataSet)
                    chart.data = lineData
                    chart.setGridBackgroundColor(com.google.android.material.R.color.mtrl_btn_transparent_bg_color)
                    chart.notifyDataSetChanged()
                }
            }
        }


    }

    companion object {
    }
}