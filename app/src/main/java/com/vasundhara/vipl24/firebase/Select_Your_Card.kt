package com.vasundhara.vipl24.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_select_your_card.*

class Select_Your_Card : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_your_card)




        img_verticalcard.setOnClickListener {

            startActivity(Intent(this,DisplayInfoActivity::class.java))
        }


        img_horizontalcard.setOnClickListener {
            startActivity(Intent(this,DisplayEmployeeinfo2::class.java))
        }

    }
}