package com.shaswat.smart_attendance.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shaswat.smart_attendance.data.Course
import com.shaswat.smart_attendance.databinding.CourseLayoutBinding


class CoursesAdapter(
    private val courses: List<Course>,
) : RecyclerView.Adapter<CoursesAdapter.CourseViewHolder>() {

    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    inner class CourseViewHolder(binding: CourseLayoutBinding, listener: OnItemClickListener) :
        ViewHolder(binding.root) {
        val courseNameTextView = binding.courseName
        val courseImageView = binding.courseImage

        init {
            itemView.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CourseViewHolder(
        CourseLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        mListener
    )

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = courses[position]
        val colors = listOf(
            "#00539CFF",
            "#EEA47FFF",
            "#2F3C7E",
            "#FBEAEB",
            "#101820FF",
            "#FEE715FF",
            "#F96167",
            "#FCE77D",
            "#CCF381",
            "#4831D4"
        )
        holder.itemView.apply {
            holder.courseNameTextView.text = course.name
            holder.courseImageView.setColorFilter(Color.parseColor(colors[(0..colors.size).random() % colors.size]))
        }
    }

    override fun getItemCount() = courses.size
}