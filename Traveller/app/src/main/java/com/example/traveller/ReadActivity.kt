package com.example.traveller

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream
import java.io.FileOutputStream

class ReadActivity : AppCompatActivity() {

    private lateinit var dataTextView: TextView
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)

        dataTextView = findViewById(R.id.dataTextView)
        clearButton = findViewById(R.id.clearButton)

        val userData = readUserDataFromFile()
        dataTextView.text = userData

        clearButton.setOnClickListener {
            clearUserDataFromFile()
            dataTextView.text = "Данные очищены"
            Toast.makeText(this, "Данные успешно очищены", Toast.LENGTH_SHORT).show()
        }
    }

    // Функция для чтения данных из файла
    private fun readUserDataFromFile(): String {
        val fileName = "Data.txt"
        val stringBuilder = StringBuilder()

        try {
            val fileInputStream: FileInputStream = openFileInput(fileName)
            val buffer = ByteArray(1024)
            var bytesRead: Int

            while (fileInputStream.read(buffer).also { bytesRead = it } != -1) {
                stringBuilder.append(String(buffer, 0, bytesRead))
            }

            fileInputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return "Ошибка при чтении данных из файла"
        }

        return stringBuilder.toString()
    }

    private fun clearUserDataFromFile() {
        val fileName = "Data.txt"

        try {
            val fileOutputStream: FileOutputStream = openFileOutput(fileName, MODE_PRIVATE)
            fileOutputStream.write("".toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка при очистке данных", Toast.LENGTH_SHORT).show()
        }
    }
}