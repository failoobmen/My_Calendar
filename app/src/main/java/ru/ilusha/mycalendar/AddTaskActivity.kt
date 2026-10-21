package ru.ilusha.mycalendar

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class AddTaskActivity : AppCompatActivity() {

    private val viewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

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

            viewModel.addTask(title, description, date, time)
            Toast.makeText(this, "Задача добавлена", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}