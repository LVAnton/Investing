package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlin.math.abs

class Statistik : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistik)
        val login = intent.getStringExtra("login").toString()
        val stock = intent.getStringExtra("stock").toString()
        val stat_title = findViewById<TextView>(R.id.stat_title)
        stat_title.text = stock
        val exit_btn = findViewById<Button>(R.id.exit_btn)
        exit_btn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("login", login)
            startActivity(intent)
        }
        val individ1 = findViewById<TextView>(R.id.individ1)
        val individ2 = findViewById<TextView>(R.id.individ2)
        val individ3 = findViewById<TextView>(R.id.individ3)
        val individ4 = findViewById<TextView>(R.id.individ4)
        val general1 = findViewById<TextView>(R.id.general1)
        val general2 = findViewById<TextView>(R.id.general2)
        val general3 = findViewById<TextView>(R.id.general3)
        val general4 = findViewById<TextView>(R.id.general4)

        val db = DBHelper(this, null)

        var logic = true
        if (stock == "Общая статистика"){
            logic = false
        }
        val trade = db.getTrade(stock, logic, login)

        val trade_len = trade.size
        var buysum = 0.0
        var sellsum = 0.0
        var kolvo = 0f
        var kolvo_all = 0f
        var now_cost = 270.0
        for (i in 0..trade_len-1){
            kolvo_all += trade[i][0]
            if (trade[i][2].toInt() == 1){
                buysum+=trade[i][0]*trade[i][1]
                kolvo += trade[i][0]
            } else {
                sellsum += trade[i][0]*trade[i][1]
                kolvo -= trade[i][0]
            }
        }
        var all_time = kolvo*now_cost + sellsum - buysum
        general1.text = "Ценных бумаг в наличии: " + (kolvo_all.toInt()).toString()
        general2.text = "На них потрачено: " + buysum.toString()
        general3.text = "Вы можете продать их за: " + (sellsum + kolvo*now_cost).toString()
        general4.text = "процент за все время: " + (all_time/buysum).toString()


        var suma = 0f
        var kolvo2 = kolvo
        var number = 1
        while (kolvo2 > 0){
            if (trade[trade_len - number][2] == 1f){
                if (kolvo2 - trade[trade_len - number][0] > 0){
                    kolvo2 = kolvo2 - trade[trade_len - number][0]
                    suma += trade[trade_len - number][0] * trade[trade_len - number][1]
                } else{
                    suma += kolvo2 * trade[trade_len - number][1]
                    kolvo2 = 0f
                }
            }
            number += 1
        }
        individ1.text = "Ценных бумаг в наличии: " + (kolvo.toInt()).toString()
        individ2.text = "На них потрачено: " + (suma).toString()
        individ3.text = "Вы можете продать их за: " + (kolvo*now_cost).toString()
        individ4.text = "процент за текущие акции: " + ((kolvo*now_cost - suma)/suma).toString()
    }
}