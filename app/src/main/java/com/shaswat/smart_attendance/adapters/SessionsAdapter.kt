package com.shaswat.smart_attendance.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shaswat.smart_attendance.R
import com.shaswat.smart_attendance.data.Session
import com.shaswat.smart_attendance.databinding.SessionDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class SessionsAdapter(
    private val sessions: List<Session>,
) : RecyclerView.Adapter<SessionsAdapter.SessionsViewHolder>() {

    inner class SessionsViewHolder(binding: SessionDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val sessionDateTime = binding.dateTime
        val courseImageView = binding.courseImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SessionsViewHolder(
        SessionDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: SessionsAdapter.SessionsViewHolder, position: Int) {
        val session = sessions[position]
        holder.itemView.apply {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val date = dateFormat.parse(session.date)!!
            val outputDateFormat = SimpleDateFormat("MMM dd, yyyy h:mm a", Locale.getDefault())
            val outputDate = outputDateFormat.format(date)
            holder.sessionDateTime.text = outputDate
            if (session.attended) {
                holder.courseImageView.setImageResource(R.drawable.success)
            } else {
                holder.courseImageView.setImageResource(R.drawable.fail)
            }
        }
    }

    override fun getItemCount() = sessions.size
}