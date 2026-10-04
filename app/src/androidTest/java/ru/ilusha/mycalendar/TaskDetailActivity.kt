package ru.ilusha.mycalendar

import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TaskDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val taskId = intent.getIntExtra(EXTRA_TASK_ID, -1)
        val task = TaskRepository.getById(taskId)

        val titleView = findViewById<TextView>(R.id.text_detail_title)
        val descView = findViewById<TextView>(R.id.text_detail_description)
        val dateView = findViewById<TextView>(R.id.text_detail_date)
        val doneView = findViewById<CheckBox>(R.id.checkbox_detail_is_done)

        if (task == null) {
            titleView.text = "Задача не найдена (ID=$taskId)"
            descView.text = ""
            dateView.text = ""
            doneView.isEnabled = false
        } else {
            titleView.text = task.title
            descView.text = task.description
            dateView.text = "Дата: ${task.date}"
            doneView.isChecked = task.isDone
            doneView.isEnabled = false
        }
    }

    companion object {
        const val EXTRA_TASK_ID = "TASK_ID"
    }
}