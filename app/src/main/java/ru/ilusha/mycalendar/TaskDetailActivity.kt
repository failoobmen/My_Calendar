package ru.ilusha.mycalendar

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var repository: TaskRepository
    private var currentTask: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        repository = TaskRepository(applicationContext)

        val titleView = findViewById<TextView>(R.id.text_detail_title)
        val descView = findViewById<TextView>(R.id.text_detail_description)
        val dateView = findViewById<TextView>(R.id.text_detail_date)
        val doneView = findViewById<CheckBox>(R.id.checkbox_detail_is_done)
        val deleteBtn = findViewById<Button>(R.id.button_delete_task)

        val taskId = intent.getIntExtra(EXTRA_TASK_ID, -1)

        lifecycleScope.launch {
            val task = repository.getById(taskId)
            currentTask = task

            if (task == null) {
                titleView.text = "Задача не найдена (ID=$taskId)"
                descView.text = ""
                dateView.text = ""
                doneView.isEnabled = false
                deleteBtn.isEnabled = false
            } else {
                titleView.text = task.title
                descView.text = task.description
                dateView.text = "Дата: ${task.date} ${task.time}"
                doneView.isChecked = task.isDone

                doneView.setOnCheckedChangeListener { _, isChecked ->
                    lifecycleScope.launch {
                        repository.update(task.copy(isDone = isChecked))
                    }
                }

                deleteBtn.setOnClickListener {
                    lifecycleScope.launch {
                        repository.delete(task)
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }
}