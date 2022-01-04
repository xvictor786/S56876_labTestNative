package com.example.labtest2

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button


class secondActivity : Activity() {
    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn1 = findViewById<View>(R.id.logoutBtn) as Button
        btn1.setOnClickListener { // TODO Auto-generated method stub
            finish()
            System.exit(0)
        }
    }
}