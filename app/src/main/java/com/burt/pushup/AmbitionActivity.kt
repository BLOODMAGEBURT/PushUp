package com.burt.pushup

import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.burt.pushup.view.StartBtnView
import kotlinx.android.synthetic.main.activity_ambition.*
import kotlinx.android.synthetic.main.activity_ambition.btn_start
import kotlinx.android.synthetic.main.activity_main.*

class AmbitionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ambition)
        supportActionBar?.hide()


        this.btn_start.setOnStartBtnListener(object : StartBtnView.StartBtnListener {
            override fun onStart() {
                Log.d("xu", "start")
            }

            override fun onStop() {

            }
        })

    }
}
