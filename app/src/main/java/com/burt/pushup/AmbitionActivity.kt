package com.burt.pushup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_ambition.*

class AmbitionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ambition)
        supportActionBar?.hide()


        knl_bottom_head.bindRuler(br_top_head)
    }
}
