package com.example.labtest2

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ViewUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_user)

        val intent = this.getIntent()
        val Username = intent.getStringExtra("Username")

        supportActionBar?.setTitle(Username)

        val db = openOrCreateDatabase("mydata", MODE_PRIVATE,null)
        val sql = "SELECT username,password FROM user WHERE Username = '$Username'"
        val cursor = db.rawQuery(sql,null)
        var username = ""
        var password = ""

        while (cursor.moveToNext()){
            username = cursor.getString(0)
            password = cursor.getString(1)

        }
        cursor.close()

        val uname = findViewById<EditText>(R.id.inUsername)
        val passwd = findViewById<EditText>(R.id.inPassword)

        uname.setText(username)
        passwd.setText(password)

        val btnDel = findViewById<FloatingActionButton>(R.id.delBtn)
        var delDialog : AlertDialog? = null
        val delBuilder = AlertDialog.Builder(this)
        delBuilder.setTitle("Delete Process")
        delBuilder.setMessage("Are sure to delete?")

        delBuilder.setNeutralButton("Cancel"){dialogInterface, which ->
            subToast("Deleted Cancelled")
        }

        delBuilder.setPositiveButton("Yes"){dialogInterface, which ->
            val db = openOrCreateDatabase("mydata", MODE_PRIVATE,null)
            val sql = "DELETE FROM user where Username='$Username';"
            //tak keluar
            db.execSQL(sql)


            subToast("Username $Username deleted!")
            val intent = Intent(this,MainActivity::class.java).apply{

            }
            startActivity(intent)
        }
        delDialog = delBuilder.create()
        btnDel.setOnClickListener(){
            delDialog.show()

        }

        //====================================

        val btnEdit = findViewById<FloatingActionButton>(R.id.editBtn)
        var editDialog : AlertDialog? = null
        val editBuilder = AlertDialog.Builder(this)
        editBuilder.setTitle("Update Process")
        editBuilder.setMessage("Are sure to update the data?")

        editBuilder.setNeutralButton("Cancel"){dialogInterface, which ->
            subToast("Update Cancelled")
        }

        editBuilder.setPositiveButton("Yes"){dialogInterface, which ->

            val uname = findViewById<EditText>(R.id.inUsername)
            val passwd = findViewById<EditText>(R.id.inPassword)
            val un = uname.text.toString()
            val pass = passwd.text.toString()


            val db = openOrCreateDatabase("mydata", MODE_PRIVATE,null)
            val sql = "UPDATE user SET username='$un', password='$pass' WHERE Username='$Username';"
            //tak keluar
            db.execSQL(sql)


            subToast("Username $Username updated!")
            val intent = Intent(this,MainActivity::class.java).apply{

            }
            startActivity(intent)
        }
        editDialog = editBuilder.create()

        btnEdit.setOnClickListener(){
            editDialog.show()

        }
//===========
    }
    private fun subToast(msg: String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}