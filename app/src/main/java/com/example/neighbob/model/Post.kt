package com.example.neighbob.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,   // unique ID
    val category: String,   // e.g. "Help", "Lost & Found"
    val title: String,      // title of the post
    val description: String,// description text
    val pinCode: String = "",    // location (optional for filtering)
    val timestamp: Long = System.currentTimeMillis() // created time
)
