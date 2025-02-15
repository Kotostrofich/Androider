package com.example.traveller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import java.io.FileOutputStream

class RegistrActivity : AppCompatActivity() {

    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var surnameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var passwordInputLayout: TextInputLayout
    private lateinit var registerButton: com.google.android.material.button.MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_scr)

        nameInputLayout = findViewById(R.id.nameInputLayout)
        surnameInputLayout = findViewById(R.id.surnameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val name = nameInputLayout.editText?.text.toString().trim()
            val surname = surnameInputLayout.editText?.text.toString().trim()
            val email = emailInputLayout.editText?.text.toString().trim()
            val password = passwordInputLayout.editText?.text.toString().trim()

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Введите корректный email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Пароль должен содержать не менее 6 символов", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            pressButton(name, surname, email, password)

        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun pressButton(name: String, surname: String, email: String, password: String) {
        val fileName = "Data.txt"
        val data = "Name: $name\nSurname: $surname\nEmail: $email\nPassword: $password\n"

        try {
            // Сохраняем данные в файл
            val fileOutputStream: FileOutputStream = openFileOutput(fileName, MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()

            // Уведомляем пользователя об успешном сохранении
            Toast.makeText(this, "Данные сохранены в файл", Toast.LENGTH_SHORT).show()

            // Переход на третью активность
            val intent = Intent(this, ReadActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show()
        }
    }
}