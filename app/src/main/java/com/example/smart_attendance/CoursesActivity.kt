package com.example.smart_attendance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smart_attendance.adapters.CoursesAdapter
import com.example.smart_attendance.data.Course
import com.example.smart_attendance.databinding.ActivityCoursesBinding
import com.example.smart_attendance.other.EventObserver
import com.example.smart_attendance.other.snackbar
import com.example.smart_attendance.viewModels.CoursesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoursesBinding
    private val viewModel: CoursesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoursesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToObservers()
    }


    private fun setupCoursesList(courses: List<Course>){
        val coursesAdapter = CoursesAdapter(courses)
        binding.coursesRecyclerView.adapter = coursesAdapter
        coursesAdapter.setOnItemClickListener(object : CoursesAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val course = courses[position]
                e("Courses", "itemClicked ${courses[position]._id}") // TODO Show session details and attendance

            }
        })
        binding.coursesRecyclerView.layoutManager = GridLayoutManager(this, 2)
    }
    private fun subscribeToObservers(){
        viewModel.getCoursesStatus.observe(this, EventObserver(
            onError = {
                binding.coursesProgressBar.isVisible = false
                snackbar(it, binding.root)
            },
            onLoading = { binding.coursesProgressBar.isVisible = true }
        ) { courses ->
            binding.coursesProgressBar.isVisible = false
            setupCoursesList(courses)
        })
    }
}