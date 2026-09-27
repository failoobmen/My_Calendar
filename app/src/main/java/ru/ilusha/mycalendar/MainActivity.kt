package ru.ilusha.mycalendar

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dummyTasks = listOf(
            Task(id = 1, title = "Купить продукты", description = "Молоко, хлеб", date = "10.10.2024", isDone = false),
            Task(id = 2, title = "Сделать лабу", description = "По сетям", date = "11.10.2024", isDone = true),
            Task(id = 3, title = "Позвонить", description = "Срочно", date = "12.10.2024", isDone = false)
        )

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_tasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = TaskAdapter(dummyTasks) { clickedTask ->
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("TASK_ID", clickedTask.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val fab: FloatingActionButton = findViewById(R.id.fab_add_task)
        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}