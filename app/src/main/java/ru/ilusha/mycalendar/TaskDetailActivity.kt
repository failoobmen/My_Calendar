package ru.ilusha.mycalendar

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

class TaskDetailActivity : AppCompatActivity() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val titleView = findViewById<TextView>(R.id.text_detail_title)
        val descView = findViewById<TextView>(R.id.text_detail_description)
        val dateView = findViewById<TextView>(R.id.text_detail_date)
        val doneView = findViewById<CheckBox>(R.id.checkbox_detail_is_done)
        val deleteBtn = findViewById<Button>(R.id.button_delete_task)

        val taskId = intent.getIntExtra(EXTRA_TASK_ID, -1)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.tasks.collect { tasks ->
                    val task = tasks.find { it.id == taskId }
                    if (task != null) {
                        titleView.text = task.title
                        descView.text = task.description
                        dateView.text = "Дата: ${task.date} ${task.time}"
                        doneView.isChecked = task.isDone

                        // Отписываемся перед обновлением, чтобы не зациклить
                        doneView.setOnCheckedChangeListener(null)
                        doneView.isChecked = task.isDone

                        doneView.setOnCheckedChangeListener { _, isChecked ->
                            viewModel.updateTask(task.copy(isDone = isChecked))
                        }

                        deleteBtn.setOnClickListener {
                            viewModel.deleteTask(task)
                            finish()
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }
}