package com.example.neighbob.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.neighbob.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert
    suspend fun insertPost(post: Post)

    @Query("SELECT * FROM posts ORDER BY timestamp DESC")
    fun getAllPosts(): Flow<List<Post>>

    @Query("DELETE FROM posts")
    suspend fun deleteAll()
}
