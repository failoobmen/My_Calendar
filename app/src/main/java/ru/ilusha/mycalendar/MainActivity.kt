package ru.ilusha.mycalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Связываем этот экран с нашей XML-разметкой
        setContentView(R.layout.activity_main)
    }
}