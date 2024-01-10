package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AddAction : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_action)

        val action = findViewById<TextView>(R.id.action_name)
        val button_add = findViewById<Button>(R.id.button_add)
        val button_del = findViewById<Button>(R.id.button_del)
        val button_exit = findViewById<Button>(R.id.button_exit)
        val kolvo_elem = findViewById<EditText>(R.id.action_kolvo)
        val price_elem = findViewById<EditText>(R.id.action_price)
        val stock = intent.getStringExtra("action_name").toString().trim()
        val login = intent.getStringExtra("login").toString().trim()
        val date_elem = findViewById<EditText>(R.id.action_date)
        action.text = "ценная бумага: " + intent.getStringExtra("action_name")

        button_exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("login", login)
            startActivity(intent)
        }
        button_add.setOnClickListener {
            val kolvo = kolvo_elem.text.toString().trim().toInt()
            val price = price_elem.text.toString().trim().toFloat()
            val date = date_elem.text.toString().trim()

            if (kolvo <= 0 || price <= 0 || date == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else {
                val trade = Trade(login, stock, kolvo, price, is_buy=true, date)
                Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
                val db = DBHelper(this, null)
                db.addTrade(trade)


                kolvo_elem.text.clear()
                price_elem.text.clear()
                date_elem.text.clear()
            }
        }

        button_del.setOnClickListener {
            val kolvo = kolvo_elem.text.toString().trim().toInt()
            val price = price_elem.text.toString().trim().toFloat()
            val date = date_elem.text.toString().trim()

            if (kolvo <= 0 || price <= 0 || date == "")
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            else {
                val trade = Trade(login, stock, kolvo, price, is_buy=false, date)
                Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()
                val db = DBHelper(this, null)
                db.addTrade(trade)


                kolvo_elem.text.clear()
                price_elem.text.clear()
                date_elem.text.clear()
            }
        }
    }
}