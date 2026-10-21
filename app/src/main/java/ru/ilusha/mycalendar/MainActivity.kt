package ru.ilusha.mycalendar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // Получаем ViewModel через делегат
    private val viewModel: TaskViewModel by viewModels()
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view_tasks)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter { clickedTask ->
            val intent = Intent(this, TaskDetailActivity::class.java)
            intent.putExtra(TaskDetailActivity.EXTRA_TASK_ID, clickedTask.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        val fab: FloatingActionButton = findViewById(R.id.fab_add_task)
        fab.setOnClickListener {
            startActivity(Intent(this, AddTaskActivity::class.java))
        }

        // Кнопка для теста (по замечанию преподавателя)
        val btnSeed: Button = findViewById(R.id.btn_debug_seed)
        btnSeed.setOnClickListener {
            viewModel.seedTestTasks()
        }

        // ПОДПИСКА НА STATE FLOW
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasks.collect { tasks ->
                    Log.d("MainActivity", "Список обновился! Задач в БД: ${tasks.size}")
                    tasks.forEach {
                        Log.d("MainActivity", "id=${it.id}, title=${it.title}, date=${it.date}, isDone=${it.isDone}")
                    }
                    adapter.submitList(tasks)
                }
            }
        }
    }
}