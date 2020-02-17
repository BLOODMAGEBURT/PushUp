package com.burt.pushup

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.burt.pushup.ext.toast
import com.burt.pushup.view.StartBtnView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : View.OnClickListener, AppCompatActivity() {

    private lateinit var sm: SensorManager
    lateinit var sensor: Sensor

    lateinit var listener: SensorEventListener

    var shouldCount = true

    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        this.tv_down.setOnClickListener(this)
        this.tv_up.setOnClickListener(this)
        this.tv_demo.setOnClickListener(this)
        this.tv_record.setOnClickListener(this)
        this.tv_ambition.setOnClickListener(this)


        this.btn_start.setOnStartBtnListener(object : StartBtnView.StartBtnListener {
            override fun onStart() {

                // 注册监听位置传感器
                sm.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
            }

            override fun onStop() {

                sm.unregisterListener(listener)

                reset()
            }
        })

        /**
         * 距离传感器
         * */

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensor = sm.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        listener = object : SensorEventListener {

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.d("xu", "执行了距离检测")
            }

            override fun onSensorChanged(event: SensorEvent?) {

                //获得距离传感器中的数值，这里只有一个远近:0.0 和 5.0
                //是否靠近
                if (event?.values!![0] == 0.0f) {

                    if (shouldCount) {

                        shouldCount = false
                        // 计数加一
                        count++

                        // 更新文字
                        update()
                    }

                } else {

                    shouldCount = true
                }
            }
        }

    }


    override fun onClick(v: View?) {

        when (v?.id) {
            R.id.tv_down -> down()
            R.id.tv_up -> up()
            R.id.tv_demo -> toDemo()
            R.id.tv_record -> toRecord()
            R.id.tv_ambition -> toAmbition()
        }
    }

    private fun toDemo() {

        startActivity(Intent(this, DemoActivity::class.java))


    }

    private fun toRecord() {
        Log.d("xu", "some log")
        Log.d("xu", "logTO")
        startActivity(Intent(this, RecordActivity::class.java))
    }

    private fun toAmbition() {
        Log.d("xu", "some log")
        Log.d("xu", "logTO")
        startActivity(Intent(this, AmbitionActivity::class.java))
    }

    private fun reset() {

        count = 0

        btn_start.reset()

    }

    private fun update() {

        val total = this.et_count.text.toString().toFloat()

        if (count <= total) {
            btn_start.updateTextInCircle(count, total)
        }

    }

    private fun up() {

        val num = et_count.text.toString().toInt()
        if (num < 200) {
            et_count.setText("${num + 1}")
        } else {
            toast("超过200个都是赛亚人")
        }

    }

    private fun down() {

        val num = et_count.text.toString().toInt()
        if (num > 1) {
            et_count.setText("${num - 1}")
        } else {
            toast("小样，怎么也得做一个吧")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        sm.unregisterListener(listener)
    }
}
