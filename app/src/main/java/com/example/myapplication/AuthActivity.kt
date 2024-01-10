package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toReg = findViewById<TextView>(R.id.link_to_reg)

        toReg.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val userLogin = findViewById<EditText>(R.id.user_login_auth)
        val userPass = findViewById<EditText>(R.id.user_pass_auth)
        val button = findViewById<Button>(R.id.button_auth)

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login == "" || pass == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else {
                val db = DBHelper(this, null)
                val isAuth = db.getUser(login, pass)

                if (isAuth){
                    userLogin.text.clear()
                    userPass.text.clear()
                    Toast.makeText(this, "Успешная авторизация", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("login", login)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Аккаунт не найден", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}