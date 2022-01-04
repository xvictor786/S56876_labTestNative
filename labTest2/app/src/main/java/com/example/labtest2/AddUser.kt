package com.example.labtest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class AddUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val intent = this.getIntent()




        val btnSave = findViewById<ImageButton>(R.id.btnSave)
        btnSave.setOnClickListener(){
            val username = findViewById<EditText>(R.id.inUsername)
            val password = findViewById<EditText>(R.id.inPassword)

            val emptyLevel = emptiness(username, password)
            if(emptyLevel > 0){
                // which field is empty
                when(emptyLevel){
                    //only entiti is empty
                    6 -> subToast("Username must not empty!")
                    7 -> subToast("Password must not empty!")
                    11 -> subToast("Entiti and Username must not empty!")
                    12 -> subToast("Entiti and password must not empty!")
                    13 -> subToast("Username and password must not empty!")
                    18 -> subToast("Entiti, username and password must not empty!")
                }
            } else{
                //cek for exist entiti
                val status = checkKey(toString())
                val uname = username.text.toString()
                val passwd = password.text.toString()
                if (!status){
                    val db = openOrCreateDatabase("mydata", MODE_PRIVATE,null)
                    val sql = "INSERT INTO user (username, password) VALUES ('$uname','$passwd');"
                    db.execSQL(sql)
                    val intent = Intent(this,MainActivity::class.java).apply{
                    }
                    startActivity(intent)

                } else{
                    subToast("Entiti already exists inside Database!")
                }
            }
        }
    }
    private fun checkKey(Username: String):Boolean{
        val db = openOrCreateDatabase("mydata", MODE_PRIVATE, null)
        val sql = "select * from user where Username='$Username'"
        val cursor = db.rawQuery(sql,null)
        var out=false
        if (cursor.count > 0)
            out=true
        return out
    }
    private fun emptiness(username:EditText, password:EditText):Int{
        var empty = 0
        if (username.text.isEmpty())
            empty+=6
        if(password.text.isEmpty())
            empty+=7

        return empty
    }

    private fun subToast(msg: String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}