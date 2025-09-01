package com.example.neighbob

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neighbob.data.DatabaseProvider
import com.example.neighbob.model.Post
import kotlinx.coroutines.launch
import android.widget.Button
import android.widget.EditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostAdapter
    private lateinit var inputTitle: EditText
    private lateinit var inputDescription: EditText
    private lateinit var inputCategory: EditText
    private lateinit var btnAddPost: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerViewPosts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(listOf())
        recyclerView.adapter = adapter

        // Input fields
        inputTitle = findViewById(R.id.inputTitle)
        inputDescription = findViewById(R.id.inputDescription)
        inputCategory = findViewById(R.id.inputCategory)
        btnAddPost = findViewById(R.id.btnAddPost)

        // ✅ Get database instance once
        val db = DatabaseProvider.getDatabase(this)
        val postDao = db.postDao()

        // Fetch posts
        lifecycleScope.launch {
            postDao.getAllPosts().collect { posts ->
                adapter.updatePosts(posts)
            }
        }
        // Handle button click → insert new post
        btnAddPost.setOnClickListener {
            val title = inputTitle.text.toString()
            val description = inputDescription.text.toString()
            val category = inputCategory.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        postDao.insertPost(
                            Post(title = title, description = description, category = category)
                        )
                    }
                }

                // Clear fields after adding
                inputTitle.text.clear()
                inputDescription.text.clear()
                inputCategory.text.clear()

            }
        }
    }
}
