package ru.ilusha.mycalendar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddTaskActivity : AppCompatActivity() {

    private lateinit var repository: TaskRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        repository = TaskRepository(applicationContext)

        val titleInput = findViewById<EditText>(R.id.edit_title)
        val descInput = findViewById<EditText>(R.id.edit_description)
        val dateInput = findViewById<EditText>(R.id.edit_date)
        val timeInput = findViewById<EditText>(R.id.edit_time)
        val saveBtn = findViewById<Button>(R.id.button_save)

        saveBtn.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val description = descInput.text.toString().trim()
            val date = dateInput.text.toString().trim()
            val time = timeInput.text.toString().trim()

            if (title.isEmpty() || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(this, "Заполни название, дату и время", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                repository.insert(
                    Task(
                        title = title,
                        description = description,
                        date = date,
                        time = time,
                        isDone = false
                    )
                )
                Toast.makeText(this@AddTaskActivity, "Задача добавлена", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}