package com.example.searchnotesappviewmodel

import androidx.recyclerview.widget.DiffUtil

class NoteDiffCallback : DiffUtil.ItemCallback<NotesData>() {
    override fun areItemsTheSame(oldItem: NotesData, newItem: NotesData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NotesData, newItem: NotesData): Boolean {
        return oldItem == newItem
    }
}