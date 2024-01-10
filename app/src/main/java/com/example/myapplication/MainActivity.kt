package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val userData = findViewById<EditText>(R.id.zapolni)
        val toRedact = findViewById<Button>(R.id.knop)
        val toAuth = findViewById<Button>(R.id.button_toAuth)
        val toStat = findViewById<Button>(R.id.button_stat)

        val login = intent.getStringExtra("login")


        toStat.setOnClickListener {
            val intent = Intent(this, Statistik::class.java)
            intent.putExtra("stock", "Общая статистика")
            intent.putExtra("login", login)
            startActivity(intent)
        }

        toRedact.setOnClickListener {
            val text = userData.text.toString().trim()
            if (text == "") {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                val intent = Intent(this, AddAction::class.java)
                intent.putExtra("login", login)
                intent.putExtra("action_name", text)
                startActivity(intent)
            }
        }
        toAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val items = arrayListOf<Item>()

        val db = DBHelper(this, null)
        val stocks_list = db.getStocks()
        val size = stocks_list.size

        var k = 0
        while (k < size) {
            val trade = db.getTrade(stocks_list[k][0], true, login.toString())
            var kolvo = 0
            var now_cost = 270.0f // в альтернативном мире, где я научился делать https запрос эта информация считывается с мосбиржи (я смог сделать этот код только на питоне)
            for (i in 0..trade.size - 1) {
                if (trade[i][2].toInt() == 1) {
                    kolvo += trade[i][0].toInt()
                } else {
                    kolvo -= trade[i][0].toInt()
                }
            }
            items.add(Item(stocks_list[k][0], kolvo, now_cost))
            k += 1
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemsAdapter(items, this, login.toString())
    }
}