package com.example.smart_attendance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smart_attendance.adapters.CoursesAdapter
import com.example.smart_attendance.adapters.SessionsAdapter
import com.example.smart_attendance.data.Session
import com.example.smart_attendance.databinding.ActivityCourseDetailsBinding
import com.example.smart_attendance.other.EventObserver
import com.example.smart_attendance.other.snackbar
import com.example.smart_attendance.viewModels.CourseDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CourseDetails : AppCompatActivity() {

    private lateinit var binding: ActivityCourseDetailsBinding
    private val viewModel: CourseDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val courseId = intent.extras!!.getString("courseId")!!
        viewModel.getSessions(courseId);
        subscribeToObservers()
    }

    private fun setupSessionsList(sessions: List<Session>) {
        val sessionsAdapter = SessionsAdapter(sessions)
        binding.sessionsRecyclerView.adapter = sessionsAdapter
        binding.sessionsRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun subscribeToObservers() {
        viewModel.getSessionsStatus.observe(this, EventObserver(
            onError = {
                binding.sessionsProgressBar.isVisible = false
                snackbar(it, binding.root)
            },
            onLoading = { binding.sessionsProgressBar.isVisible = true }
        ) { sessionsDetail ->
            binding.sessionsProgressBar.isVisible = false
            binding.totalSessions.text =
                this.getString(R.string.total_sessions, sessionsDetail.sessionsCount)
            binding.attendedSessions.text =
                this.getString(R.string.attended_sessions, sessionsDetail.attendanceCount)
            setupSessionsList(sessionsDetail.sessions)
        })
    }
}