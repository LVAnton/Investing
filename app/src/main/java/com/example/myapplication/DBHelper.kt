package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, "trades", factory,2 ) {
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users(id INT PRIMARY KEY, login TEXT, email TEXT, pass TEXT)"
        val query2 = "CREATE TABLE changes (\n" +
                "    id_changes int PRIMARY KEY,\n" +
                "    login REFERENCES users(login) NOT NULL,\n" +
                "    stock text,\n" +
                "    kolvo integer,\n" +
                "    price float,\n" +
                "    is_buy boolean,\n" +
                "    buy_time date\n" +
                ");"
        db!!.execSQL(query)
        db.execSQL(query2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS changes")
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User){
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("pass", user.password)

        val db = this.writableDatabase
        db.insert("users", null, values)

        db.close()
    }

    fun addTrade(trade: Trade){
        val values = ContentValues()
        values.put("buy_time", trade.buy_time)
        values.put("is_buy", trade.is_buy)
        values.put("kolvo", trade.kolvo)
        values.put("price", trade.price)
        values.put("stock", trade.stock)
        values.put("login", trade.login)

        val db = this.writableDatabase
        db.insert("changes", null, values)

        db.close()
    }

    fun getTrade(stock: String, one_stock: Boolean, login: String): Array<Array<Float>>{
        val db = this.writableDatabase
        val cursor: Cursor
        if (one_stock == true) {
            cursor = db.query("changes WHERE stock ='$stock' and login = '$login'  ", null, null, null, null, null, null)
        } else{
            cursor = db.query("changes WHERE login = '$login'  ", null, null, null, null, null, null)
        }
        val trade_array = Array(cursor.count){Array<Float>(3) { 0f }}
        var k = 0
        while(cursor?.moveToNext()!!) {
            val a1 = cursor.getColumnIndex("kolvo")
            val a2 = cursor.getColumnIndex("price")
            val a3 = cursor.getColumnIndex("is_buy")
            val shtuka = cursor.getFloat(a1)
            val shtuka2 = cursor.getFloat(a2)
            var shtuka3 = cursor.getFloat(a3)

            trade_array[k][0] = shtuka
            trade_array[k][1] = shtuka2
            trade_array[k][2] = shtuka3
            k+=1
        }
        cursor.close()
        return trade_array
    }

    fun getUser(login: String, pass: String): Boolean {
        val db = this.writableDatabase

        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)

        return result.moveToFirst()
    }

    fun getStocks(): Array<Array<String>>{
        val db = this.writableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT(stock) FROM changes", null)

        val trade_array = Array(cursor.count){Array<String>(1) { "" }}

        var k = 0
        while(cursor?.moveToNext()!!) {
            val a1 = cursor.getColumnIndex("stock")
            val shtuka = cursor.getString(a1)
            trade_array[k][0] = shtuka
            k+=1
        }
        cursor.close()
        return trade_array
    }

}