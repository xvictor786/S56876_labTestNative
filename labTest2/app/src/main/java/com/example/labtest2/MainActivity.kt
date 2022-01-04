package com.example.labtest2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var entities = ArrayList<String>()


        if(!dbExist(this,"mydata")){
            createDB();
        }

        val db = openOrCreateDatabase("mydata", MODE_PRIVATE,null)
        val sql = "SELECT Username FROM user"
        val c: Cursor = db.rawQuery(sql,null)
        while (c.moveToNext()){
            val entiti = c.getString(0)
            entities.add(entiti)
        }
        c.close()

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,entities)
        val lv = findViewById<ListView>(R.id.lv)
        lv.setAdapter(myAdapter)
        lv.onItemClickListener = AdapterView.OnItemClickListener{ adapter, v, position, arg3 ->
            val value = adapter.getItemAtPosition(position).toString()
            val intent = Intent(this,ViewUser::class.java).apply{
                putExtra("entiti",value.toString())
            }
            startActivity(intent)

        }

        val fab = findViewById<FloatingActionButton>(R.id.fab1)
        fab.setOnClickListener(){

            val intent = Intent(this,AddUser::class.java).apply{

            }
            startActivity(intent)
        }
    }



    private fun dbExist(c: Context, dbName:String ):Boolean{
        val dbFile: File = c.getDatabasePath(dbName)
        return dbFile.exists()
    }

    private fun createDB(){
        val db = openOrCreateDatabase("mydata", MODE_PRIVATE,null)
        subToast("Database mydata created!")
        val sqlText = "CREATE TABLE IF NOT EXISTS user " +
                "username VARCHAR(30) NOT NULL, " +
                "password VARCHAR(30) NOT NULL " +
                ");"
        subToast("Table user created!")
        db.execSQL(sqlText)
        var nextSQL = "INSERT INTO user (username,password) VALUES ('ahmad','ahmad1234');"
        db.execSQL(nextSQL)
        subToast("1 sample user added!")
    }

    private fun subToast(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }


}